import java.util.Scanner;
import java.util.Random;

public class Quick {
	
	public static void sort(int[] a){
		sort(a,0,a.length-1);
		
	}
	
	public static void exch(int[] a,int i,int j){
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	public static void sort(int[] a,int lo,int hi){
		if( hi <= lo )
			return;
		int j = hi;
		int i = lo+1;
		int pivot = lo;
		
		do{
			while(a[i] < a[pivot] && i < hi) i++;
			while(a[j] > a[pivot] && j > lo) j--;
			if (j > i) 
				{
					exch(a,i,j);
					i++;
					j--;
				}
				
		}while(j > i);
		
		exch(a,j,pivot);
		sort(a,lo,j-1);
		sort(a,j+1,hi);
		
		
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
				Random r = new Random();
				//System.out.print("Enter " + N +" elements into the array: ");
				
				for(int i = 0;i < N;i++)
					a[i] = r.nextInt(N*10);// 0 2 7 4 9 9 4 2 8 4 
					//a[i] = Integer.valueOf(args[i]);
				break;
			}
			case 2:{
				Quick.sort(a);
				break;
				}
			
			case 3:{
				Quick.display(a);
			}
			case 4:{
				break;
			}
			
			
			}
			
			
		}while(c!=4);
		input.close();
		
	}
}
