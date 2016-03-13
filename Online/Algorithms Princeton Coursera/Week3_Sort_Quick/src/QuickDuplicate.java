import java.util.Scanner;
//import java.util.Random;

public class QuickDuplicate {
	
	public static void sort(int[] a){
		sort(a,0,a.length-1);
		
	}
	
	public static void exch(int[] a,int i,int j){
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	
	public static void sort(int[] a,int lo,int hi){
		if(hi <= lo)
			return;
		int i = lo + 1;
		int lt = lo;
		int gt = hi;
		int v = a[lo];
		//System.out.print("lo: " + lo + ", hi: " + hi + " ");
		
		
		do{
			//display(a);
			//System.out.print("a[i] =  " + a[i] + ", v: " + v + " ");
			
			if(a[i] < v) 
				{
					exch(a,i,lt);
					i++;lt++;
					//System.out.println(" a[i] < v . exch(a,i,lt)");
					//display(a);
					//System.out.println("Now  a[i] = " + a[i]
						//	+ ", a[lt] = " + a[lt]);
				}
			else if(a[i] > v) 
				{
					exch(a,i,gt);
					gt--;
					//System.out.println(" a[i] > v . exch(a,i,gt)");
					//display(a);
					//System.out.println("Now  a[i] = " + a[i]
						//	+ ", a[gt] = " + a[gt]);
				}
			else 
			{
				i++;
				//System.out.println(" a[i] == v . i++");
			//	display(a);
				//System.out.println("Now  a[i] = " + a[i]);
			}
			
			
			System.out.println();
			
			
		}while(gt >= i);
		System.out.println(" ---- ");
		System.out.print("lo: " + lo + ", hi: " + hi + " ");
		display(a);
		
		System.out.println("-----------------");
		sort(a,lo,lt - 1);
		sort(a,gt+1,hi);
		
	}
	
	public static void display(int[] a){
		System.out.print("Elements in the array: ");
		for(int  i = 0;i < a.length;i++)
			System.out.print(a[i]+ " ");
		System.out.println();
	}
	
	
	public static void main(String[] args){
		
		int c;
		Scanner input = new Scanner(System.in);
		
		int[] a = null;
		do{
			System.out.println("Choose:\n1. Insert elements\n2. Sort\n3. Display\n4. Exit ");
			c = input.nextInt();
			switch(c){
			
			case 1:{
				int N;
				//System.out.print("Enter number of elements: ");
				//N = input.nextInt();
				N=10;
				a = new int[N];
				//Random r = new Random();
				//System.out.print("Enter " + N +" elements into the array: ");
				
				for(int i = 0;i < N;i++)
					//a[i] = r.nextInt(N*10);// 0 2 7 4 9 9 4 2 8 4 
					a[i] = Integer.valueOf(args[i]);
				break;
			}
			case 2:{
				QuickDuplicate.sort(a);
				break;
				}
			
			case 3:{
				QuickDuplicate.display(a);
			}
			case 4:{
				break;
			}
			
			
			}
			
			
		}while(c!=4);
		input.close();
		
	}
}
