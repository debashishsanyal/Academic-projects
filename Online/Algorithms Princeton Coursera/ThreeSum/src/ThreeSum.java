import java.util.Scanner;
import java.util.Random;




public class ThreeSum {
	
	
	
	public static void findSums(int[] a){
		int i,j,k,count=0;
		for(i=0;i< a.length-2;i++)
			for(j=i+1;j<a.length-1;j++)
				for(k=j+1;k<a.length;k++)
					if(a[i]+a[j]+a[k] == 0)
					{
						count+=1;
						//System.out.print(count+": "+a[i] +" + "+ a[j] +" + "+ a[k] +" = 0");
						//System.out.println();
					}
		System.out.println("Total sums = "+count);
	}
	
	public static void display(int[] a){
		System.out.print("Current array: ");
		for(int i = 0;i < a.length;i++)
			System.out.print(a[i] + " ");
	}
	
	public static int[] generateRandom(int N){
		int[] a = new int[N];
		Random r = new Random();
		for(int i = 0;i < N;i++)
			a[i] = r.nextInt(200)-99;
		return a;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		int c,N;
		int[] a = new int[8];
		for(int i = 0;i < 8;i++)
			a[i] = Integer.valueOf(args[i]);
		
		do{
			System.out.println("\nChoose:\n1. Enter new array\n2. Create new random array"
					+ "\n3. Display array\n4. Compute \n5. Exit ");
			c = input.nextInt();
			switch(c){
			case 1:{System.out.println("Enter the number of elements in the array: ");
					N = input.nextInt();
					System.out.println("Enter "+N+" elements into the array: ");
					for(int i=0;i<N;i++)
					{
						a[i] = input.nextInt();
					}
					
					
					}
			case 2:{System.out.print("Enter size of random array: ");
					N = input.nextInt();
					a = new int[N];
					a = generateRandom(N);}
			case 3:{display(a);
					break;}
			case 4:{Stopwatch s = new Stopwatch(); 
					findSums(a);
					double time = s.elapsedTime();
					System.out.println("Elapsed time: "+time);
					break;
					}
					
			case 5:{break;}
			
			}
		}while(c!=5);
		input.close();

	}

}
