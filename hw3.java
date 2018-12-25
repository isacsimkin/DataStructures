/*  ------------------------------------------------------------------
       THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING    
       A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - Isac Simkin   
    ------------------------------------------------------------------  */
import java.util.*;
import java.text.DecimalFormat;

public class hw3
{

private static double timestamp;

    public static void main (String [] args)
    {
        String [] x = {"l", "a", "s", "m", "d", "q", "f"};
        String [] x2 = {"luis", "kevin", "isac"};
        String [] x3 = {"shoes", "t-shirt", "lamp", "desk", "shelf", "pan"};
        String [] x4 = {"Banana", "Orange", "Cherry", "Apple", "Pineapple", "Melon", "Plum"};
        String [] x5 = {"Books", "Pens", "Pencils", "Notebooks"};

         
        int n, n2, n3, n4, n5;
        n = x.length;
        n2 = x2.length;
        n3 = x3.length;
        n4 = x4.length;
        n5 = x5.length;

        boolean descending = true;

        System.out.println();
        System.out.println("--------------Predetermined Array Testing");
        System.out.println();
        //Heap Sort 
        System.out.println("-------------heapSort");
        supHeapSort.heapSort(x, n, descending);
        String array = Arrays.toString(x);
        System.out.println("Resulting array: " + array);

        // Merge Sort 
        System.out.println("-------------mergeSort1");
        mergeSort.mergeSort1(x2, n2, descending);
        String array2 = Arrays.toString(x2);
        System.out.println("Resulting array: " + array2); 

        System.out.println("-------------mergeSort2");
        mergeSort.mergeSort2(x3, n3, descending);
        String array3 = Arrays.toString(x3);
        System.out.println("Resulting array: " + array3);


        //Selection Sort
        System.out.println("-------------selectionSort");
        selectionSort.selectionSort(x4, n4, descending);
        String array4 = Arrays.toString(x4);
        System.out.println("Resulting array: " + array4);


        //Quick Sort
        System.out.println("-------------quickSort");       
        quickSort.quickSort(x5, n5, descending);
        String array5 = Arrays.toString(x5);
        System.out.println("Resulting array: " + array5);

        // String [] randomArr = randomArray(5, 5); // has to be deleted. 
        // int randLength = randomArr.length;
        // String array6 = Arrays.toString(randomArr);
        // System.out.println("Array to be used: " + array6);
        // System.out.println();
        
        // //Heap Sort 
        // System.out.println("-------------heapSort w/randomArray");
        // boolean descending = true;
        // supHeapSort.heapSort(randomArr , randLength, descending);
        // array = Arrays.toString(randomArr);
        // System.out.println("Resulting array: " + array);
        

        // // Merge Sort 
        // System.out.println("-------------mergeSort w/randomArray");
        // startTimer();
        // mergeSort.mergeSort1(randomArr, randLength, true);
        // System.out.println("Time: " + endTimer());
        // array2 = Arrays.toString(randomArr);
        // System.out.println("Resulting array: " + array2); 
        // //
        // startTimer();
        // mergeSort.mergeSort2(randomArr, randLength, true); 
        // System.out.println("Time: " + endTimer());
        // array3 = Arrays.toString(randomArr);
        // System.out.println("Resulting array: " + array3);

        // //Selection Sort
        // System.out.println("-------------selectionSort w/randomArray");
        // startTimer();
        // selectionSort.selectionSort(randomArr, randLength, true);
        // System.out.println("Time: " + endTimer());
        // array4 = Arrays.toString(randomArr);
        // System.out.println("Resulting array: " + array4);

        // //Quick Sort
        // System.out.println("-------------quickSort w/randomArray");  
        // startTimer();
        // quickSort.quickSort(randomArr, randLength, true);
        // System.out.println("Time: " + endTimer());
        //  array5 = Arrays.toString(randomArr);
        // System.out.println("Resulting array: " + array5);


    }   

    public static class supHeapSort
    {
        public static void heapSort(String[] x, int n, boolean descending) 
        {
        // Builds a min heap
            for (int i = n / 2 - 1; i >= 0; i--) {
                 makeItAHeap(x, n, i);
            }

            //Heap sort
            for (int i = n-1; i>=0; i--)
            {
                 String temp = x[0];
                        x[0] = x[i];
                        x[i] = temp;
         
                // makeItAHeap root element
                makeItAHeap(x, i, 0);
            }
            //descending verification


            for(int p = 0; p < x.length-1; p++)
            {
                if (x[p].compareTo(x[p+1]) > 0)
                {
                    descending = true;

                }else descending = false;
            }
            System.out.println("Is HeapSort descending: " + descending);
           
        }

        public static void makeItAHeap(String[]x , int n, int i) // turns the array into a heap
        {
            // Find largest among root, left child and right child
            int largest = i; 
            int lchild = 2*i + 1; 
            int rchild = 2*i + 2;  
     
            if (lchild < n && x[lchild].compareTo(x[largest]) > 0) // changes if left child is smaller than 'largest'
                largest = lchild;
     
            if (rchild < n && x[rchild].compareTo(x[largest]) > 0) // changes if rigth child is smaller than 'largest'
                largest = rchild;
     
            // If the value of 'largest' has changed, swap the values and make it a heap with the new array 
            if (largest != i) // being size equal to the length up to what used to be the largest. 
            {               // I.e largest-1
             String temp = x[i];
                    x[i] = x[largest];
                    x[largest] = temp;
             
              makeItAHeap(x, n, largest);
            }
        }
    }
    


    public static class mergeSort
    {
        public static void mergeSort1(String[] x, int n, boolean descending)
        { //Uses recursion
            String [] aux = new String [x.length];
            sort(x, aux, 0, x.length-1);

            for(int p = 0; p < x.length-1; p++)
            {
                if (x[p].compareTo(x[p+1]) > 0)
                {
                    descending = true;

                }else descending = false;
            }
        System.out.println("Is mergeSort Top-Down descending: " + descending);
        }
        public static void mergeSort2(String[] x, int n, boolean descending)
        {// Does not use recursion 
            int s = x.length; 
            String [] aux = new String [x.length];
            int mid; 
            int hi;

        for (int i = 1; i < s; i *= 2) { //determines the size of the sub-arrays and doubles them with each pass.
            for (int j = 0; j < s-i; j += i+i) {// runs from beginning to end of the array in increments of i.
                hi = Math.min(j+i+i-1, s-1);  //(x-i) makes sure that j starts at the beginning of the final sub-array.
                mid = j+i-1;
                merge(x, aux, j, mid, hi);
            }
        }

            for(int p = 0; p < x.length-1; p++)
            {
                if (x[p].compareTo(x[p+1]) > 0)
                {
                    descending = true;

                }else descending = false;
            }  
            System.out.println("Is mergeSort Bottom-Up descending: " + descending);      
        }

        private static void sort(String[] a, String[] aux, int lo, int hi) 
        {
            if (hi <= lo) return;
                int mid = lo + (hi - lo) / 2;
                sort(a, aux, lo, mid); // does the sorting the 1st half 
                sort(a, aux, mid + 1, hi); // does the sorting in the 2nd half 
                merge(a, aux, lo, mid, hi); // merges everything together after it has been sorted 
        }
        private static void merge(String[] x, String[] aux, int lo, int mid, int hi) 
        {
            // copy to aux[]
            for (int k = lo; k <= hi; k++) {
               aux[k] = x[k]; 
            }

                // merge back to x[]
                int i = lo, j = mid+1;
                for (int k = lo; k <= hi; k++) {
                    if (i > mid)    
                           x[k] = aux[j++];
                    else if (j > hi)            
                        x[k] = aux[i++];
                    else if (aux[j].compareTo(aux[i]) < 0)  
                        x[k] = aux[j++];
                    else                        
                        x[k] = aux[i++];
            }
        }
    }



    public static class selectionSort
    {
        public static void selectionSort(String[] x, int n, boolean descending) 
        {
            int N = x.length;
            for (int i = 0; i < N; i++) {
                int min = i;
                for (int j = i+1; j < N; j++) {
                    if (x[j].compareTo(x[min]) < 0) min = j;
                }
                exch(x, i, min);
            }

            for(int p = 0; p < x.length-1; p++)
            {
                if (x[p].compareTo(x[p+1]) > 0)
                {
                    descending = true;

                }else descending = false;
            }   
            System.out.println("Is selectionSort descending: " + descending);       
        }

            private static void exch(String [] x, int i, int j) // method to swap variables 
            {
              String swap = x[i];
                    x[i] = x[j];
                    x[j] = swap;
            }
    }

    public static class quickSort
    {
            public static void quickSort(String[] x, int n, boolean descending)
            {
                int low = 0;
                sort(x, low, n); 
                    for(int p = 0; p < x.length-1; p++)
                    {
                        if (x[p].compareTo(x[p+1]) > 0)
                        {
                            descending = true;

                        }else descending = false;
                    }
                System.out.println("Is quickSort descending: " + descending);
            }   
            public static void sort(String [] x, int low, int high) 
            { 
                if (low < high) 
                { 
                    int p = partition(x, low, high-1); 
          
                    sort(x, low, p-1); 
                    sort(x, p+1, high); 
                } 
            } 
            public static int partition(String [] x, int low, int high) 
            { 
                String pivot = x[high];  
                int i = (low-1); // index of smaller element 
                for (int j=low; j<high; j++) 
                { 
                    // If current element is smaller than or 
                    // equal to pivot 
                    if (x[j].compareTo(pivot) <= 0 ) 
                    { 
                        i++; 
          
                        // swap x[i] and x[j] 
                        String temp = x[i]; 
                                x[i] = x[j]; 
                                x[j] = temp; 
                    } 
                } 
          
                // swap x[i+1] and x[high] 
                String temp = x[i+1]; 
                        x[i+1] = x[high]; 
                        x[high] = temp; 
          
                return i+1; 
            } 

    }

    
}