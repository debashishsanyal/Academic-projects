import java.util.Scanner;

public class MinPQ<Item extends Comparable<Item>> {
	
	private Queue first = null,last = null;
	
	private class Queue{
		Item item;
		Queue next;
	}
	
	public static boolean less(Comparable v,Comparable w){
		return v.compareTo(w) < 0;
	}
	
	public void enqueue(Item s){
		
		Queue temp = new Queue();
		if (first == null)//if empty
		{
			first = temp;
			last = temp;
			first.item = s;
			first.next = null;
		}
		else
		{
			if(first == last)
			{
				temp.item = s;
				if(less(s,first.item))
				{
					temp.next = first;
					first = temp;
				}
				else
				{
					first.next = temp;
					last = temp;
					last.next = null;
				}
			}
			else
			{
				temp = first;
				Queue before = null;
				while ((s.compareTo(temp.item) >= 0) && temp!= last)//s is greater than temp.item, or s
				{
					before = temp;
					temp = temp.next;
				}
				
				if (temp == first)
				{	
					Queue now = new Queue();
					now.item = s;
					if(less(s,first.item))
					{
						now.next = first;
						first = now;
						
					}
					else
					{
						now.next = first.next;
						first.next = now;
					}
				}
				else if (temp == last)
				{	
					System.out.println("Temp is last");
					temp = new Queue();
					temp.item = s;
					if (less(s,last.item))//if s is smaller
					{
						//put s before temp
						before.next = temp;
						temp.next = last;
						System.out.println("S before last");
					}
					else
					{
						//put s at last
						temp.next = null;
						last.next = temp;
						last = temp;
					}
				}
				else
				{
					Queue now = new Queue();
					now.item = s;
					now.next = temp;
					before.next = now;
					
					//put s between temp and temp.next
				}
			}
		}
		
	}
	
	public Item dequeue(){
		if (first == null){
			System.out.println("Empty Queue");
			return null;
		}
		Item item = first.item;
		if (first == last){
			first = null;
			last = null;
		}
		else{
			first = first.next;
		}
			
		return item;
	}
	
	
	public Item show(int i){
		Item send = null;
		if (first == null){
			System.out.println("Empty queue!");
			return send;
		}
		else{
			Queue temp = first;
			int count = 1;
			while(temp!=last){
				
				//send  = temp.item;
				if(count == i)
					{send = temp.item;break;}
				count+=1;	
				temp = temp.next;
			}
			
			if(count == i)
				send = temp.item; 
			
			return send;
			
			
		}
		
	}
	
	
	public static void main(String[] args){
		MinPQ<Student> test = new MinPQ<Student>();
		Scanner input = new Scanner(System.in);
		int c;
		do{
			System.out.println("Enter choice:\n1. Enqueue\n2. Dequeue\n3. Display\n4. Exit ");
			c = input.nextInt();
			switch(c){
			case 1: {
				System.out.println("Enter name of student: ");
				input.nextLine();
				String s = input.nextLine();
				
				System.out.println("Enter age of student: ");
				int age = input.nextInt();
				Student st = new Student(s,age);
				test.enqueue(st);
				break;
			}
			case 2: {
				Student item  = test.dequeue();
				if (item == null)
					System.out.println("ERROR");
				else
				System.out.println("Dequeue-ed item: "+item);
				break;
			}
			case 3: {
				int i = 1;
				System.out.println("Elements in the queue: ");
				Student temp = new Student("what",2);
				temp = test.show(1);
				while(temp!=null){
					
					System.out.print(i + ". ");
					temp.display();
					System.out.println();
					temp = test.show(++i);
						
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
