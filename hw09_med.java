import java.util.*;

public class hw09_med
{

    public static void main (String args[]) {
       MinimumEditDistance t = new MinimumEditDistance();

       List<String> operations = new ArrayList<String>();
       System.out.println(t.editDistance("sunday", "saturday", 3, 2, 5, operations)); 
        
    }
}

class MinimumEditDistance
{

    public static int editDistance(String x, String y, int insertCost, int deleteCost, int replaceCost, List<String> resultOperations)
    {
        int cost = 0;
        int len_x = x.length();
        int len_y = y.length();
        int min = 0;
    
        int insCost = insertCost;
        int repCost = replaceCost;
        int delCost = deleteCost;
        /*

        initialize the table

        */

        int [][] table = new int [len_x+1][len_y+1]; 

        for (int i = 1; i <= len_x; i++)
        {
            table[i][0] = insertCost;
            insertCost += insCost;
        }

        for (int j = 1; j<= len_y; j++)
        {
            table[0][j] = deleteCost;
            deleteCost += delCost;
        }


//        print2D(table);
       /*
        ______________________________________________
       */     
        /*
            for each character in the table, compare to check if chars are equal or not 
        */
        for (int i = 0; i < len_x; i++)
        {
            char c1 = x.charAt(i);

            for (int j = 0; j < len_y; j++)
            {
                char c2 = y.charAt(j);

                if(c1 == c2)
                {//diagonal increase due to same character
                    table[i+1][j+1] = table[i][j];
                }
                else
                {// costs of each action represented 
                    replaceCost = table[i][j] + repCost;
                    insertCost = table[i][j+1] + insCost;
                    deleteCost = table [i+1][j] + delCost;

                    if (replaceCost > insertCost)
                    {
                        min = insertCost;
                    }
                    else
                    {
                        min = replaceCost;
                    }
                    if (deleteCost > min)
                    {
                        min = min;
                    }else
                    {
                        min = deleteCost;
                       
                    }
                    
                    table [i+1][j+1] = min;

                    
                }
            }
            /*
                add the operations to the list 
            */
                    if (min == insertCost)
                    {
                        resultOperations.add("Inserted ");
                    }else if (min == replaceCost)
                    {
                        resultOperations.add("Replaced with ");
                    }else
                    {
                        resultOperations.add("Deleted");
                    }
        }


        System.out.println();
        print2D(table);
        //System.out.println(resultOperations);
         return table[len_x][len_y];
    }

   public static void print2D(int mat[][]) 
    { 
        // Loop through all rows 
        for (int[] row : mat) 
  
            // converting each row as string 
            // and then printing in a separate line 
            System.out.println(Arrays.toString(row)); 
    } 
  

}
