#!/usr/bin/env python3
"""Вызвать штатный Core materializer для внешнего student/grading stage."""
import argparse
from pathlib import Path
import subprocess
import sys

def main(argv=None):
    parser=argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--root',type=Path,default=Path(__file__).resolve().parents[1])
    parser.add_argument('--model',required=True,type=Path)
    parser.add_argument('--output-root',required=True,type=Path)
    parser.add_argument('--audience',required=True,choices=['student','grading'])
    parser.add_argument('--task',required=True,action='append')
    parser.add_argument('--teacher-root',type=Path)
    mode=parser.add_mutually_exclusive_group(required=True)
    mode.add_argument('--write',action='store_true');mode.add_argument('--check',action='store_true')
    args=parser.parse_args(argv);root=args.root.resolve(strict=True)
    entries=[root/'_extensions/course-core/entrypoints/materialize-projects.ts',root/'_extensions/Afonenko-Course-Tools/course-core/entrypoints/materialize-projects.ts']
    entries=[p for p in entries if p.is_file() and not p.is_symlink()]
    if len(entries)!=1:raise ValueError('Требуется ровно один установленный Core с materialize-projects.ts')
    command=['quarto','run',str(entries[0]),'--','--root',str(root),'--model',str(args.model.absolute()),'--output-root',str(args.output_root.absolute()),'--audience',args.audience]
    for task in args.task:command.extend(['--task',task])
    if args.teacher_root:command.extend(['--teacher-root',str(args.teacher_root.absolute())])
    command.append('--write' if args.write else '--check')
    return subprocess.run(command,cwd=root,check=False).returncode

if __name__=='__main__':
    try:sys.exit(main())
    except (ValueError,OSError) as error:print(error,file=sys.stderr);sys.exit(1)
