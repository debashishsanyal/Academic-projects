import java.util.Scanner;
import java.util.Iterator;

public class ObjectIterate<Item> implements Iterable<Item>{
	
	public class Node{
		Item item;
		Node next;
	}
	
	
	Node first = null;
	@Override
	public Iterator<Item> iterator(){  
		return new ListIterator();
	}
	
	public class ListIterator implements Iterator<Item>{
		
		private Node current = first;
		@Override
		public boolean hasNext(){
			return current != null;
			
		}
		@Override
		public Item next(){
			Item temp = current.item;
			current = current.next;
			return temp;
		}
		
		
		
		
	}
	public void push(Item s){
		Node temp = new Node();
		temp.next = first;
		temp.item = s;
		first = temp;
		
	}
	
	public Item pop(){
		if(isEmpty())
			{System.out.println("Empty Stack!"); return null;}
		Item temp = first.item;
		first = first.next;
		return temp;
	}
	
	public boolean isEmpty(){
		return first == null;
		
	}
	
	public static void main(String[] args){
		int c;
		Scanner input = new Scanner(System.in);
		ObjectIterate<Car> test = new ObjectIterate<Car>();
		do{
			System.out.println("Choose:\n1. Push\n2. Pop\n3. Check isEmpty\n4. Display\n5. Exit ");
			c = input.nextInt();
			switch(c){
			
			case 1:{
				System.out.print("Enter width:");
				int w = input.nextInt();
				System.out.print("Enter height:");
				int h = input.nextInt();
				Car test2 = new Car();
				test2.setParam(w, h);
				test.push(test2);
				break;
			}
			case 2:{
				Car temp = test.pop();
				if(temp!=null)
				{
					System.out.println("Element popped: ");
					System.out.println("Car");
					System.out.println("Height = "+ temp.getHeight());
					System.out.println("Width = " + temp.getWidth());
				}
				
				break;
			}
			case 3:{
				if(test.isEmpty())
					System.out.println("Empty Stack");
				else
					System.out.println("No not empty");	
				break;
			}
			case 4:{
				int count=0;
				/*
				for (Car s1 : test)
				{
					System.out.println(++count + ". Car" + count);
					System.out.println("Height = "+ s1.height);
					System.out.println("Width = " + s1.width);
					System.out.println();
					
				}*/
				
				Car s1 = null;
				Iterator<Car> i = test.iterator();
				while(i.hasNext()){
					s1 = i.next();
					System.out.println(++count + ". Car" + count);
					System.out.println("Height = "+ s1.getHeight());
					System.out.println("Width = " + s1.getWidth());
					System.out.println();
				}
				
				break;
			}
			
			
			}
			
			
		}while(c!=5);
		input.close();
		
	}


}