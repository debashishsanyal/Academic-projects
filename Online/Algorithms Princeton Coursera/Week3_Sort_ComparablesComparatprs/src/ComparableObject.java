import java.util.Scanner;


public class ComparableObject{
	
	//private int[] a =  null;
	

	
	public ComparableObject(){
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
			if(less(a[i+1],a[i]))
				return false;
		return true;
	}
	

	
	public static void display(Student[] a){
		System.out.println("Elements in the array:");
		for(int  i = 0;i < a.length;i++)
		{
			System.out.println((i+1) + ". Name: " + a[i].name);
			System.out.println("Age: " + a[i].age);
			System.out.println();
			
		}
			
		System.out.println();
	}
	
	
	public static void main(String[] args){
		
		int c;
		Scanner input = new Scanner(System.in);
		ComparatorSort b = null;
		Student[] a = new Student[10];
		do{
			System.out.println("Choose:\n1. Enter Data\n2. Sort\n3. Display\n4. Check sorted\n5. Exit ");
			c = input.nextInt();
			switch(c){
			
			case 1:{
				int N;
				System.out.print("Enter number of elements: ");
				N = input.nextInt();
				a = new Student[N];
				String name;
				int age;
				System.out.println("Enter " + N +" elements into the array:");
				for(int i = 0;i < N;i++)
				{
					
					input.nextLine();
					System.out.print("Enter name for student" + (i+1) + ": ");
					name = input.nextLine();
					System.out.println("Enter age for student" + (i+1) + ": ");
					age = input.nextInt();
					a[i] = new Student(name,age);
					
				}
					
				
				break;
			}
			case 2:{
				ComparableObject.sort(a);
				break;
				}
			
			case 3:{
				ComparableObject.display(a);
				break;
			}
			case 4:{
				if (ComparableObject.isSorted(a))
					System.out.println("Sorted");
				else
					System.out.println("Not");
				break;
			}
			
			
			}
			
			
		}while(c!=5);
		input.close();
		
	}

	
}
