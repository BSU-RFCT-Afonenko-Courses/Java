class TaskBinding {
static boolean equal(java.nio.file.Path a,java.nio.file.Path b)throws Exception{return CompFiles.equal(a,b);}
static Class<?> entry(){return CompFiles.class;}static boolean equalityPrinted(coursesupport.JavaProcess.Result r,boolean expected){return r.stderr().isBlank()&&r.stdout().strip().equals(expected?"Файлы совпадают":"Файлы различаются");}
}
