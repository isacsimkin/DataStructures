// THIS CODE WAS WRITTEN BY ME WITHOUT CONSULTING ANY CODE MADE BY OTHER STUDENTS OR FROM OTHER SOURCES.
// ISAC SIMKIN
import java.util.*;
import java.io.*;

public class hw2
{
//  'n' is the amount of elements used in the operations regardless of how long the array is 

        static int i = 0;
        static int parent = (i-1)/2;
        static int lchild = (2*i) + 1;
        static int rchild = (2*i) + 2;

    public static void main (String [] args)
    {
        String [] x = {"a", "b", "c", "d", "e", "f", "g"};
        String [] x2 = {"g", "f", "e", "d", "c", "b", "a"};
        System.out.println(isMinHeap(x, x.length));
        System.out.println(isMaxHeap(x, x.length));
        System.out.println("buildMax");
        System.out.println("Is x a min: " + isMinHeap(x, x.length));
        System.out.println("Is x a max: " + isMaxHeap(x, x.length));
        System.out.println(toTreeString(x, x.length));
        buildMaxHeap(x, x.length-1);
        System.out.println(toTreeString(x, x.length));
        System.out.println("Is x a min: " + isMinHeap(x, x.length));
        System.out.println("Is x a max: " + isMaxHeap(x, x.length));
        System.out.println();
        System.out.println("buildMin");
        System.out.println("Is x2 a min: " + isMinHeap(x2, x.length));
        System.out.println("Is x2 a max: " + isMaxHeap(x2, x.length));
        System.out.println(toTreeString(x2, x.length));
        buildMinHeap(x2, x.length-1);
        System.out.println(toTreeString(x2, x.length));
        System.out.println("Is x2 a min: " + isMinHeap(x2, x.length));
        System.out.println("Is x2 a max: " + isMaxHeap(x2, x.length));

        

    }

    public int getParent(int index)
    {
        return (index - 1)/2;
    }

    public int getLeftChild(int index)
    {
        return 2*index+1;
    }

    public int getRighChild(int index)
    {
        return 2*index+2;
    }

    public static String toTreeString(String[]x, int n) // takes the array and evaluates its length 
    {                                                   // depending on the amount of elements, it will give out all of them or tell you if there is no element in the right child.         
        
        String str = "";
        i = 0;
        while(i < (n-1)/2)
        {
            str += "("+ x[i] + " (" + x[(2*i) + 1] + ")(" + x[(2*i) + 2] + "));\n";
            i++;    
        }
            return str;
    }



    public static boolean isMinHeap(String[] x, int n) // the parent is smaller than both of the children 
    {   
        i = 0; 

        while (i < (n-1)/2)
        {
            if (x[parent].compareTo(x[lchild]) < 0 && (x[parent].compareTo(x[rchild]) < 0))
            {
                i++;
            }
            else
            {
                return false;
            }
        }

        return true;
    }
    public static boolean isMaxHeap(String[] x, int n) // the parent is larger than both of the children 
    {       
        i = 0; 
        while (i < (n-1)/2)
        {
            if (x[parent].compareTo(x[lchild]) > 0 && (x[parent].compareTo(x[rchild]) > 0))
            {
                i++;
            }
            else
            {
                return false;
            }
        }
    
        return true;
    }
    public static void buildMinHeap(String[] x, int n)
    {
        i = (n-1)/2;

            int parent = i;
            int lchild = (2*i) + 1;
            int rchild = (2*i) + 2;
    
        if(n <= x.length) // check for amount of 'n' elements that are being asked 
        {                                       
            if (x[parent].compareTo(x[lchild]) > 0 || x[parent].compareTo(x[rchild]) > 0) //if either of the children is larger than the parent
            {
                if(x[rchild].compareTo(x[lchild]) < 0) // if rchild is larger than lchild 
                {
                    String temp; 
                            temp = x[parent];
                            x[parent] = x[rchild];
                            x[rchild] = temp;
                            
                }
                else if (x[rchild].compareTo(x[lchild]) > 0)
                {
                    String temp; 
                            temp = x[parent];
                            x[parent] = x[lchild];
                            x[lchild] = temp;   
                }
            }               
        
        }
        else
        {
            System.out.println("You're asking for more values than there are");
        }

    }
    //build a maxHeap 
    private static void buildMaxHeap(String[] x, int n) 
    {   
            i = (n-1)/2;

            int parent = i;
            int lchild = (2*i) + 1;
            int rchild = (2*i) + 2;
    
        if(n <= x.length)// check for amount of 'n' elements that are being asked 
        {                                   

            if (x[parent].compareTo(x[lchild]) < 0 || x[parent].compareTo(x[rchild])< 0) //if either of the children is larger than the parent
            {
                if(x[rchild].compareTo(x[lchild]) > 0) // if rchild is larger than lchild 
                {
                    String temp; 
                            temp = x[parent];
                            x[parent] = x[rchild];
                            x[rchild] = temp;

                }
                else if (x[rchild].compareTo(x[lchild]) < 0)
                {
                    String temp; 
                            temp = x[parent];
                            x[parent] = x[lchild];
                            x[lchild] = temp;   
                }
                
            }
        }else
        {
            System.out.println("You're asking for more values than there are");
        }

    }
    public static boolean addToHeap(String s, String[] x, int n) 
    {                                                           
        if (isMaxHeap(x, n))
        {// add a string s to x
            if(n < x.length)// addition was succesful
            {
                return true;
            }
            return false; // addition not succesful
        }   
        else if(isMinHeap(x, n))
        { // add a string s to x
            if(n < x.length) // addition was succesful
            {
                return true;
            }
            return false; // addition not succesful 
        } 
        else // if x is not a heap 
        {
            System.out.println("'x' is not a heap");
            return false;
        }                                   
    }
    public static String extractFromHeap(String[] x, int n) 
    {                                                                                                               
        String temp = "";
        return temp;
    }
}