import java.util.Scanner;


public class Binary {
	
	public static void display(int[] a){
		System.out.print("Current array: ");
		for(int i = 0;i < a.length;i++)
			System.out.print(a[i] + " ");
	}
	
	public static int binary(int[] a,int key,int left,int right){
		if(left>right)
			{
					return -1;
			}
		int mid = (left+right)/2;
		if(a[mid] == key)
			return mid+1;
		if(key>a[mid])
		{
			left = mid+1;
			return binary(a,key,left,right);
		}
		else
		{
			right = mid - 1;
			return binary(a,key,left,right);
		}
		
	}
	
	
	public static int find(int[] a,int key){
		return binary(a,key,0,a.length-1);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input =  new Scanner(System.in);
		int c,N;
		int[] a = new int[10];
		for(int  i = 0;i < a.length;i++)
			a[i] = Integer.valueOf(args[i]);
		do{
			System.out.println("\nChoose:\n1. Enter new array\n2. Search"
					+ "\n3. Display array\n4. Exit ");
			c = input.nextInt();
			switch(c){
			case 1:{System.out.println("Enter the number of elements in the array: ");
					N = input.nextInt();
					a = new int[N];
					System.out.println("Enter "+N+" elements into the array: ");
					for(int i=0;i<N;i++)
					{
						a[i] = input.nextInt();
					}
					break;
					
					}
			case 2:{System.out.print("Enter key: ");
					int key = input.nextInt();
					int pos = find(a,key);
					if(pos == -1)
					{
						System.out.print("Not found");
					}
					else
					{
						System.out.println("Found! At position: "+pos);
					}
					
					break;
					}
			case 3:{display(a);
					break;}
			case 4:{
					break;
					}
			default:System.out.println("Invalid input");
					
			}
		}while(c!=4);
		input.close();
		

	}

}
