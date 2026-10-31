class Main {void main(){boolean[] composite=new boolean[101];for(int p=2;p<=100;p++){if(!composite[p]){System.out.print(p+"\r\n");for(int v=p+p;v<=100;v+=p)composite[v]=true;}}}}
