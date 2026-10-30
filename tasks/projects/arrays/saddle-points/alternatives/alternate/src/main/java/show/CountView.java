package show;
public class CountView {private int count(int[][] a){int result=0;for(int i=0;i<a.length;i++)for(int j=0;j<a[i].length;j++){boolean row=true,column=true;for(int k=0;k<a.length;k++){if(a[i][k]<a[i][j])row=false;if(a[k][j]>a[i][j])column=false;}if(row&&column)result++;}return result;}
void main(String[] args){int[][] a={{-3,0,3},{2,-1,0},{1,3,-2}};System.out.println("Количество: "+count(a));}}
