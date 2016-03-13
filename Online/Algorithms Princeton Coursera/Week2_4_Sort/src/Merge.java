import java.util.Scanner;

public class Merge {
	
	private int[] a = null;
	private int[] b = null;
	private int[] c = null;
	
	
	public Merge(int[] a,int[] b){
		this.a = a;
		this.b = b;
		c = new int[2*a.length];
		
	}

	public void sort(){
		int i=0,j=0,k=0;
		
		//i = 0, j = 0,for every 'ith' element in a
		//check weather either i or j have reached the end; if so, copy the
		//check the jth element in b
		//whichever is the smaller element, gets added to c, and corresponding i,j is incremented
		//if either i, or j reach the end, simple copy the other completely onto c
		while(k != (2*a.length)){
			if( i == a.length || j == a.length)
			{
				if(i == a.length)
				{
					while(j < a.length){
						c[k] = b[j];
						j++;
						k++;
					}
					k--;
				}
				else
				{
					while(i < a.length){
						c[k] = a[i];
						i++;
						k++;
					}
					k--;
					
				}
			}
			else
			{
				if(a[i]<=b[j])
				{
					c[k] = a[i];
					i++;
				}
				else
				{
					c[k] = b[j];
					j++;
				}
			}
			k++;
		}
		
			
			
	}
	
	public void display(){
		System.out.print("Elements in a: ");
		for(int  i = 0;i < a.length;i++)
			System.out.print(a[i]+ " ");
		System.out.println();
		
		System.out.print("Elements in b: ");
		for(int  i = 0;i < a.length;i++)
			System.out.print(b[i]+ " ");
		System.out.println();
		
		System.out.print("Elements in c: ");
		for(int  i = 0;i < c.length;i++)
			System.out.print(c[i]+ " ");
		System.out.println();
	}
	
	
	public static void main(String[] args){
		
		int c;
		Scanner input = new Scanner(System.in);
		Merge test = null;
		do{
			System.out.println("Choose:\n1. Insert elements\n2. Sort\n3. Display\n4. Exit ");
			c = input.nextInt();
			switch(c){
			
			case 1:{
				int N;
				System.out.print("Enter number of elements in both array: ");
				N = input.nextInt();
				int[] a = new int[N];
				System.out.print("Enter " + N +" elements into the array 1: ");
				for(int i = 0;i < N;i++)
					a[i] = input.nextInt();
				int[] b = new int[N];
				System.out.print("Enter " + N +" elements into the array 1: ");
				for(int i = 0;i < N;i++)
					b[i] = input.nextInt();
				
				test = new Merge(a,b);
				break;
			}
			case 2:{
				test.sort();
				break;
				}
			
			case 3:{
				test.display();
			}
			case 4:{
				break;
			}
			
			
			}
			
			
		}while(c!=4);
		input.close();
		
	}
}
