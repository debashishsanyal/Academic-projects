import java.util.Scanner;
import java.util.Random;
//aux initialized only once
public class Merge3 {
	
	public Merge3(){
		
	}

	public static void merge(int[] a,int[] aux,int lo,int mid,int hi){
		int i=lo,j=mid+1,k=lo;
		
		while(k <= hi){
			if( i > mid || j > hi)
			{
				if(i > mid)
				{
					while(j <= hi){
						a[k] = aux[j];
						j++;
						k++;
					}
				}
				else
				{
					while(i <= mid){
						a[k] = aux[i];
						i++;
						k++;
					}
					
				}
				k--;
			}
			else
			{
				if(aux[i]<=aux[j])
				{
					a[k] = aux[i];
					i++;
				}
				else //aux[j] < a[i]
				{
					a[k] = aux[j];
					j++;
				}
			}
			k++;
		}
		
	}
	
	public static void sort(int[] a,int[] aux,int lo,int hi){
		if(hi > lo)
		{
			int mid = (hi + lo)/2;
			sort(aux,a,lo,mid);
			sort(aux,a,mid+1,hi);
			merge(a,aux,lo,mid,hi);
			
		}
		//sort first half
		//sort second half
		//merge the two halves
		//do until hi == lo
	}
	
	public static void sort(int[] a){
		int[] aux = new int[a.length];
		int i;
		
		for(i = 0;i < a.length;i++)
			aux[i] = a[i];
		sort(a,aux,0,aux.length-1);
	}
	
	public static void display(int[] a){
		System.out.print("Elements in a: ");
		for(int  i = 0;i < a.length;i++)
			System.out.print(a[i]+ " ");
		System.out.println();
		
	}
	
	
	public static void main(String[] args){
		
		int c;
		int[] a = null;
		Scanner input = new Scanner(System.in);
		do{
			System.out.println("Choose:\n1. Insert elements\n2. Sort\n3. Display\n4. Exit ");
			c = input.nextInt();
			switch(c){
			
			case 1:{
				int N;
				System.out.print("Enter number of elements in the array: ");
				N = input.nextInt();
				Random r = new Random();
				a = new int[N];
				//System.out.print("Enter " + N +" elements into the array: ");
				for(int i = 0;i < N;i++)
					//a[i] = input.nextInt();
					a[i] = r.nextInt(100);
				break;
			}
			case 2:{
				
				Merge3.sort(a);
				break;
				}
			
			case 3:{
				Merge3.display(a);
			}
			case 4:{
				break;
			}
			
			
			}
			
			
		}while(c!=4);
		input.close();
		
	}
}
