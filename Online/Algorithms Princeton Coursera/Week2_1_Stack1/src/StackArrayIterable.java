import java.util.Scanner;
import java.util.Iterator;

public class StackArrayIterable<Item> implements Iterable<Item>{
	
	private Item[] a = (Item[]) new Object[1];
	public int count = 0;
	
	@Override
	public Iterator<Item> iterator(){
		return new ArrayIterator();
	}
	
	public class ArrayIterator implements Iterator<Item>{
		@Override
		
		
		public boolean hasNext(){
			return count > 0;
		}
		
		@Override
		public Item next(){
			return a[--count];
		}
	}
	
	public void newSize(int capacity){
		Item[] temp = (Item[]) new Object[capacity];
		for(int i = 0;i < count;i++)
			temp[i] = a[i];
		a = temp;
	}
	/*public StackArrayIterable(Item s){
		
		a[count] = s;
		count += 1;
		
	}
	
	public StackArrayIterable() {
		// TODO Auto-generated constructor stub
	}*/
	public void push(Item s){
		if(count == a.length) 
			newSize(2*count);
		a[count] = s;
		count += 1;
	}
	
	public Item pops(){
		
		Item item = a[--count];
		a[count] = null;
		if(count == a.length/4)
			newSize(count);
		return item;
		
	}
	
	public int getSize(){
		return a.length;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner input = new Scanner(System.in);
		StackArrayIterable<String> stack = new StackArrayIterable<String>();
		String s = " ";
		while(!s.equals("")){
			System.out.print("Enter string: ");
			s = input.nextLine();
			if(!s.equals("-"))
				{
					stack.push(s);
					System.out.println("Current size: "+stack.getSize()+", count = "+ stack.count);
				}
			else
				{
				System.out.println(stack.pops());
				System.out.println("Current size: "+stack.getSize()+", count = "+ stack.count);
				
				}
			
			
		}
		int count = 0;
		stack.pops();
		for(String s1: stack)
			System.out.println(++count + ". "+s1);
		
		input.close();
	}

}
