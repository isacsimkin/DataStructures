/*
This code was written by myself without consulting code from others.
Isac Simkin
*/

public class RedBlackTrees
{

    // class Tree
    // {

    // }

    class Node 
    {
         Node left, right, parent;
         String key, value; 
         boolean color;

         public Node (String key, String value, boolean color)//, Node left, Node right, Node parent)
        {
            this.key = key;
            this.value = value;
            this.color = color; // red
            // this.left = left;
            // this.right = right;
            // this.parent = parent;


        }
    }
    
    private Node root;
    private boolean red = true; 
    private boolean black = false;
    
    public static void main (String [] args)
    {
        RedBlackTrees t = new RedBlackTrees();
        t.add("j", "Whats up");
         System.out.println(t.toString());
        t.add("s", "Not much");
         System.out.println(t.toString());
        t.add("a", "leftmost");
         System.out.println(t.toString());
        t.add("l", "Oh! Thats great");
        t.add("s", "yeah. I know");

        t.add("j","Not abismal");
        t.add("a", "leftmost");
        t.add("f", "leftmost");
        System.out.println(t.toString());


        // System.out.println(t.search("f"));
        // System.out.println(t.search("a"));
        // System.out.println(t.search("i"));
        // System.out.println(t.search("j"));
        // System.out.println(t.search("k"));
        // System.out.println(t.search("s"));
        // System.out.println(t.search("l"));
    }

    public String toString() 
    {// makes the string representing the tree starting from the root
        String tree = subtreeToString(root);
        return tree;
    }

            private String subtreeToString(Node x) 
            {// recursively print the tree including if there are null values, represented by an empty parenthesis. 
                String str = "";
                    if(x == null) 
                    {
                        return str;
                    }

                str += x.key + " " + x.color;
                str += " (" + subtreeToString(x.left)+") (" + subtreeToString(x.right)+ ")";

                return str;
            }

    public void add (String key, String value)
    {
         root = insert (root, key, value);
         root.color = black;

    }

        Node insert (Node x, String key, String value)
        {
            if (x == null)
            {   
                //System.out.println("Created node: " + key);
                return new Node(key, value, red);
            }
            if (key.compareTo(x.key) < 0)
            { // if the inserted value is smaller, then insert in the left. 
                x.left = insert(x.left, key, value);
                x.left.parent = x;
                //System.out.println("Inserting in: " +x.key + " left child: " + x.left.key + " parent: " + x.left.parent.key);
            }
            else if(key.compareTo(x.key) > 0)
            { // if the inserted value is larger, then insert in the right. 
                x.right = insert(x.right, key, value);
                x.right.parent = x;
                //System.out.println("Inserting in: " +x.key + " right child: " + x.right.key + " parent: " + x.right.parent.key);
            }
            else
            {
                x.value = value;
            }
            //---------------------------------------------------------------
        return balance(x);
            
        }

        boolean isRed(Node x)
        {
            if(x == null)
            {
                return false;
            }
            if (x.color = red)
            {
                return true;
            }else
                return false;
        }
        void switchColor(Node x)
        {
            x.color = !x.color;
            x.left.color = !x.left.color;
            x.right.color = !x.right.color;
        }
            Node rotateRight(Node x) 
            {// remaps the pointers through a new Node 'y' and updates the height 
                Node y = x.left;
                         x.left = y.right;
                         y.right = x;

                         y.color = y.right.color;
                         y.right.color = red;

                return y;
            }

            Node rotateLeft(Node x) 
            { // remaps the pointers through a new Node 'y' and updates the height 
                Node y = x.right;
                         x.right = y.left;
                         y.left = x;
                        
                         y.color = y.left.color;
                         y.left.color = red;
                
                return y;
            }



     public void remove(String key)
     {  
        if(key == null)
        {
            throw new IllegalArgumentException("argument is null");
        }
        if(!isRed(root.left) && !isRed(root.right))
        {
            root.color = true;
        }
        root = delete(root, key);
        // if(!isEmpty())
        // {
        //  root.color = false;
        // }
    }
        Node delete(Node x, String key)
        {
            if (key.compareTo(x.key) < 0)
            {
                if(!isRed(x.left) && !isRed(x.left.left))
                {
                    x = moveRedLeft(x);
                    x.left = delete(x.left,key);
                }
                else
                {
                    if (isRed(x.left))
                    {
                        x = rotateRight(x);
                    }
                    if(key.compareTo(x.key) == 0 && (x.right == null))
                    {
                        return null;
                    }
                    if(!isRed(x.right) && !isRed(x.right.left))
                    {
                        x = moveRedRight(x);
                    }
                    if(key.compareTo(x.key) == 0)
                    {
                        Node y = min(x.right);
                            x.key = y.key;
                            x.value = y.value;

                            x.right = deleteMin(x.right);
                    }else
                    {
                        x.right = delete(x.right, key);
                    }
                }
            }
            return balance(x);
      }
        

         Node min(Node x) 
         { 
            if (x.left == null)
            { 
                return x; 
            }
            else
            {                
                return min(x.left); 
            }
         } 
        Node deleteMin(Node x) 
        { 
            if (x.left == null)
            {
                return null;
            }

            if (!isRed(x.left) && !isRed(x.left.left))
            {
                x = moveRedLeft(x);
            }

                x.left = deleteMin(x.left);
            return balance(x);
        }
          private Node balance(Node x) 
          {
            System.out.println(x.parent);
            //recoloring procedure 
            try
            {
            while(x.parent.color == red)
            {
                if(x.parent == x.parent.parent.left)
                {
                    Node y = x.parent.parent.right;
                    if (y.color == red)
                    {
                        x.parent.color = black;
                        y.color = black;
                        x.parent.parent.color = black;
                        x = x.parent.parent;
                    }else if (x == x.parent.right)
                    {
                        x = x.parent;
                        rotateLeft(x);
                    }else
                    {
                        x.parent.color = black;
                        x.parent.parent.color = red;
                        rotateRight(x.parent.parent);
                    }
                }else
                {

                }
            root.color = black; 
            }

            }
             catch (NullPointerException e)
            {
                System.out.println(x.key + "'s parent is null");
            }

            try
            {
                System.out.println("Parent: " + x.key + " Right Child: " + x.right.key + " Left child: " + x.left.key);
            }
            catch (NullPointerException e)
            {
                if (x.left == null){
                    System.out.println("left child is null, but right is: " + x.right.key);
                }else if (x.right == null){
                    System.out.println("right child is null, but left is: " + x.left.key);
                }else if (x.left == null && x.right == null) {
                    System.out.println("both of the children is null");
            }
          }
            // length of black nodes fixing procedure 


       //      if (isRed(x.right))
       //      {
       //        x = rotateLeft(x);
       //      }                  
               
       //      if (isRed(x.left) && isRed(x.left.left)) 
       //      {
       //       x = rotateRight(x);
       //      }
       //      if (isRed(x.left) && isRed(x.right)) 
       //      {
                // switchColor(x);
       //      }    
             return x;
          }

             Node moveRedLeft(Node x) 
             {
                    switchColor(x);
                if (isRed(x.right.left)) 
                { 
                    x.right = rotateRight(x.right);
                    x = rotateLeft(x);
                    switchColor(x);
                }
                    return x;
            }
             Node moveRedRight(Node x)
             {
                    switchColor(x);
                if (isRed(x.left.left)) 
                { 
                    x = rotateRight(x);
                    switchColor(x);
                }
                return x;
            }


    public String search (String key)
    {
        String result = "";
        try 
        {
            result = find (root, key);
        }
        catch (NullPointerException e)
        {
            System.out.println("'" + key + "'" + " does not exist");
        }

        return result;
    }
    String find (Node x, String key)
    {// binary recursive traversal 
        while(x != null){
        if (key.compareTo(x.key) == 0)
        {//  if the key is equal to the one in the tree
            return "Found -> key: " + x.key + ", Value:  " + x.value + "\nRed? " + x.color + "\n";
        }
        else if (key.compareTo(x.key) < 0)
        {// if the key is smaller than the node's key 
            return find(x.left, key);   
        }
        else if (key.compareTo(x.key) > 0)
        { // if the key is larger than the node's key 
            return find(x.right, key);
        }
    }
            return x.key;

    }
}