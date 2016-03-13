import java.util.Scanner;
import java.util.Comparator;


public class ComparatorSort{
	
	//private int[] a =  null;
	

	
	public ComparatorSort(){
	}
	
	public static void sort(Object[] a,Comparator comparator){
		int i,pos,j;
		Object temp;
		for(i = 0;i< a.length;i++)
		{pos = i;
			for(j = i;j < a.length;j++)
			{
				if(less(comparator,a[j],a[pos]))
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
	
	public static void exch(Object[] a,int i,int j){
		Object swap;
		swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}
	//returns True if w less than v
	public static boolean less(Comparable v,Comparable w)
	{
		return (v.compareTo(w) < 0);
	}
	
	public static boolean less(Comparator c,Object v,Object w)
	{
		return (c.compare(v,w) < 0);
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
		ComparableObject b = null;
		Student[] a = new Student[10];
		do{
			System.out.println("Choose:\n1. Enter Data\n2. Sort\n3. Display\n4. Exit ");
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
				int choice = 0;
				Comparator comp = null;
				while(choice!=1 && choice != 2)
				{
					System.out.println("Choose:\n1. Sort by name\n2. Sort by age");
					choice = input.nextInt();
					if (choice == 1)
					{
						comp = Student.BY_NAME;
					}
					else if (choice == 2)
					{
						comp = Student.BY_AGE;
					}
					else
						System.out.println("INVALID");
				}
				
				
				
				ComparatorSort.sort(a,comp);
				break;
				}
			
			case 3:{
				ComparatorSort.display(a);
			}
			case 4:{
				break;
			}
			
			
			}
			
			
		}while(c!=4);
		input.close();
		
	}

	
}
