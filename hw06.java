
import java.util.*;

public class hw06 {

/*  ------------------------------------------------------------------
     Test Cases for various Graphing Methods
    ------------------------------------------------------------------  */
  public static void main (String args[]) {
    Graph testGraph = new Graph();                // Test Case Tree

    testGraph.addVertex("q");                   // Tests Node Addition
    testGraph.addVertex("r");
    testGraph.addVertex("s");
    testGraph.addVertex("t");
    testGraph.addVertex("u");
    testGraph.addVertex("v");
    testGraph.addVertex("w");
    testGraph.addVertex("x");
    testGraph.addVertex("y");
    testGraph.addVertex("z");

    testGraph.addEdge("q", "s", 5);             // Tests Edge Addition
    testGraph.addEdge("s", "v", 2);
    testGraph.addEdge("v", "w", 3);
    testGraph.addEdge("w", "s", 2);
    testGraph.addEdge("q", "w", 4);
    testGraph.addEdge("q", "t", 5);
    testGraph.addEdge("t", "x", 2);
    testGraph.addEdge("x", "z", 1);
    testGraph.addEdge("z", "x", 2);
    testGraph.addEdge("t", "y", 6);
    testGraph.addEdge("y", "q", 9);
    testGraph.addEdge("r", "u", 2);
    testGraph.addEdge("u", "y", 3);
    testGraph.addEdge("r", "y", 4);


    System.out.println(testGraph.toString());           // Tests toString (.dot)
    
    testGraph.breadthFirstSearch("r");              // Tests BFS
    for (int i = 1; i <= testGraph.vertices.size() * 2; i++) {  // Records twice the amount of nodes as steps (once for white -> gray, once for gray -> black) as .dot
      System.out.println("\nStep: " + i + "\n" + testGraph.searchSteps.remove(0));
    }

    List pathRQ = testGraph.path("r", "q");         // Tests Path for possible
    int lengthRQ = pathRQ.size();
    for (int j = 0; j < lengthRQ; j++) {
      System.out.println("\n" + pathRQ.remove(0));
    }

    List pathUR = testGraph.path("u", "r");         // Tests Path for impossible
    int lengthUR = pathUR.size();
    for (int j = 0; j < lengthUR; j++) {
      System.out.println("\n" + pathUR.remove(0));
    }

    List pathNR = testGraph.path("not", "real");        // Tests Path for invalid
    int lengthNR = pathNR.size();
    for (int k = 0; k < lengthNR; k++) {
      System.out.println("\n" + pathNR.remove(0));
    }

    System.out.println("\n" + testGraph.pathWeight("r", "q"));  // Tests PathWeght for possible
    System.out.println("\n" + testGraph.pathWeight("u", "r"));  // Tests PathWeight for impossible
    System.out.println("\n" + testGraph.pathWeight("not", "real"));// Tests PathWeight for invalid

    testGraph.depthFirstSearch();              // Tests BFS
    for (int i = 1; i <= testGraph.vertices.size() * 2; i++) {  // Records twice the amount of nodes as steps (once for white -> gray, once for gray -> black) as .dot
      System.out.println("\nStep: " + i + "\n" + testGraph.searchSteps.remove(0));
    }
    
  }

}


/*  ------------------------------------------------------------------
     Data structure class to store vertices and edges between them
    ------------------------------------------------------------------  */
class Graph {

    Map<String, Vertex> vertices;               // Map of vertices
    Map<String, ArrayList<Edge>> adjacent;          // Map of adjacent vertices
    List<String> searchSteps;                   // List of search steps
    List<String> p;                     // List of steps along path
    int time;

/*  ------------------------------------------------------------------
     Constructor method with no passed variable.
    ------------------------------------------------------------------  */
    public Graph() {
        vertices = new TreeMap<String, Vertex>();
        adjacent = new HashMap<String, ArrayList<Edge>>();
        searchSteps = new ArrayList<String>();
        p = new ArrayList<String>();
        time = 0;
    }

/*  ------------------------------------------------------------------
     Method that creates and stores a vertex in the graph with
     passed key.
    ------------------------------------------------------------------  */
    public void addVertex(String key) {
        Vertex v = new Vertex(key);             // Create node
        vertices.put(key, v);                   // Record for future reference
        adjacent.put(key, new ArrayList<Edge>());       // Record for future adjacency
    }
    
/*  ------------------------------------------------------------------
     Method that creates and stores an edge in the graph with
     passed source, target, and weight. Creates nodes if needed.
    ------------------------------------------------------------------  */
    public void addEdge(String source, String target, double weight) {
        if (!vertices.containsKey(source)) {            // If source doesn't exist, create it
            addVertex(source);
        }
        if (!vertices.containsKey(target)) {            // If target doesn't exist, create it
            addVertex(target);
        }
        ArrayList<Edge> edges = adjacent.get(source);
        Edge e = new Edge(vertices.get(source), vertices.get(target), weight);
        edges.add(e);
    }

/*  ------------------------------------------------------------------
     To string method that returns a string in form of a .dot file
     which can be run in GraphViz.
    ------------------------------------------------------------------  */
    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append("\ndigraph G {\n");
        for (String key : vertices.keySet()) {          // For each key, add a corresponding node to the graph
            s.append("\"" + key + "\"" + " [shape = ellipse, label = \"" + vertices.get(key));

            if (vertices.get(key).color == 2) {         // Modify node coloring to match vertex color
              s.append("\", style = bold, fillcolor = white, color = black];\n");
            } else if (vertices.get(key).color == 1) {
              s.append("\", style = filled, fillcolor = gray, color = black];\n");
            } else {
              s.append("\", style = dashed, fillcolor = white, color = black];\n");
            }

        }

        for (String key : vertices.keySet()) {          // For each adjacent vertex, add a corresponding edge to the graph with matching weight
            ArrayList<Edge> edges = adjacent.get(key);
            for (Edge e : edges) {
                s.append("\"" + e.source + "\"" + " -> " + "\"" + e.target + "\"" + " [label = \"" + e.weight + "\"];\n");
            }
        }

        s.append("}");
        return s.toString();                    // Return .dot code to run in GVEdit
    }

/*  ------------------------------------------------------------------
     Search method that searches across all adjacent nodes then
     repeats on each subsequent node if not already marked.
    ------------------------------------------------------------------  */
    public void breadthFirstSearch(String startVertex) {
        Vertex s = vertices.get(startVertex);
        Vertex u = null;
        Vertex v = null;
        Queue<Vertex> q = new LinkedList<Vertex>();     // Queue of vertices for searching with priority of label

        for (String key : vertices.keySet()) {          // Reset all nodes
          u = vertices.get(key);
          u.color = 0;
          u.distance = Double.POSITIVE_INFINITY;
          u.parent = null;
        }

        s.color = 1;                        // Set starting node
        s.distance = 0;
        s.parent = null;
        searchSteps.add(this.toString());           // Record the first color change

        while (true) {                      // Empty queue just in case
          try {
            q.remove();
          } catch (NoSuchElementException e) {
            break;
          }

        }

        q.add(s);                       // Enqueue the starting node

        while (q.peek() != null){
          u = q.remove();                   // Dequeue current node
          ArrayList<Edge> edges = adjacent.get(u.label);    // Identify all adjacent nodes

          for (Edge e : edges) {
            v = e.target;

            if (v.color == 0) {                 // If adjacent node is white, update, enqueue and turn gray. Record this change
              v.color = 1;
              searchSteps.add(this.toString());
              v.distance = u.distance + e.weight;
              v.parent = u;
          v.discoverStep = searchSteps.size();
          v.finishStep = searchSteps.size();
              q.add(v);
            }

          }

          u.color = 2;                      // Recolor current node as black then record change
          searchSteps.add(this.toString());
        }

    }


 public void depthFirstSearch() {
        // You implement this method
            time = 0;
            if (vertices == null)
            {
                return;
            }
            for (String key : vertices.keySet()) // for each vertex
            {
                Vertex x = vertices.get(key);
                x.color = 0;
                x.parent = null;
            }
 
            for (String key : vertices.keySet()) // for each vertex
            {
                Vertex x = vertices.get(key);
                if (x.color == 0)// if its white
                {
                    dfsvisit(x);
                }
            }
           
 
 
    }
        public void dfsvisit(Vertex x)
        {  
            time += 1;
            x.discoverStep = time;
            x.color = 1;
            searchSteps.add(this.toString());
            ArrayList<Edge> edges = adjacent.get(x.label);
                for (Edge e : edges)
                {
                   Vertex target =  e.target;
                   if (target.color == 0)
                   {
                    target.parent = x;
                    dfsvisit(target);
                   }
                }
            x.color = 2;
            time += 1;
            x.finishStep = time;
            searchSteps.add(this.toString());
 
        }

/*  ------------------------------------------------------------------
     Method that runs if a search method has already been run. 
     Returns a list of nodes along the path from the starting to the
     ending vertex. If impossible, return an error message.
    ------------------------------------------------------------------  */
    public List<String> path(String startVertex, String endVertex) {
        try {
        Vertex s = vertices.get(startVertex);
        Vertex v = vertices.get(endVertex);

        if (startVertex == endVertex) {
          p.add(s.label);
        } else if (v.parent == null) {
          p.clear();
          p.add("Null: Path doesn't Exist");
        } else {
          p.add(v.label);
          this.path(startVertex, vertices.get(endVertex).parent.label);
        }

        return p;

        } catch (NullPointerException e) {
          p.clear();
          p.add("Error, Invalid/Unfound Node Key");
          return p;
        }

    }
    
/*  ------------------------------------------------------------------
     Method that runs if a search method has already been run. 
     Checks using path if a node is along a searched path.
     If it is, return the difference in weights. If impossible,
     return an error message.
    ------------------------------------------------------------------  */
    public double pathWeight(String startVertex, String endVertex) {
      if (this.path(startVertex, endVertex).remove(0) == "Error, Invalid/Unfound Node Key" || this.path(startVertex, endVertex).remove(0) == "Null: Path doesn't Exist") {
        return Double.POSITIVE_INFINITY;
      }

      Vertex s = vertices.get(startVertex);
      Vertex v = vertices.get(endVertex);
      double x = v.distance - s.distance;

      return x;
    }
    

    public List<String> topologicalSort() {
        // You implement this method
        return null;
    }
    
}


/*  ------------------------------------------------------------------
     Class that can act as a node in a graph. Can be colored to help
     in searching.
    ------------------------------------------------------------------  */
class Vertex {
    
    public static final int WHITE = 0;
    public static final int GRAY = 1;
    public static final int BLACK = 2;

    String label;
    
    int color;
    
    double distance;
    
    Vertex parent;
    
    int discoverStep;
    
    int finishStep;

/*  ------------------------------------------------------------------
     Constructor method that takes passed value as the key.
    ------------------------------------------------------------------  */
    public Vertex(String label) {
        this.label = label;
        color = WHITE;
        distance = Double.POSITIVE_INFINITY;
        parent = null;
        discoverStep = 0;
        finishStep = 0;
    }
    
/*  ------------------------------------------------------------------
     To string method for this class. Returns the label/key.
    ------------------------------------------------------------------  */
    public String toString() {
        return label;
    }

}

/*  ------------------------------------------------------------------
     Class that acts as links between node on the graph.
    ------------------------------------------------------------------  */
class Edge {
    
    Vertex source;
    
    Vertex target;
    
    double weight;

/*  ------------------------------------------------------------------
     Constructor that takes a target, source, and weight variable.
    ------------------------------------------------------------------  */
    public Edge(Vertex source, Vertex target, double weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

/*  ------------------------------------------------------------------
     To string method for this class. Returns all attributes.
    ------------------------------------------------------------------  */
    public String toString() {
        return "(" + source + " -> " + target + " : " + weight + ")";
    }
    
}