import java.util.Scanner;


public class QuickUnion {
	private int[] array = null;
	
	public QuickUnion(int N){
		array = new int[N];
		for(int i = 0;i < array.length;i++)
			array[i] = i;
			
	}
	
	public int root(int a){
		int i = array[a];
		while(array[i] != i)
			i = array[i];
		return i;
		
	}
	
	public void union(int p,int q){
		if (connected(p,q))
			System.out.println("Already connected");
		else{
	
				array[p] = root(q);
	}
		
	}
	
	public boolean connected(int a,int b){
		return (root(a) == root(b));
		
	}
	
	public void display(){
		System.out.print("Current array: ");
		for(int i = 0;i < array.length;i++)
			System.out.print(array[i]+ " ");
		System.out.println();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		System.out.print("How many elements in the array? ");
		int N = input.nextInt();
		int c;
		Islands2 test = new Islands2(N);
		
		do{
			System.out.println("\nChoose:\n1. Union\n2. Find\n3. Display\n4. Exit ");
			c = input.nextInt();
			switch(c){
			case 1:{System.out.println("Enter first element: ");
					int a  = input.nextInt();
					System.out.println("Enter second element: ");
					int b  = input.nextInt();
					test.union(a,b);
					break;
					}
			case 2:{System.out.println("Enter first element: ");
					int a  = input.nextInt();
					System.out.println("Enter second element: ");
					int b  = input.nextInt();
					if(test.connected(a,b))
						System.out.println("Connected");
					else
						System.out.println("Not connected");
					break;}
			case 3:{test.display();break;}
			case 4:{break;}
			
			}
		}while(c!=4);
		input.close();
	}

}
