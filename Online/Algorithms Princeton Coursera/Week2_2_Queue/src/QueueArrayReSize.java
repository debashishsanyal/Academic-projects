import java.util.Scanner;

public class QueueArrayReSize {
	
	
	private int N = 1;
	private String[] a = new String[N];
	private int head = 0;
	private int tail = 0;
	
	public int size(){

		System.out.println("Head = "+head+", tail = "+ tail);
		return a.length;
	}
	
	public void enqueue(String s){
		if (tail == N){
			if(head == 0){
				resize(2*N);
				a[tail] = s;
				tail++;
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
	
	public void resize(int capacity){
		String[] temp = new String[capacity];
		for(int i = 0;i < tail - head;i++)
			temp[i] = a[head+i];
		
		a = temp;
		tail = tail - head;
		head = 0;
		N = capacity;
				
	}
	
	public String dequeue(){
		if(a[head] == null){
			System.out.println("EMPTY QUEUE");
			return "error";
		}
		
		String item = a[head];
		a[head] = null;
		
		if (a.length==1)
		{
			head = 0;
			tail = 0;
		}
		else
		{
			head++;
			if (tail - head == a.length/4)
				resize(a.length/4);
		}
			
		
		
		return item;
	}
	
	public void display(){
		if(a[head] == null)
			System.out.println("Nothing to display");
		else{
			System.out.println("Elements in the array: ");
			for(int i = head;i < tail;i++)
				System.out.println(a[i]+" ");
			System.out.println("Tail: "+tail);
			System.out.println("Head: "+head);
		}
	}
	
	
	public static void main(String[] args){
		QueueArrayReSize test = new QueueArrayReSize();
		Scanner input = new Scanner(System.in);
		int c;
		do{
			System.out.println("Enter choice:\n1. Enqueue\n2. Dequeue\n3. Display\n4. Size\n5. Exit " + (1/4));
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
				System.out.println("Current size: "+test.size());
				break;
			}
			case 4: {
				System.out.println("Current size: "+test.size());
				
				break;
			}
			case 5: {
				break;
			}
			
			
			}
			
		}while(c!=5);
		input.close();
		
	}
}
