import java.util.Scanner;
import java.util.Iterator;

public class StackLinkedGenericIterable<Item> implements Iterable<Item>{
	
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
		String s;
		Scanner input = new Scanner(System.in);
		StackLinkedGenericIterable<String> test = new StackLinkedGenericIterable<String>();
		do{
			System.out.println("Choose:\n1. Push\n2. Pop\n3. Check isEmpty\n4. Display\n5. Exit ");
			c = input.nextInt();
			switch(c){
			
			case 1:{
				System.out.print("Enter string to push:");
				input.nextLine();
				s = input.nextLine();
				test.push(s);
				break;
			}
			case 2:{
				System.out.println("Element popped: "+test.pop());
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
				for (String s1 : test)
					System.out.println(++count + "."+s1);
				Iterator<String> i = test.iterator();
				while(i.hasNext()){
					s = i.next();
					System.out.println(++count + "."+s);
					
				}
				break;
			}
			
			
			}
			
			
		}while(c!=5);
		input.close();
		
	}


}