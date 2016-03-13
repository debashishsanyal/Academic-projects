import java.util.Scanner;
public class QueueLinkedList {
	
	private Queue first = null,last = null;
	
	private class Queue{
		String item;
		Queue next;
	}
	
	public void enqueue(String s){
		
			Queue temp = new Queue();
			temp.item = s;
			temp.next = null;
			if(last!=null){
				last.next = temp;
			}
			else
				first = temp;
			
			last = temp;
		
		
	}
	
	public String dequeue(){
		if (first == null){
			return "ERROR CODE 123";
		}
		String item = first.item;
		if (first == last){
			first = null;
			last = null;
		}
		else{
			first = first.next;
		}
			
		return item;
	}
	
	public void display(){
		if (first == null){
			System.out.println("Empty queue!");
		}
		else{
			Queue temp = first;
			System.out.println("Elements in the queue: ");
			int count = 1;
			while(temp!=last){
				System.out.println("\n"+count+". "+temp.item);
				count+=1;	
				temp = temp.next;
			}
			
			System.out.println("\n"+count+". "+temp.item);
			
		}
		
	}
	
	
	public static void main(String[] args){
		QueueLinkedList test = new QueueLinkedList();
		Scanner input = new Scanner(System.in);
		int c;
		do{
			System.out.println("Enter choice:\n1. Enqueue\n2. Dequeue\n3. Display\n4. Exit ");
			c = input.nextInt();
			switch(c){
			case 1: {
				System.out.println("Enter string to enqueue: ");
				input.nextLine();
				String s = input.nextLine();
				test.enqueue(s);
				break;
			}
			case 2: {
				String item = test.dequeue();
				if (item.equals("ERROR CODE 123"))
					System.out.println("ERROR");
				else
				System.out.println("Dequeue-ed item: "+item);
				break;
			}
			case 3: {
				test.display();
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
