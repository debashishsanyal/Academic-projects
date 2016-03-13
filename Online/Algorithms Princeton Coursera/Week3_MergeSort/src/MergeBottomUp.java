import java.util.Scanner;
//aux initialized only once
public class MergeBottomUp {
	
	
	
	
	public MergeBottomUp(){
		
	}

	public static void merge(int[] a,int[] aux,int lo,int mid,int hi){
		int i=lo,j=mid+1,k=lo;
		for(i = lo;i <= hi;i++)
			aux[i] = a[i];
		i = lo;
		
		
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
				else
				{
					a[k] = aux[j];
					j++;
				}
			}
			k++;
		}
		
			
			
	}
	
	
	
	public static void sort(int[] a){
		//merge  pairs 
		
		int k = 2;
		int i,hi,lo,mid,max=a[0];
		while(k<a.length)
			k*=2;
		int[] a_temp = new int[k];
		int[] aux = new int[k];
		for(i = 0;i< k;i++)
		{
			if(i < a.length)
			{	
				a_temp[i] = a[i];
				if(a[i] > max)
					max = a[i];
			}
			else
			{
				a_temp[i]  = max;
			}
				
			
		}
			
		k = 2;
		
		while(k <= a_temp.length)
		{
			for(i = 0;i < a_temp.length;i += k)
			{	
				hi = i+k-1;
				lo=i;
				mid = (hi+lo)/2;
				merge(a_temp,aux,lo,mid,hi);
			}
			k *= 2;
			//merge merged pairs
			//repeat until last pair left
		}
		
		for(i = 0;i < a.length; i++)
			a[i] = a_temp[i];
		
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
				System.out.print("Enter number of elements in both array: ");
				N = input.nextInt();
				a = new int[N];
				System.out.print("Enter " + N +" elements into the array 1: ");
				for(int i = 0;i < N;i++)
					a[i] = input.nextInt();
				break;
			}
			case 2:{
				
				MergeBottomUp.sort(a);
				break;
				}
			
			case 3:{
				MergeBottomUp.display(a);
			}
			case 4:{
				break;
			}
			
			
			}
			
			
		}while(c!=4);
		input.close();
		
	}
}
