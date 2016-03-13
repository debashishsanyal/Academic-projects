import java.util.Scanner;

public class ComparableBubble{
	
	//private int[] a =  null;
	
	public ComparableBubble(){
	}

	public static void sort(Comparable[] a){
		int i,pos,j;
		Comparable temp;
		for(i = 0;i< a.length;i++)
		{pos = i;
			for(j = i;j < a.length;j++)
			{
				if(less(a[j],a[pos]))
				{
					pos = j;
				}
			}
			if(pos != i)
			{
				exch(a,i,pos);
			}
			
		}
	}
	
	//returns True if w less than v
	public static boolean less(Comparable v,Comparable w)
	{
		return (v.compareTo(w) < 0);
	}
	
	public static void exch(Comparable[] a,int i,int j){
		Comparable swap;
		swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}
	
	public static boolean isSorted(Comparable[] a){
		for(int i = 0; i < a.length-1; i++)
			if(less(a[i],a[i+1]))
				return false;
		return true;
	}
	/*
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}*/
	
	
	
	public static void display(Comparable[] a){
		System.out.print("Elements in the array: ");
		for(int  i = 0;i < a.length;i++)
			System.out.print(a[i]+ " ");
		System.out.println();
	}
	
	
	public static void main(String[] args){
		
		int c;
		Scanner input = new Scanner(System.in);

		Integer[] a = new Integer[10];
		do{
			System.out.println("Choose:\n1. Insert elements\n2. Sort\n3. Display\n4. Exit ");
			c = input.nextInt();
			switch(c){
			
			case 1:{
				int N;
				System.out.print("Enter number of elements: ");
				N = input.nextInt();
				a = new Integer[N];
				System.out.print("Enter " + N +" elements into the array: ");
				for(int i = 0;i < N;i++)
					a[i] = input.nextInt();
				
				break;
			}
			case 2:{
				ComparableBubble.sort(a);
				break;
				}
			
			case 3:{
				ComparableBubble.display(a);
			}
			case 4:{
				break;
			}
			
			
			}
			
			
		}while(c!=4);
		input.close();
		
	}

	
}
