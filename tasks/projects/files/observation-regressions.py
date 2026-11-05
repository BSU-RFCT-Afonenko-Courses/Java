#!/usr/bin/env python3
"""Адресные host-проверки наблюдателей; не входят в оценку студента.

Запуск: python observation-regressions.py --java-bin /path/to/jdk25/bin
    --junit /path/to/junit-platform-console-standalone.jar --output /tmp/files-checks
Собирает только эталон, наблюдатель и малые регрессии, без Gradle и course build.
Для узкой проверки сообщений опубликованных комплектов добавьте --student-messages.
"""
import argparse
import json
from pathlib import Path
import subprocess
import time

READABILITY = r'''
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ObservationReadabilityTest {
 @Test void wrongNormalShowsPublicExpectationAndObservedResult() {
  var failure=assertThrows(AssertionError.class,()->PublicChecks.checkNormal(new PublicChecks.Observation("actual-sentinel",false)));
  assertTrue(failure.getMessage()!=null&&failure.getMessage().contains("actual-sentinel"),"JUnit должен показывать фактический результат");
  assertTrue(failure.getMessage().contains("Ожида"),"JUnit должен показывать открытое ожидание");
 }
 @Test void wrongBoundaryShowsPublicExpectationAndObservedResult() {
  var failure=assertThrows(AssertionError.class,()->PublicChecks.checkBoundary(new PublicChecks.Observation("actual-sentinel",false)));
  assertTrue(failure.getMessage()!=null&&failure.getMessage().contains("actual-sentinel"),"JUnit должен показывать фактический результат");
  assertTrue(failure.getMessage().contains("Ожида"),"JUnit должен показывать открытое ожидание");
 }
}
'''
INFRASTRUCTURE = r'''
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;
class ObservationInfrastructureTest {
 @TempDir Path directory;
 @Test void observationWorkspaceFailureHasNoStudentVerdict() throws Exception {
  Path file=directory.resolve("not-a-directory");Files.writeString(file,"occupied");
  String previous=System.getProperty("java.io.tmpdir");
  try {System.setProperty("java.io.tmpdir",file.toString());assertEquals(2,PublicChecks.example(true),"Ошибка подготовки наблюдения не является неверным ответом студента");}
  finally {System.setProperty("java.io.tmpdir",previous);}
 }
}
'''
REFUSAL = r'''
import java.io.IOException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ObservationRefusalTest {
 @Test void toolFailureIsNotSuccessfulStudentRefusal() {
  var failure=new IOException("Недоступен запуск JVM");
  assertSame(failure,assertThrows(IOException.class,()->FileInvocation.refused(()->{throw failure;})));
 }
}
'''

PROTOCOL = r'''
import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ObservationProtocolTest {
 @Test void studentPrintedMarkerDoesNotBecomeToolFailure() {
  assertDoesNotThrow(()->FileInvocation.run("printed-marker"));
 }
 @Test void actualStudentExceptionKeepsItsType() {
  assertThrows(JavaProcess.UncaughtStudentException.class,()->FileInvocation.run("student-failure"));
 }
 @Test void actualStudentExceptionExplainsRefusal() {
  assertDoesNotThrow(()->FileInvocation.refused(()->FileInvocation.run("student-failure")));
 }
 @Test void silentReturnIsNotExplainedRefusal() {
  assertThrows(AssertionError.class,()->FileInvocation.refused(()->FileInvocation.run("silent")));
 }
}
'''
PORT_FIXTURE = r'''
public class ProcessPort {
 public static void main(String[] args) throws Exception {
  if(args[0].equals("printed-marker"))System.err.println("COURSE_PORT_FAILURE=это обычный открытый текст программы");
  if(args[0].equals("student-failure"))throw new java.io.IOException("Отказ чтения входа в программе");
 }
}
'''

def main():
    parser=argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--java-bin',required=True,type=Path)
    parser.add_argument('--junit',required=True,type=Path)
    parser.add_argument('--output',required=True,type=Path)
    parser.add_argument('--task',action='append')
    mode=parser.add_mutually_exclusive_group()
    mode.add_argument('--protocol-only',action='store_true')
    mode.add_argument('--student-messages',action='store_true')
    args=parser.parse_args()
    tasks=sorted(task for task in Path(__file__).parent.iterdir() if (task/'observations/src/test/java').is_dir())
    unknown=set(args.task or [])-{task.name for task in tasks}
    if unknown:
        parser.error('Неизвестные проекты: '+', '.join(sorted(unknown)))
    results=[]
    for task in tasks:
        observations=task/'observations/src/test/java'
        if not observations.is_dir() or args.task and task.name not in args.task:
            continue
        if args.protocol_only and not (observations/'FileInvocation.java').exists():
            continue
        start=time.monotonic()
        work=args.output/task.name
        main_classes=work/'student'
        test_classes=work/'observations'
        fixtures=work/'fixtures'
        for directory in (main_classes,test_classes,fixtures):
            directory.mkdir(parents=True,exist_ok=True)
        (fixtures/'ObservationReadabilityTest.java').write_text(READABILITY)
        public=(observations/'PublicChecks.java').read_text()
        if not args.student_messages and 'static int example(' in public:
            (fixtures/'ObservationInfrastructureTest.java').write_text(INFRASTRUCTURE)
        if not args.student_messages and (observations/'FileInvocation.java').exists():
            (fixtures/'ObservationRefusalTest.java').write_text(REFUSAL)
        sources=sorted(observations.rglob('*.java'))
        if args.student_messages:
            replacements={'PublicChecks.java':task/'student/src/test/java/PublicChecks.java'}
            student_binding=task/'student/src/test/java/TaskBinding.java'
            if student_binding.exists():
                replacements['TaskBinding.java']=student_binding
            sources=[replacements.get(source.name,source) for source in sources]
        commands=[
            [args.java_bin/'javac','--release','25','-encoding','UTF-8','-d',main_classes,*sorted((task/'reference/src/main/java').rglob('*.java'))],
            [args.java_bin/'javac','--release','25','-encoding','UTF-8','-cp',str(main_classes)+':'+str(args.junit),'-d',test_classes,*sources,*sorted(fixtures.glob('*.java'))],
            [args.java_bin/'java','-jar',args.junit,'execute','--class-path',str(test_classes)+':'+str(main_classes),'--scan-class-path','--disable-banner','--details=summary'],
        ]
        if args.protocol_only:
            commands=[]
        if not args.student_messages and (observations/'FileInvocation.java').exists():
            protocol=work/'protocol'
            protocol.mkdir(exist_ok=True)
            (protocol/'ProcessPort.java').write_text(PORT_FIXTURE)
            (protocol/'Study.java').write_text('public class Study {}')
            (protocol/'ObservationProtocolTest.java').write_text(PROTOCOL)
            (protocol/'ObservationRefusalTest.java').write_text(REFUSAL)
            commands.extend([
                [args.java_bin/'javac','--release','25','-encoding','UTF-8','-cp',args.junit,'-d',protocol,observations/'FileInvocation.java',observations/'coursesupport/JavaProcess.java',*sorted(protocol.glob('*.java'))],
                [args.java_bin/'java','-jar',args.junit,'execute','--class-path',str(protocol),'--scan-class-path','--disable-banner','--details=summary'],
            ])
        log=[]
        for command in commands:
            result=subprocess.run(list(map(str,command)),text=True,stdout=subprocess.PIPE,stderr=subprocess.STDOUT)
            log.append('$ '+' '.join(map(str,command))+'\n'+result.stdout)
            if result.returncode:
                break
        (work/'run.log').write_text('\n'.join(log))
        record={'task':task.name,'status':'PASS' if result.returncode==0 else 'FAIL','seconds':round(time.monotonic()-start,3),'log':str(work/'run.log')}
        results.append(record)
        print(json.dumps(record,ensure_ascii=False),flush=True)
    if not results:
        parser.error('Для выбранного режима не найдено ни одного проекта')
    args.output.mkdir(parents=True,exist_ok=True)
    (args.output/'results.json').write_text(json.dumps(results,ensure_ascii=False,indent=2))
    return int(any(result['status']!='PASS' for result in results))

if __name__=='__main__':
    raise SystemExit(main())
