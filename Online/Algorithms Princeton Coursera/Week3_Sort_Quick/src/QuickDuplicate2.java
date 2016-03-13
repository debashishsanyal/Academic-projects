import java.util.Scanner;
import java.util.Random;

public class QuickDuplicate2 {
	
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
		
		System.out.print("lo = "+ lo + " ");
		do{
			
			if(a[i] < v) 
				{
					exch(a,i,lt);
					i++;lt++;
				}
			else if(a[i] > v) 
				{
					exch(a,i,gt);
					gt--;
				}
			else 
			{
				i++;
			}
			
			
		}while(gt >= i);
		display(a);
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
				N=20;
				a = new int[N];
				Random r = new Random();
				//System.out.print("Enter " + N +" elements into the array: ");
				
				for(int i = 0;i < N;i++)
					a[i] = r.nextInt(8);// 0 2 7 4 9 9 4 2 8 4 
					//a[i] = Integer.valueOf(args[i]);
				break;
			}
			case 2:{
				QuickDuplicate2.sort(a);
				break;
				}
			
			case 3:{
				QuickDuplicate2.display(a);
			}
			case 4:{
				break;
			}
			
			
			}
			
			
		}while(c!=4);
		input.close();
		
	}
}
