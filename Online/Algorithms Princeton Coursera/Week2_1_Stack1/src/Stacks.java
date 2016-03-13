import java.util.Scanner;


public class Stacks {
	
	private String[] a = new String[100];
	private int count = 0;
	
	public Stacks(){
		
	}
	
	public Stacks(String s){
		a[count] = s;
		count += 1;
		
	}
	
	public void push(String s){
		a[count] = s;
		count += 1;
	}
	
	public String pops(){
		String item = a[count--];
		a[count] = null;
		return item;
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner input = new Scanner(System.in);
		StackArrayIterable stack = new StackArrayIterable();
		String s = " ";
		String a = "What";
		System.out.println(a);
		while(s!=""){
			System.out.print("Enter string: ");
			s = input.nextLine();
			if(!s.equals("-"))
				stack.push(s);
			else
				System.out.println(stack.pops());
			
			
		}
		
		input.close();
	}

}
