import java.util.Scanner;

public class Insertion {
	
	public static void sort(int[] a){
		int i,j,temp;
		for(i = 0;i< a.length-1;i++)
		{
			j = i+1;
			temp = a[j];
			while( j > 0 && a[j-1]>temp)
			{
				a[j] = a[j-1];
				j--;
			}
			System.out.println("j = "+j);
			
			a[j] = temp;
			display(a);
			
			
		}
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
				System.out.print("Enter number of elements: ");
				N = input.nextInt();
				a = new int[N];
				System.out.print("Enter " + N +" elements into the array: ");
				for(int i = 0;i < N;i++)
					a[i] = input.nextInt();
				
				break;
			}
			case 2:{
				Insertion.sort(a);
				break;
				}
			
			case 3:{
				Insertion.display(a);
			}
			case 4:{
				break;
			}
			
			
			}
			
			
		}while(c!=4);
		input.close();
		
	}
}
