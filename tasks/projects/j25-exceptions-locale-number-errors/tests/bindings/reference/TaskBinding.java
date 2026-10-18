class TaskBinding {
record Summary(long count,double sum,java.util.OptionalDouble average){}static Summary run(java.nio.file.Path p)throws Throwable{var r=Study.analyze(Study.read(p));return new Summary(r.count(),r.sum(),r.average());}static java.util.List<java.util.List<String>> read(java.nio.file.Path p)throws java.io.IOException{return Study.read(p).stream().map(Study.Raw::fields).toList();}static boolean recordError(Throwable t){return t instanceof Study.BadRecord;}static long line(Throwable t){return ((Study.BadRecord)t).line();}
}
