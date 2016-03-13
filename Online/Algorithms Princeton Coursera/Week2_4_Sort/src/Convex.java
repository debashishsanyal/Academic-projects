/*
 * Objective: set of points on a plane. find the set of points that when connected
 * enclose all points on the plane
 * 
 * The algorithm:
 * 
 * 1) get a set of points (x,y)
 * 2) find the lowest point p
 * 3) Sort the points in order of polar angle formed with p, with p being the beginning
 * 4) connect the immediate next two points in the sort, and push each point connected onto hull
 * , starting with p. Check last 3 points connected 
 * traverse CCW
 * if not, discard the LAST point form the hull.
 */
import java.util.Scanner;
public class Convex {
	private int x;
	private int y;
	private char letter;
	
	
	public Convex(int x,int y,char letter){
		this.x = x;
		this.y = y;
		this.letter = letter;
		
	}
	
	public Convex(Convex p){
		this.x = p.x;
		this.y = p.y;
		this.letter = p.letter;
	}
	
	public void setCoordinates(int x,int y,char letter){
		this.x = x;
		this.y = y;
		this.letter = letter;
	}
	
	public static double polar(Convex p,Convex q){
		int center_x = p.x;
		int center_y = p.y;
		int touch_x = q.x;
		int touch_y = q.y;
		
		int delta_x = touch_x - center_x;
		int delta_y = touch_y - center_y;
		double theta_radians = Math.atan2((double)delta_y,(double)delta_x);
		return theta_radians;
	}
	
	public static void sortPointsByPolar(Convex[] p){
		int i,j;
		Convex temp;
		int lowest = findLowestY(p);
		for(i = 0;i< p.length-1;i++)
		{	
			j = i+1;
			temp = p[j];
			while( j > 0 && (polar(p[lowest],p[j-1])>polar(p[lowest],temp)))
				//if p[j-1] has a greater polar angle, then swap with 
			{
				p[j] = p[j-1];
				j--;
			}
			//System.out.println("j = "+j);
			p[j] = temp;
			
			
		}
	}
	
	static public int findLowestY(Convex[] p){
		int pos = 0;
		for(int i = 1;i < p.length;i++)
			if( p[i].x < p[pos].x)
				pos  = i;
		return pos;
		
	}
	
	public static boolean ccw(Convex a,Convex b,Convex c){
		double area2 = (b.x - a.x)* (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
		return area2 >= 0;
	}
	
	public static Convex[] findHull(Convex[] p){
		sortPointsByPolar(p);
		int count = 0;
		Convex[] hull = new Convex[p.length];
		hull[count++] = new Convex(p[0]);
		hull[count++] = new Convex(p[1]);
		
		
		for(int i = 2;i < p.length;i++)
		{	//add next point to hull
			System.out.println("i ="+i);
			hull[count++] = new Convex(p[i]);
			while(!ccw(hull[count-3],hull[count-2],hull[count-1]))
			{
				//while the last three point in hull isnt counter clockwise.
				//pop 2, insert i
				System.out.println("count ="+count);
				count-=2;
				hull[count++] = p[i];
				
			}
			
		}
		
		Convex[] hull2 = new Convex[count];
		for(int  i = 0;i < count; i++)
		{
			hull2[i] = hull[i];
		}
		return hull2;
	}
	
	public static void displayHull(Convex[] p){
		System.out.println("Points belonging to the outer boundary: ");
		for(int i = 0;i < p.length;i++){
			System.out.println((i+1) + ". (" + p[i].x + "," + p[i].y + "), " + p[i].letter );
		}
	}
	
	public static void displayPoints(Convex[] p){
		System.out.println("Points in the plane: ");
		for(int i = 0;i < p.length;i++){
			System.out.println((i+1) + ". (" + p[i].x + "," + p[i].y + "), " + p[i].letter );
		}
		
	}
	
	public static void displayGraphs(Convex[] p){
		//Display ## everywhere
		//except display the corresponding letter when the coordinate matches with any point
		int i,j,k,t;
		int size = 20;
		for(i = 0 ;i < size;i++)
		{
			for(j = 0;j < size;j++)
			{	t = 0;
				for(k = 0;k < p.length;k++)
					if( i == p[k].x && j == p[k].y )
						{
							System.out.print(p[k].letter);
							t = 1;
							break;
						}
				if (t==0){
					System.out.print("#");
				}
					
						
			}
			System.out.println();
		}
		
		
	}
	
	public static void main(String[] args){
		
		Scanner input = new Scanner(System.in);
		int c,N;
		Convex[] test = null;
		Convex[] hull = null;
		do{
			System.out.println("Choose:\n1. Enter points\n2. Find Hull\n3. Display Points"
					+ "\n4. Display Hull\n5. Polar Sort\n6. Display Graphs\n7. Exit");
			c = input.nextInt();
			switch(c){
			case 1:{
				System.out.println("Enter the number of points: ");
				N = input.nextInt();
				test = new Convex[N];
				for(int i = 0;i < N;i++)
				{
					System.out.println("Enter x for point " + (i+1) + ":");
					int x = input.nextInt();
					System.out.println("Enter y for point " + (i+1) + ":");
					int y = input.nextInt();
					char a =(char) (65 + i);
					test[i] = new Convex(x,y,a);
					
				}
				break;
				
			}
			case 2:{
				hull = Convex.findHull(test);
				break;
				
			}
			case 3:{
				Convex.displayPoints(test);
				break;
				
			}
			case 4:{
				Convex.displayHull(hull);
				break;
				
			}
			case 5:{
				Convex.sortPointsByPolar(test);
				break;
				
			}
			case 6:{
				Convex.displayGraphs(test);
				break;
				
			}
			
			}
		}while(c!=7);
		
		input.close();
		
	}
	
	

}
