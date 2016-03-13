import java.util.Scanner;
import java.util.Random;

public class QuickSelection {
	
	public static void sort(int[] a,int key){
		
	//set pivot = lo, hi = a.length - 1
		int lo = 0;
		int hi = a.length - 1;
		int j;
		key = key - 1;
		do{
			j = partition(a,lo,hi);
			if (key < j)
				hi = j - 1;
			else if (key > j)
				lo = j + 1;
			else
			{
				System.out.println("Found, " + a[j]);
				return;
			}
		}while(true);
		
	}
	
	public static int partition(int[] a,int lo,int hi){
		if(hi <= lo)
			return hi;
		int i = lo + 1;
		int j = hi;
		
		do{
		
			while(a[i] < a[lo] && i < hi) i++;
			while(a[j] > a[lo] && j > lo) j--;
			
			if(j > i) 
				{
					exch(a,i,j);i++;j--;
				}
			
			
		}while(j > i);
		
		exch(a,lo,j);
		return j;
		
	}
	
	
	
	public static void exch(int[] a,int i,int j){
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
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
			System.out.println("Choose:\n1. Insert elements\n2. Find kth largest\n3. Display\n4. Sort\n5. Exit ");
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
				System.out.println("Enter key(kth highest element): ");
				int key = input.nextInt();
				QuickSelection.sort(a,key);
				break;
				}
			
			case 3:{
				QuickSelection.display(a);
			}
			case 4:{
				QuickDuplicate.sort(a);
				break;
			}
			case 5:{
				break;
			}
			
			}
			
			
		}while(c!=5);
		input.close();
		
	}
}
