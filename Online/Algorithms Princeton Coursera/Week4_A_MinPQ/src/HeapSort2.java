import java.util.Scanner;

public class HeapSort2<Item extends Comparable<Item>> {
	
	private static int count = 0;
	
	public int getCount(){
		return count;
	}
	
	public static boolean less(Comparable v,Comparable w){
		return v.compareTo(w) < 0;
	}
	
	public static void swim(int n,Item[] heap){
		int  i = n;
		while(i > 1 && (less(heap[i/2],heap[i]))){
			exch(heap,i,i/2);
			i = i/2;
		}
	}
	
	public static Item delMax(Item[] heap){
		
		Item del = heap[1];
		exch(heap,1,count--);
		heap[count+1] = null;
		sink(heap,1);
		return del;
		
	}
	
	public static void sink(Item[] heap,int k){
		//while 2*k exists
			//if 2*k+1 exists, find the greater of 2k and 2k+1
				//if greater is greater than k, exchange heap,k,greater
			//else if 2k is greater, exchange  heap,k,2k
		int temp;
		while(2*k <= count){ //while k has a child
			if(2*k + 1 <= count) //if k also has a second child
			{
				if(less(heap[2*k],heap[2*k+1])) //find the bigger child
				{
					temp = 2*k+1;
				}
				else
				{
					temp = 2*k;
				}
				if(less(heap[k],heap[temp])) //if the bigger child is bigger than k
					exch(heap,temp,k);
				else 
					break;
			}
			else if(less(heap[k],heap[2*k])) //if the single child is bigger than k
				exch(heap,k,2*k);
			else
				break;
		}
	}
	

	public static void insert(Item s,Item[] heap){
		heap[++count] = s;
		swim(count,heap);
		
	}
	
	
	
	public static void exch(Item[] v,int i,int j){
		Item temp;
		temp = v[i];
		v[i] = v[j];
		v[j] = temp;
	}
	
	public static void main(String[] args){
		HeapSort2<Student> test = new HeapSort2<Student>();
		Student[] s = null;
		int N = 0;
		Scanner input = new Scanner(System.in);
		int c;
		do{
			System.out.println("Enter choice:\n1. Insert\n2. Sort\n3. Display\n4. Exit ");
			c = input.nextInt();
			switch(c){
			case 1: {
				Student st = null;
				System.out.println("Enter number of elements: ");
				N = input.nextInt();
				s = new Student[N];
				String str = null;
				for(int i = 0;i < N;i++)
				{
					input.nextLine();
					System.out.println("Enter name of student: ");
					str = input.nextLine();
					System.out.println("Enter age of student: ");
					int age = input.nextInt();
					st = new Student(str,age);
					//test.insert(st);
					s[i] = st;
				}
				break;
			}
			case 2: {
				
				break;
			}
			case 3: {
				for(int i = 0;i < N;i++)
					{
						s[i].display();
						System.out.println();
					}
				break;
				}
				
			
			case 4: {
				break;
			}
			
		}
			
		}while(c!=4);
		input.close();
		
	}
}
