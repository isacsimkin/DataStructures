import java.util.*;


public class hw10
{

   static ArrayList<Integer> coins = new ArrayList<Integer>();

    public static void main (String [] args)
    {
        coins.add(10);
        coins.add(5);
        coins.add(1);

        System.out.println(coinChange1(8, coins));
        //System.out.println("Coin change 2: " + coinChange2(14, coins));
        //System.out.println("Coin change 3: " + coinChange3(10, coins));
        
    }

    public static ArrayList<Integer> coinChange1(int money, ArrayList <Integer> coins)
    {
          ArrayList<Integer> result = new ArrayList<Integer>();
    
        if (money == 0)
        {// base case              
            return result;
        }
        
         for (int i = 0; i < coins.size(); i++)
         { // for each of the coin values 
            for (int j = 0; j < money; j++)
            {
                if (coins.get(i) <= money)
                { // while the largest value of the coin fits in money   
                    result.add(coins.get(i)); // add to list and update
                    money -= coins.get(i);
                    coinChange1(money, coins); // call with new values
                }
            }
        }
        return result;
    }

       public static ArrayList<Integer> coinChange2(int money, ArrayList <Integer> coins)
       {
        ArrayList<Integer> result = new ArrayList<Integer>();
      
        int arr[] = new int[money + 1];
        int arr_2[] = new int[money + 1];

        arr[0] = 0;

        for(int i=1; i <= money; i++)
        {// initializing the arrays
            arr[i] = Integer.MAX_VALUE-1;
            arr_2[i] = -1;
        }
        for(int j=0; j < coins.size(); j++)
        { // for every coin value
            for(int i=1; i <= money; i++)
            {// for all values of money
                if(i >= coins.get(j))
                {// if the coin is less than the "current" value of money 
                    if (arr[i - coins.get(j)] + 1 < arr[i]) 
                    {// set the value according to the value of the coin with respect to the value of money 
                        arr[i] = 1 + arr[i - coins.get(j)];
                        arr_2[i] = j;
                    }
                }
            }
        }
           /*
                 Adds the coins used to the ArrayList results
            */
        if (arr_2[arr_2.length - 1] == -1) 
        {
            System.out.print("No solution is possible");
            return result;
        }
        int start = arr_2.length - 1;
        while ( start != 0 ) 
        {
            int j = arr_2[start];
            result.add(coins.get(j));
            start = start - coins.get(j);
        }
            return result;
       }


       public static ArrayList<Integer> coinChange3(int money, ArrayList <Integer> coins)
       {
           ArrayList<Integer> result = new ArrayList<Integer>();
           int length = coins.size();

           if (money == 0)
           {   // base case            
               return result;
           }
            for (int i = 0; i < length; i++)
            {// for each of the coin values 
               while (coins.get(i) <= money)
               { // while the largest value of the coin fits in money 
                   result.add(coins.get(i));
                   money -= coins.get(i);         
                   coinChange3(money, coins);// call with new values
               }
               
           }
           return result;
       }
    }


