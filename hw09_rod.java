import java.util.*;

public class hw09_rod
{

    public static void main (String args[]) {
       
        List<Double> prices = new ArrayList<Double>();
        List<Integer> resultCuts = new ArrayList<Integer>();
        prices.add(0.0);
        prices.add(1.0);
        prices.add(5.0);
        prices.add(8.0);
        prices.add(9.0);
        prices.add(10.0);
        prices.add(17.0);
        prices.add(17.0);
        prices.add(20.0);
    
       RodCutting i = new RodCutting();
       System.out.println("Rod Cut 1 w/ length 8 -> " + i.rodCut1(8, prices, resultCuts));
       System.out.println("Rod Cut 2 w/ length 8 -> " + i.rodCut2(8, prices, resultCuts));
       System.out.println("Rod Cut 3 w/ length 8 -> " + i.rodCut3(8, prices, resultCuts));
        System.out.println();
       System.out.println("Rod Cut 1 w/ length 6 -> " + i.rodCut1(6, prices, resultCuts));
       System.out.println("Rod Cut 2 w/ length 6 -> " + i.rodCut2(6, prices, resultCuts));
       System.out.println("Rod Cut 3 w/ length 6 -> " + i.rodCut3(6, prices, resultCuts));

    }
}

class RodCutting
{

    public static double rodCut1(int length, List<Double> prices, List<Integer> resultCuts)
    {// direct recursive implementation
        double q = 0;

         if (length == 0)
         {
            return 0;
         }

         q = Double.NEGATIVE_INFINITY;
        
         for (int i = 1; i <= length; i++)
         {        
            q = Math.max(q, prices.get(i) + rodCut1(length-i, prices, resultCuts)); 
         }
        return q;
    }
    public static double rodCut2(int length, List<Double> prices, List<Integer> resultCuts)
    {//top-down recursion with memoization
        double [] temp = new double[length+1];
        double maxPrice = 0;

        for (int i = 0; i <= length; i++)
        {
            temp[i] = Double.NEGATIVE_INFINITY;
        }
        
        return memoized_rodCut_Aux(prices, length, temp);
    }

    public static double memoized_rodCut_Aux(List<Double> prices, int length, double [] temp)
    { // helper method 
        double q = 0;

        /*
            base case 
        */
        if(temp[length] >= 0)
        {
            return temp[length];
        }

        if(length == 0)
        { 
            q = 0;
        }
        else
        {
            q = Double.NEGATIVE_INFINITY;
            for (int j = 1; j <= length; j++)
            {
                q = Math.max(q, prices.get(j) + memoized_rodCut_Aux(prices,length-j, temp));// finds the optimal (max) cuts 
            }
        }
        temp[length] = q;

        return q;
    }

    public static double rodCut3(int length, List<Double> prices, List<Integer> resultCuts)
    { // bottom-up iteration with dynamic programming 

        /*
            initialization of the variables and array
        */
        double [] temp = new double [length+1];
        double q = 0;
        temp[0] = 0;

        for (int j = 1; j <= length; j++)
        {
            q = Double.NEGATIVE_INFINITY;
            for (int i = 1; i <= j; i++)
            {
                q = Math.max(q, prices.get(i) + temp[j-i]); // finds the optimal (max) cuts 
            }
            temp [j] = q; // assign each found combination of prices to a space in the array 
        }
        return temp[length];
    }

// public static void printRodSolutions()
// {
//     while(length > 0)
//     {
//         System.out.println(temp[length]);
//         length = length - temp[length];
//     }
// }



}
