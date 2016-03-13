import java.util.Scanner;

public class BinaryHeap<Item extends Comparable<Item>> {
	
	private Item[] heap = (Item[]) new Student[15];
	private int count = 0;
	
	private class Queue{
		Item item;
		Queue next;
	}
	
	public int getCount(){
		return count;
	}
	
	public Item getItem(int i){
		return heap[i];
	}
	
	public static boolean less(Comparable v,Comparable w){
		return v.compareTo(w) < 0;
	}
	
	public void swim(int n){
		int  i = n;
		while(i > 1 && (less(heap[i/2],heap[i]))){
			exch(heap,i,i/2);
			i = i/2;
		}
	}
	
	public Item delMax(){
		
		Item del = heap[1];
		exch(heap,1,count--);
		heap[count+1] = null;
		sink(1);
		return del;
		
	}
	
	public void sink(int k){
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
	

	public void insert(Item s){
		heap[++count] = s;
		swim(count);
		
	}
	
	
	
	public void exch(Item[] v,int i,int j){
		Item  temp;
		temp = v[i];
		v[i] = v[j];
		v[j] = temp;
	}
	
	public static void main(String[] args){
		BinaryHeap<Student> test = new BinaryHeap<Student>();
		Scanner input = new Scanner(System.in);
		int c;
		do{
			System.out.println("Enter choice:\n1. Insert\n2. Remove\n3. Display\n4. Exit ");
			c = input.nextInt();
			switch(c){
			case 1: {
				System.out.println("Enter name of student: ");
				input.nextLine();
				String s = input.nextLine();
				
				System.out.println("Enter age of student: ");
				int age = input.nextInt();
				Student st = new Student(s,age);
				test.insert(st);
				break;
			}
			case 2: {
				Student s = test.delMax();
				System.out.println("Name of student: " + s.name);
				System.out.println("Age of student: " + s.age);
				System.out.println();
				
				
				break;
			}
			case 3: {
				for(int i = 1;i <= test.getCount();i++)
					{
						test.getItem(i).display();	
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
