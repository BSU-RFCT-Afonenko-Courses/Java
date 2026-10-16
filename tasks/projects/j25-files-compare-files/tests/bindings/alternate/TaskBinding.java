class TaskBinding {
static boolean equal(java.nio.file.Path a,java.nio.file.Path b)throws Exception{var r=new variant.CompFiles().compare(a,b);if(r.problem()!=null)throw new java.io.IOException(r.problem());return r.same();}
}
