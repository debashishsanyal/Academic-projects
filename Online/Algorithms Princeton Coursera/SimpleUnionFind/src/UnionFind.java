import java.util.Scanner;


public class UnionFind {
	private int[] array = null;
	
	public UnionFind(int N){
		array = new int[N];
		for(int i = 0;i < array.length;i++)
			array[i] = i;
			
	}
	
	public void union(int a,int b){
		if (connected(a,b))
			System.out.println("Already connected");
		else{
	
				int temp = array[a];
				for(int i = 0;i < array.length;i++)
					if(array[i] == temp)
						array[i] = array[b];
	}
		
	}
	
	public boolean connected(int a,int b){
		return (array[a] == array[b]);
		
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
		UnionFind test = new UnionFind(N);
		
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
