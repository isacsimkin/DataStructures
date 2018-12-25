import java.util.*;

public class hw09_lcs
{

    public static void main (String args[]) {

       LongestCommonSubsequence t = new LongestCommonSubsequence();
       List<String> operations = new ArrayList<String>();

       System.out.println(t.longestCommonSubsequence("algorithm", "altruistic")); 
        
    }
}

class LongestCommonSubsequence
{

   public static String longestCommonSubsequence(String x, String y)
   {
        int len_x = x.length();
        int len_y = y.length();

        int [][] table = new int [len_x+1][len_y+1];

        for(int i = 0; i <= len_x; i++)
        {
            for(int j = 0; j <= len_y; j++)
            {
                if (i == 0 || j ==0)
                {
                    table[i][j] = 0;

                }else if (x.charAt(i-1) == y.charAt(j-1))
                {
                    table[i][j] = table [i-1][j-1] + 1;
                }else
                {
                    table[i][j] = Math.max(table[i-1][j], table[i][j-1]);
                }
            }
            
        }

        int index = table[len_x][len_y];
        int temp = index;

        char [] lcs = new char[index+1];

        int i = len_x;
        int j = len_y;

        while(i > 0 && j > 0)
        {
            if(x.charAt(i-1) == y.charAt(j-1))
            {
                lcs[index -1] = x.charAt(i-1);

                i--;
                j--;
                index--;
            }
            else if(table[i-1][j] > table [i][j-1])
            {
                i--;
            }else
            {
                j--;
            }
        }

        String string = new String (lcs);
        return string;
    
   }
}
