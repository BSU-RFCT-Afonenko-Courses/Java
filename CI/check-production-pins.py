#!/usr/bin/env python3
"""Проверить production pins и штатную authenticated release assembly."""
from pathlib import Path
import hashlib
import importlib.util
import json
import os
import re
import subprocess

ROOT=Path(__file__).resolve().parents[1]
REPO='Afonenko-Course-Tools/prairielearn-platform'
ASSETS=('runtime-image.json','published-runtime-profiles.json','published-toolchain-lock.json')

def require(ok,message):
    if not ok:raise ValueError(message)

def git(root,*args):
    return subprocess.check_output(['git','-c','core.hooksPath=/dev/null',*args],cwd=root,stderr=subprocess.PIPE)

def pins(root):
    providers=json.loads((root/'providers.json').read_text())['providers']
    require(not any(p.get('candidate') for p in providers.values()),'Production требует опубликованных provider pins, без local candidate')
    data=json.loads((root/'CI/toolchain.json').read_text());p=data['platform'];publication=data['imagePublication']
    require(p['repository']==REPO,'Неизвестный Platform repository')
    require(re.fullmatch(r'[0-9]+\.[0-9]+\.[0-9]+',p['version']) and p['tag']=='v'+p['version'],'Нужен фактический version tag')
    for key in ('commit','sourceTree','tagObject'):require(re.fullmatch(r'[a-f0-9]{40}',p[key]),'Нужен точный Git '+key)
    for role,name in [('java','java25-grader'),('fish','fish-git-grader'),('community','prairielearn-community-gateway')]:
        require(re.fullmatch(r'ghcr\.io/afonenko-course-tools/'+name+r'@sha256:[a-f0-9]{64}',p[role+'Image']),'Нужен immutable image '+role)
    for role in ('java','fish'):require(re.fullmatch(r'sha256:[a-f0-9]{64}',p[role+'ImageConfig']),'Нужен наблюдённый config ID: '+role)
    require(publication['buildSource']==p['commit'],'Publisher source отличается от source pin')
    require(isinstance(publication['run'],int) and publication['run']>0,'Нужен actual publisher run')
    release=publication['release'];base='https://github.com/'+REPO+'/releases/'
    require(isinstance(release['id'],int) and release['id']>0 and release['url']==base+'tag/'+p['tag'] and isinstance(release['immutable'],bool),'Нужны фактические release identity/status')
    assets=publication['assets'];require(set(assets)==set(ASSETS),'Нужны ровно три опубликованных asset pins')
    for name,x in assets.items():
        require(set(x)=={'url','sha256','bytes'} and x['url']==base+'download/'+p['tag']+'/'+name and re.fullmatch(r'[a-f0-9]{64}',x['sha256']) and isinstance(x['bytes'],int) and x['bytes']>0,'Неверный asset pin: '+name)
    for key,name in [('receiptSha256','runtime-image.json'),('runtimeProfilesSha256','published-runtime-profiles.json'),('toolchainLockSha256','published-toolchain-lock.json')]:
        require(publication[key]==assets[name]['sha256'],'Asset pin не совпадает: '+key)
    require(re.fullmatch(r'[a-f0-9]{64}',publication['runnerSourceHash']),'Нужен exact runner hash')
    return data

def assembly(data,root,registry):
    p=data['platform'];publication=data['imagePublication'];root=Path(root).absolute();registry=Path(registry).absolute()
    require(registry.name=='published-runtime-profiles.json','Требуется published registry из внешнего asset directory')
    require(git(root,'rev-parse','HEAD').decode().strip()==p['commit'] and git(root,'rev-parse','HEAD^{tree}').decode().strip()==p['sourceTree'],'Platform commit/tree не совпадает')
    require(git(root,'cat-file','-t',p['tag']).decode().strip()=='tag' and git(root,'rev-parse',p['tag']).decode().strip()==p['tagObject'],'Annotated tag object не совпадает')
    # Не исполнять изменённый Python до штатной полной проверки Context.
    for name in ('release_assembly.py','publish-receipt.py','prepare_native_toolchain.py','prepare_native_jdk.py'):
        relative='tools/'+name;f=root/relative
        require(not f.is_symlink() and f.read_bytes()==git(root,'show','HEAD:'+relative),'Изменён trusted assembly module: '+name)
    for name,x in publication['assets'].items():
        f=registry.parent/name;require(not f.is_symlink() and f.is_file(),'Нужен обычный asset: '+name);raw=f.read_bytes()
        require(len(raw)==x['bytes'] and hashlib.sha256(raw).hexdigest()==x['sha256'],'Asset bytes отличаются: '+name)
    spec=importlib.util.spec_from_file_location('course_release_assembly',root/'tools/release_assembly.py');module=importlib.util.module_from_spec(spec);spec.loader.exec_module(module)
    context=module.for_build(root,registry)
    require(context is not None,'Отсутствует release assembly context')
    expected={'schema':'pl-platform-release-assets-v1','repository':'https://github.com/'+REPO,'sourceCommit':p['commit'],'sourceTree':p['sourceTree'],'version':p['version'],'tag':p['tag'],'assets':{n:x['sha256'] for n,x in publication['assets'].items()}}
    require(context.images['releaseAssembly']==expected,'External descriptor отличается от course pins')
    for role,key in [('java','javaGrader'),('fish','fishGrader'),('community','communityGateway')]:require(context.images[key]['image']==p[role+'Image'],'Image отличается от course pins: '+role)
    for role,key in [('java','javaGrader'),('fish','fishGrader')]:require(context.images[key]['localImageId']==p[role+'ImageConfig'],'Наблюдённый config отличается: '+role)
    require(context.registry['profiles']['java25-junit-v1']['runnerSha256']==publication['runnerSourceHash'],'Runner отличается от course pins')
    context.recheck()

def main():
    try:
        data=pins(ROOT);platform=os.environ.get('PL_PLATFORM_ROOT');registry=os.environ.get('PL_RUNTIME_REGISTRY')
        require(bool(platform)==bool(registry),'Platform root и published registry задаются вместе')
        if platform:assembly(data,platform,registry)
        print('Проверены production pins'+(' и authenticated release assembly' if platform else '')+'; это не grading qualification')
        return 0
    except (ValueError,KeyError,TypeError,OSError,subprocess.CalledProcessError) as error:
        print('Production pins отклонены: '+str(error));return 2
if __name__=='__main__':raise SystemExit(main())
