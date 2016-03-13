import java.util.Scanner;

public class Shell {
	
	public static void sort(int[] a){
		int i,j,temp,h=1;
		while(h < a.length/3) 
			h = h*3+1;
		while(h >= 1)
		{
			for(i = 0;i< a.length-h;i++)
			{
				j = i+h;
				temp = a[j];
				while( j > h - 1 && a[j-h]>temp)
				{
					a[j] = a[j-h];
					j -= h;
				}
				System.out.println("j = "+j+", h = "+h);
			
				a[j] = temp;
				display(a);
				
			
			}
			h = h/3;
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
				Shell.sort(a);
				break;
				}
			
			case 3:{
				Shell.display(a);
			}
			case 4:{
				break;
			}
			
			
			}
			
			
		}while(c!=4);
		input.close();
		
	}
}
