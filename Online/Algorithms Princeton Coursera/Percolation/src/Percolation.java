import java.util.Scanner;


/**
 * This program allows a user to generate a 2D surface, create 'holes' in them and check
 * for 'percolation' (i.e., is the top connected to the bottom by a path consisting entirely
 * of holes?). Though the purpose of this program is to be used by another program to simulate 
 * a large number of such surfaces, a user may call this program directly, and create a 2D 
 * surface, make 'holes' in the surface, and check for percolation. The user may also display
 * the state of the surface at each stage by choosing 'display' from the menu.
 * @author Hemant
 * @version 1.0
 */


public class Percolation {
	private int sizeN;
	private int[][] theGrid = null;
	private WeightedQuickUnionUF uf = null;
	
   /** Constructor for Percolation.
	* 
	* @param sizeN			Dimension of 2D surface to be created.
	*/
   public Percolation(int N) {
	   // create N-by-N grid, with all sites blocked
	   theGrid = new int[N][N];
	   uf = new WeightedQuickUnionUF(N*N+2); 
	   this.sizeN = N;
	   for(int iCoord = 0;iCoord < N;iCoord++)
		   for(int jCoord = 0;jCoord < N;jCoord++)
			   theGrid[iCoord][jCoord] = 1; //1 means the site is blocked, 0 means open
   }
   
   /** Opens site at the given coordinates and connects the new hole to adjacent holes
    * via UnionFind algorithm.
	* 
	* @param iCoord			x coordinate of the site to be opened
	* @param jCoord			y coordinate of the site to be opened
	* 
	*/
   public void open(int iCoord, int jCoord) {
	   // open site (row i, column j) if it is not open already
	   if(isOpen(iCoord,jCoord))
		   System.out.println("Already open");
	   else{
		   int i1 = iCoord;
		   int j1 = jCoord;
		   iCoord = iCoord - 1;
		   jCoord = jCoord - 1;
		   int p = (iCoord)*sizeN+(jCoord)+1;
		   int q;
		   theGrid[iCoord][jCoord] = 0;
		   
		   
		   if (iCoord == 0) {
			   uf.union(p, 0);
			   if(jCoord == 0){
				   if(isOpen(i1,j1+1)){ q = (iCoord)*sizeN+(jCoord+1)+1; uf.union(p, q); }
				   if(isOpen(i1+1,j1)){ q = (iCoord+1)*sizeN+(jCoord)+1; uf.union(p, q); }
				   
			   }
			   else if(jCoord == sizeN-1){
				   if(isOpen(i1,j1-1)){ q = (iCoord)*sizeN+(jCoord-1)+1; uf.union(p, q); }
				   if(isOpen(i1+1,j1)){ q = (iCoord+1)*sizeN+(jCoord)+1; uf.union(p, q); }
				   
			   }
			   else{
				   if(isOpen(i1,j1-1)){ q = (iCoord)*sizeN+(jCoord-1)+1; uf.union(p, q); }
				   if(isOpen(i1,j1+1)){ q = (iCoord)*sizeN+(jCoord+1)+1; uf.union(p, q); }
				   if(isOpen(i1+1,j1)){ q = (iCoord+1)*sizeN+(jCoord)+1; uf.union(p, q); }
				   
			   }
			   
		   }
		   else if (iCoord == sizeN-1) {
			   uf.union(p, sizeN*sizeN+1);
			   if (jCoord == 0) {
				   if(isOpen(i1-1,j1)){ q = (iCoord-1)*sizeN+(jCoord)+1; uf.union(p, q); }
				   if(isOpen(i1,j1+1)){ q = (iCoord)*sizeN+(jCoord+1)+1; uf.union(p, q); }
				   
			   }
			   else if(jCoord == sizeN-1){
				   
				   if(isOpen(i1,j1-1)){ q = (iCoord)*sizeN+(jCoord-1)+1; uf.union(p, q); }
				   if(isOpen(i1-1,j1)){ q = (iCoord-1)*sizeN+(jCoord)+1; uf.union(p, q); }
			   }
			   else{
				   if(isOpen(i1,j1-1)){ q = (iCoord)*sizeN+(jCoord-1)+1; uf.union(p, q); }
				   if(isOpen(i1,j1+1)){ q = (iCoord)*sizeN+(jCoord+1)+1; uf.union(p, q); }
				   if(isOpen(i1-1,j1)){ q = (iCoord-1)*sizeN+(jCoord)+1; uf.union(p, q); }
				   
			   }
		   }
		   else if (jCoord == 0){
			   if(isOpen(i1-1,j1)){ q = (iCoord-1)*sizeN+(jCoord)+1; uf.union(p, q); }
			   if(isOpen(i1+1,j1)){ q = (iCoord+1)*sizeN+(jCoord)+1; uf.union(p, q); }
			   if(isOpen(i1,j1+1)){ q = (iCoord)*sizeN+(jCoord+1)+1; uf.union(p, q); }
		   }
		   else if (jCoord == sizeN-1){
			   if(isOpen(i1-1,j1)){ q = (iCoord-1)*sizeN+(jCoord)+1; uf.union(p, q); }
			   if(isOpen(i1+1,j1)){ q = (iCoord+1)*sizeN+(jCoord)+1; uf.union(p, q); }
			   if(isOpen(i1,j1-1)){ q = (iCoord)*sizeN+(jCoord-1)+1; uf.union(p, q); }
		   }
		   else{
			   if(isOpen(i1-1,j1)){ q = (iCoord-1)*sizeN+(jCoord)+1; uf.union(p, q); }
			   if(isOpen(i1+1,j1)){ q = (iCoord+1)*sizeN+(jCoord)+1; uf.union(p, q); }
			   if(isOpen(i1,j1-1)){ q = (iCoord)*sizeN+(jCoord-1)+1; uf.union(p, q); }
			   if(isOpen(i1,j1+1)){ q = (iCoord)*sizeN+(jCoord+1)+1; uf.union(p, q); }
			   
		   }
	   }
	   
   }
   
   /** Returns true if the site at the given coordinate is a 'hole' (open), else false.
	* 
	* @param iCoord			x coordinate of the site to be checked
	* @param jCoord			y coordinate of the site to be checked
	* 
	* @return 				whether or not the the site at the give coordinate is open.
	* 
	*/
   public boolean isOpen(int i, int j) {
	   // is site (row i, column j) open?
	   i = i - 1;
	   j = j - 1;
	   return theGrid[i][j] == 0; //0 indicates site is open
	   
   }
   
   /** Returns true if the site at the given coordinate is connected to the top, else false.
	* 
	* @param iCoord			x coordinate of the site to be checked
	* @param jCoord			y coordinate of the site to be checked
	* 
	* @return 				whether or not the the site at the give coordinate is connected to the top.
	* 
	*/
   public boolean isFull(int i, int j) {
	   // is site (row i, column j) full?
	   i = i - 1;
	   j = j - 1;
	   int p = i*sizeN+j+1;
	   return uf.connected(p, 0);
	   
	   
   }
   
   /**
    * To check if the surface percolates (i.e. is the top connected to the bottom by a path
    * consisting entirely of holes (open sites)?
    * @return 			True if the surfaces percolates, else false.
    */
   public boolean percolates() {
	   // does the system percolate?
	   return uf.connected(sizeN*sizeN+1, 0);
   }

   /**
    * Main program Percolation. Consists of a user driven menu.
    */
   public static void main(String[] args){
	   // test client (optional)
	   Scanner input = new Scanner(System.in);
	   int sizeN,user_choice,iCoord,jCoord;
	   Percolation test = null;
	   System.out.println("Size of matrix? ");
	   sizeN = input.nextInt();
	   test = new Percolation(sizeN);
	   
	   do{ System.out.println("Choose:\n1. Enter matrix size\n2. Open site\n3. Check isOpen"
	   		+ "\n4. Check isFull\n5. Check percolates\n6. Display\n7. Exit ");
	   user_choice = input.nextInt();
	   switch(user_choice){
	   case 1:{
		   System.out.println("Size of matrix? ");
		   sizeN = input.nextInt();
		   test = new Percolation(sizeN);
		   break;
	   }
	   case 2:{
		   System.out.println("Enter i: ");
		   iCoord = input.nextInt();
		   System.out.println("Enter j: ");
		   jCoord = input.nextInt();
		   test.open(iCoord,jCoord);
		   break;
	   }
	   case 3:{
		   System.out.println("Enter i: ");
		   iCoord = input.nextInt();
		   System.out.println("Enter j: ");
		   jCoord = input.nextInt();
		   if(test.isOpen(iCoord,jCoord))
			   System.out.println("Site is open");
		   else
			   System.out.println("Site is closed");
		   break;
	   }
	   case 4:{
		   System.out.println("Enter i: ");
		   iCoord = input.nextInt();
		   System.out.println("Enter j: ");
		   jCoord = input.nextInt();
		   if(test.isFull(iCoord,jCoord))
			   System.out.println("Is Full");
		   else
			   System.out.println("Not Full");
		   break;
	   }
	   case 5:{
		   if(test.percolates())
			   System.out.println("Percolates");
		   else
			   System.out.println("Does not percolates");
		   break;
	   }
	   case 6:{
		   System.out.println("Current state: ");
		   for(iCoord = 1;iCoord <= sizeN;iCoord++)
		   {
			   System.out.println();
			   for(jCoord = 1;jCoord <= sizeN;jCoord++)
			   {
				   if(test.isOpen(iCoord, jCoord))
				   {
					   System.out.print("O ");
				   }
				   else
					   System.out.print("# ");
			   }
			   
		   }
		   System.out.println();
			   
			   
		   break;
	   
	   }
	   
	   }
	   		  
	   }while(user_choice!=7);
	   input.close();
   }
}