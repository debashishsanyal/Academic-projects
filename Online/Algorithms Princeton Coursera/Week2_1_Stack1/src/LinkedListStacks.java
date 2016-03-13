import java.util.Scanner;


public class LinkedListStacks {
	
	private Node first = null;
	
	private class Node{
		String item;
		Node next;
	}
	
	public void push(String s){
		Node temp = new Node();
		temp.next = first;
		temp.item = s;
		first = temp;
		
	}
	
	public String pop(){
		if(isEmpty())
			{System.out.println("Empty Stack!"); return null;}
		String temp = first.item;
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
		LinkedListStacks test = new LinkedListStacks();
		do{
			System.out.println("Choose:\n1. Push\n2. Pop\n3. Check isEmpty\n4. Exit ");
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
				break;
			}
			
			}
			
			
		}while(c!=4);
		input.close();
		
	}

}
