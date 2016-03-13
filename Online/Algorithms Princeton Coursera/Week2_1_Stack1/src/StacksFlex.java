import java.util.Scanner;


public class StacksFlex {
	
	private String[] a = new String[1];
	private int count = 0;
	
	public StacksFlex(){
		
	}
	
	public void newSize(int capacity){
		String[] temp = new String[capacity];
		for(int i = 0;i < count;i++)
			temp[i] = a[i];
		a = temp;
	}
	public StacksFlex(String s){
		
		a[count] = s;
		count += 1;
		
	}
	
	public void push(String s){
		if(count == a.length) 
			newSize(2*count);
		a[count] = s;
		count += 1;
	}
	
	public String pops(){
		
		String item = a[--count];
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
		StacksFlex stack = new StacksFlex();
		String s = " ";
		String a = "What";
		System.out.println(a);
		while(s.equals("")){
			System.out.print("Enter string: ");
			s = input.nextLine();
			if(!s.equals("-"))
				{
					stack.push(s);
					System.out.println("Current size: "+stack.getSize());
				}
			else
				{
				System.out.println(stack.pops());
				System.out.println("Current size: "+stack.getSize());
				
				}
			
			
		}
		
		input.close();
	}

}
