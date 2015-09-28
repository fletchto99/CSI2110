class tryStack2 {
	
public	static	void main(String [] args){

  Integer[] arr=new Integer[50];  

  for(int i=0; i<50; i++){
   arr[i]=new Integer(i*2);
  }

  printA(arr);
  arr = reverse(arr);
  printA(arr);
}
  public static Integer[] reverse(Integer[] a) {
    NodeStack S = new NodeStack(); 	// This is the only change from tryStack1!!!
    Integer[] b = new Integer[a.length];
    for (int i=0; i < a.length; i++)
      S.push(a[i]);
    for (int i=0; i < a.length; i++)
      b[i] = (Integer) (S.pop());
    return b;
  }

  public static void printA(Integer[] a) {
      System.out.println();
    for(int i=0; i<50; i++){
     System.out.print(a[i].intValue()+"\t");
    }
      System.out.println();

  }
}


