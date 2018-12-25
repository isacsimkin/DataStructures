/*
This code was written with consulting the code by Marcelo Silva found in the Princeton code repertoir. 
Isac Simkin
*/

public class AVLTree_1
{
        class TreeNode 
        {
                String name; // key
                String email; // value
                TreeNode left; // left child
                TreeNode right; // right child
                TreeNode parent; // parent node
                int height; // height of this node in the tree
            public String toString() 
            {
                return name + ":" + email + ":" + height;
            }

            public TreeNode (String name, String email, int height)
            {
                this.name = name;
                this.email = email;
                this.height = height;
            }
        }
        TreeNode root;
    public static void main (String [] args)
    {
        AVLTree_1 t = new AVLTree_1();

        t.add("p", "p@gmail.com");
        t.add("n", "n@gmail.com");
        t.add("b" ,"b@gmail.com");
        t.add("a", "a@gmail.com");
        t.add("h", "h@gmail.com");
        t.add("k", "k@gmail.com");
        t.add("x", "x@gmail.com");
        t.add("z", "z@gmail.com");

                System.out.println(t.search("n"));
                System.out.println(t.search("k"));
                System.out.println(t.search("a"));
                System.out.println(t.search("b"));
                System.out.println("Height: " + t.height());
            System.out.println(t.toString());


        System.out.println();

        System.out.println("Changing a few of the emails");
        t.add("b", "new-b@gmail.com");
        t.add("k", "new-k@gmail.com");

            System.out.println(t.search("n"));
            System.out.println(t.search("k"));
            System.out.println(t.search("a"));
            System.out.println(t.search("b"));
            System.out.println("Height: " + t.height());
            System.out.println(t.toString());

        System.out.println();
        System.out.println("Removing certain elements");

            t.remove("h");
        System.out.println("Removed 'h'");
        System.out.println(t.toString());

            t.remove("x");
        System.out.println("Removed 'x'");
        System.out.println(t.toString());
        System.out.println(t.search("x"));


    }

    public String toString() 
    {// makes the string representing the tree starting from the root
        String tree = subtreeToString(root);
        return tree;
    }

            private String subtreeToString(TreeNode x) 
            {// recursively print the tree including if there are null values, represented by an empty parenthesis. 
                String str = "";
                    if(x == null) 
                    {
                        return str;
                    }
                str += x.name;
                str += " (" + subtreeToString(x.left) + ") (" + subtreeToString(x.right) + ")";
                return str;
            }


    public void add(String name, String email)
    {
        root = insert(root, name, email);
    }

            TreeNode insert (TreeNode x, String name, String email)
            {
                if (x == null) 
                {// if a specific node is a leaf, create a new Node with the information and height 1.
                    return new TreeNode(name, email, 1);
                }

                if (name.compareTo(x.name) < 0)
                { // if the inserted name is smaller, then insert in the left. 
                    x.left = insert(x.left, name, email);
                }
                else if(name.compareTo(x.name) > 0)
                { // if the inserted name is larger, then insert in the right. 
                    x.right = insert(x.right, name, email);
                }
                else
                { // if its the same, just update the email
                    x.email = email;
                    return x;
                }
                x.height = 1 + Math.max(height(x.left), height(x.right));
                return balance(x);
            }

            int height(){ // serves as a getter method for the height 
                return height(root);
            }
            int height(TreeNode x) // height of a specific node 
            {
                if (x == null)
                {
                    return 0;
                }
                return x.height;
            }


            TreeNode balance (TreeNode x)
            {
                if (balanceFactor(x) < -1) 
                {// if the factor is less than -1 then analyze the right child's factor for inner rotation possibilities.
                    if (balanceFactor(x.right) > 0)
                    {
                        x.right = rotateRight(x.right);
                    }
                        x = rotateLeft(x);
                }else if (balanceFactor(x) > 1)
                {// if the factor is larger than 1 then analyze the left child's factor for inner rotation possibilities.
                    if (balanceFactor(x.left) < 0) 
                    {
                        x.left = rotateLeft(x.left);
                    }
                    x = rotateRight(x);
                }
                return x;
            }

            int balanceFactor (TreeNode x)
            { // balance calculator
                int factor;
                factor = height(x.left) - height(x.right); 
                return factor;
            }
            TreeNode rotateRight(TreeNode x) 
            {// remaps the pointers through a new Node 'y' and updates the height 
                TreeNode y = x.left;
                         x.left = y.right;
                         y.right = x;
                    x.height = 1 + Math.max(height(x.left), height(x.right));
                    y.height = 1 + Math.max(height(y.left), height(y.right));
                return y;
            }

            TreeNode rotateLeft(TreeNode x) 
            { // remaps the pointers through a new Node 'y' and updates the height 
                TreeNode y = x.right;
                         x.right = y.left;
                         y.left = x;
                    x.height = 1 + Math.max(height(x.left), height(x.right));
                    y.height = 1 + Math.max(height(y.left), height(y.right));
                return y;
            }



    public void remove(String name) 
    {
        root = delete(root, name);
    }

        TreeNode delete(TreeNode x, String name) 
        { // using binary search to find the element to be deleted.
            if (name.compareTo(x.name) < 0) 
            {
                x.left = delete(x.left, name);
            }
            else if (name.compareTo(x.name) > 0) 
            {
                x.right = delete(x.right, name);
            }
            else 
            { // cases if there is an incomplete subtree
                if (x.left == null) 
                {
                    return x.right;
                }
                else if (x.right == null) 
                {
                    return x.left;
                }
                else 
                { // if the node is a leaf 
                    TreeNode y = x;
                             x = min(y.right);
                             x.right = deleteMin(y.right);
                             x.left = y.left;
                }
            }
            // updates the height and rebalances 
            x.height = 1 + Math.max(height(x.left), height(x.right));
            return balance(x);
        }

        TreeNode min(TreeNode x) 
        {//returns the smallest of the children
            if (x.left == null)
            {
                return x;
            } 
            return min(x.left);
        }
        TreeNode deleteMin(TreeNode x) 
        {// find minimum and remap the parent to the next smallest child. 
            if (x.left == null) 
            {
                return x.right;
            }
                x.left = deleteMin(x.left);
                x.height = 1 + Math.max(height(x.left), height(x.right));
            return balance(x);
        }




    public String search(String name)
    { // try and catch NullPointerExceptions 
        String result = "";
            try
            {
                result = find(root, name);  
            }
            catch (NullPointerException e)
            { 
                System.out.println("'" + name + "'" + " does not exist");
            }
        return result;
    }
            String find (TreeNode x, String name)
            {// binary recursive traversal 
                if (name.compareTo(x.name) == 0)
                {//  if the name is equal to the one in the tree
                    return "Found -> Name: " + x.name + ", Email:  " + x.email;
                }
                else if (name.compareTo(x.name) < 0)
                {// if the name is smaller than the node's name 
                    return find(x.left, name);  
                }
                else if (name.compareTo(x.name) > 0)
                { // if the name is larger than the node's name 
                    return find(x.right, name);
                }
                    return x.name;
            }





}