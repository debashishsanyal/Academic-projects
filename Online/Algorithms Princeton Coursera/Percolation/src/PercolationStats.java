import java.util.Scanner;

/**
 * How much percentage of a given square surface should be empty (at random), such that if 2D 
 * water (imagine) were to be trickled from the top surface, it would surely reach the 
 * bottom bottom surface through the 'emptiness'? This is a dumbed down explanation of the
 * percolation problem.
 * 
 * This program, with use of 'Percolation.java' simulates a number of 2D surfaces
 * and randomly creates 'holes' in them until the top and bottom of the surface are connected
 * by a path consisting completely of holes. Once such a situation is reached, the program 
 * stores the proportion holes/totalSurace. It finally prints the average proportion and its
 * 95% confidence interval.
 * 
 * Both the size of the surface and the number of simulations are provided by the user.
 * @author Hemant
 * @version 1.0
 */

public class PercolationStats {
	
	private double mean;
	private double stdv;
	private double cLo;
	private double cHi;
	
	/** Constructor for PercolationStats.
	 * 
	 * @param sizeN			Dimension of 2D surface to be simulated
	 * @param noOfTests			Number of independent simulations to be performed.
	 */
   public PercolationStats(int sizeN, int noOfTests) {
	   // perform T independent experiments on an N-by-N grid
	   Percolation test = new Percolation(sizeN);
	   double[] ratios = new double[noOfTests];
	   
	   double attempts;
	   for(int count = 0;count < noOfTests; count ++)
	   {	
		   attempts = 0;
		   test = new Percolation(sizeN);
		   while(!test.percolates()) {
			   int i = StdRandom.uniform(sizeN)+1;
			   int j = StdRandom.uniform(sizeN)+1;
			   if(!test.isOpen(i, j))
			   {
				   test.open(i, j);
				   attempts++;
			   }
			   
		   }
		   
	   ratios[count] = attempts/(sizeN*sizeN);
	   //System.out.println(ratios[count]);
	   
	   }
	   mean = StdStats.mean(ratios);
	   stdv = StdStats.stddev(ratios);
	   cLo = mean - (1.96*(stdv))/Math.sqrt(noOfTests);
	   cHi = mean - (1.96*(stdv))/Math.sqrt(noOfTests);
	   
	   
	/** Returns sample mean of percolation threshold
	 * 
	 * @return mean		The sample mean of the percolation threshold.
	 */
   }
   public double mean() {
	   // sample mean of percolation threshold
	   return mean;
   }

	/** Returns sample standard deviation of percolation threshold
	 * 
	 * @return stdv		The sample standard deviation of the percolation threshold.
	 */
   public double stddev() {
	   // sample standard deviation of percolation threshold
	   return stdv;
	   
   }
   /** Returns lower limit of condidence interval.
	 * 
	 * @return cLo		Lower limit of confidence interval
	 */
   public double confidenceLo() {
	   // low  endpoint of 95% confidence interval
	   return cLo;
   }
   
   /** Returns upper limit of condidence interval.
	 * 
	 * @return cHi		Upper limit of confidence interval
	 */
   public double confidenceHi() {
	   // high endpoint of 95% confidence interval
	   return cHi;
   }
   
   /**
    * Main program PercolationStats. Consists of a user driven menu.
    * User gives 2 inputs: 
    * 1) The size of the surface to be simulated and 
    * 2) the number of tests to be performed.
    */
   public static void main(String[] args) {
	// test client (described below)
	   int user_choice,noOfTests,sizeN;
	   PercolationStats test = null;
	   
	   Scanner input = new Scanner(System.in);
	   do{
		   System.out.println("Choose:\n1. Perform experiment\n2. Exit");
		   user_choice = input.nextInt();
		   switch(user_choice){
		   case 1:{
			   System.out.println("Enter dimension: ");
			   sizeN = input.nextInt();
			   System.out.println("Enter number of experiments: ");
			   noOfTests = input.nextInt();
			   test = new PercolationStats(sizeN,noOfTests);
			   double lo,hi;
			   lo  = test.confidenceLo();
			   hi = test.confidenceHi();
			   
			   System.out.println("mean                    = " + test.mean());
			   System.out.println("stddev                  = " + test.stddev());
			   System.out.println("95% confidence interval = " + lo + ", "+hi);
			   
		   }
		   case 2:break;
		   
		   }
	   }while(user_choice!=2);
	   input.close();
	   
   }
}