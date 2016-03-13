import java.util.Scanner;

public class Selection {
	
	private int[] a =  null;
	
	public Selection(int[] a){
		this.a = a;
	}

	public void sort(){
		int i,pos,j,temp;
		for(i = 0;i< a.length;i++)
		{pos = i;
			for(j = i;j < a.length;j++)
			{
				if(a[j]<a[pos])
				{
					pos = j;
				}
			}
			if(pos != i)
			{
				temp = a[i];
				a[i] = a[pos];
				a[pos] = temp;
			}
			
		}
	}
	
	public void display(){
		System.out.print("Elements in the array: ");
		for(int  i = 0;i < a.length;i++)
			System.out.print(a[i]+ " ");
		System.out.println();
	}
	
	
	public static void main(String[] args){
		
		int c;
		Scanner input = new Scanner(System.in);
		Selection test = null;
		do{
			System.out.println("Choose:\n1. Insert elements\n2. Sort\n3. Display\n4. Exit ");
			c = input.nextInt();
			switch(c){
			
			case 1:{
				int N;
				System.out.print("Enter number of elements: ");
				N = input.nextInt();
				int[] a = new int[N];
				System.out.print("Enter " + N +" elements into the array: ");
				for(int i = 0;i < N;i++)
					a[i] = input.nextInt();
				test = new Selection(a);
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
