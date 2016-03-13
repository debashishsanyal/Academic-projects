import java.util.Scanner;

public class QueueArray {
	
	
	private int N = 10;
	private String[] a = new String[N];
	private int head = 0;
	private int tail = 0;
	
	public void enqueue(String s){
		if (tail == N){
			if(head == 0){
				System.out.println("Array is full!");
			}
			else{
				int temp = head;
				tail -= head;
				head = 0;
				for(int i = 0;i < N;i++)
					if(i < tail)
						a[i] = a[temp+i];
					else
						a[i] = null;
				
				a[tail] = s;
				tail++;
				
				}
			}
		else{
			a[tail] = s;
			tail++;
			
		}
		
	}
	
	public String dequeue(){
		if(a[head] == null){
			System.out.println("ERROR!");
			return "error";
		}
		String item = a[head];
		a[head] = null;
		if (head==tail)
		{
			head = 0;
			tail = 0;
		}
		else
			head++;
		return item;
	}
	
	public void display(){
		if(a[head] == null)
			System.out.println("Nothing to display");
		else{
			System.out.println("Elements in the array: ");
			for(int i = head;i < tail;i++)
				System.out.println(a[i]+" ");
		}
	}
	
	
	public static void main(String[] args){
		QueueArray test = new QueueArray();
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
				if (item.equals("error"))
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
