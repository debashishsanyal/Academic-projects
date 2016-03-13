import java.util.Scanner;
import java.util.Random;


public class Shuffle {
	static Random random = new Random();
	public static void shuffle(int[] a){
		int j,N = a.length;
		for(int i = 0;i < N;i++)
		{
			j = random.nextInt(i+1);
			exch(a,i,j);
			
		}
	}
	public static void display(int[] a){
		System.out.print("Elements in the array: ");
		for(int  i = 0;i < a.length;i++)
			System.out.print(a[i]+ " ");
		System.out.println();
	}
	
	public static void exch(int[] a,int i,int j){
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
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
				System.out.print("Enter number of elements: ");
				N = input.nextInt();
				a = new int[N];
				System.out.print("Enter " + N +" elements into the array: ");
				for(int i = 0;i < N;i++)
					a[i] = input.nextInt();
				
				break;
			}
			case 2:{
				Shuffle.shuffle(a);
				break;
				}
			
			case 3:{
				Shuffle.display(a);
			}
			case 4:{
				break;
			}
			
			
			}
			
			
		}while(c!=4);
		input.close();
		
	}
}
