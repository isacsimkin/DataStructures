import java.util.*;

public class hw08
{

    public static void main (String args[]) {
     Graph t = new Graph();             // Graph for other tests
     Graph t2 = new Graph();


        t.addVertex("S");
        t.addVertex("A");
        t.addVertex("B");
        t.addVertex("C");
        t.addVertex("D");
        t.addVertex("T");


        t.addEdge("S", "A", 10, 0);
        t.addEdge("S", "C", 10, 0);
        t.addEdge("A", "B", 4, 0);
        t.addEdge("A", "C", 2, 0);
        t.addEdge("A", "D", 8, 0);
        t.addEdge("C", "D", 9, 0);
        t.addEdge("D", "B", 6, 0);
        t.addEdge("B", "T", 10, 0);
        t.addEdge("D", "T", 10, 0);



        t2.addVertex("S");
        t2.addVertex("A");
        t2.addVertex("B");
        t2.addVertex("C");
        t2.addVertex("D");
        t2.addVertex("T");

        t2.addEdge("S", "A", 1, 0);
        t2.addEdge("S", "C", 1, 0);
        t2.addEdge("A", "B", 1, 0);
        t2.addEdge("A", "D", 1, 0);
        t2.addEdge("C", "D", 1, 0);    
        t2.addEdge("B", "T", 1, 0);
        t2.addEdge("D", "T", 1, 0);

        



         System.out.println("MaxFlow: " + t.maxFlow("S", "T"));
         System.out.println(t.toString(true));

         System.out.println("MaxFlow: " + t2.maxFlow("S", "T"));
         System.out.println(t.toString(true));
        //  System.out.println();
        // Graph res = t.makeResidual(); 
        // System.out.println(res.toString(true)); 

    }
}


    class Graph 
    {

              Map<String, Vertex> vertices;             // Map of vertices
              Map<String, ArrayList<Edge>> adjacent;        // Map of adjacent vertices
              List<String> searchSteps;             // List of search steps
              List<Vertex> path;                    // List of steps along path
              List<String> foundPath;               // List of found path steps
              List<String> foundPathWeight;             // List of found path weight        
              List<Vertex> dijVertices;             // List for Dijkstra


            /*  ------------------------------------------------------------------
                 Constructor method with no passed variable.
                ------------------------------------------------------------------  */
              public Graph() {
                vertices = new TreeMap<String, Vertex>();
                adjacent = new HashMap<String, ArrayList<Edge>>();
                searchSteps = new ArrayList<String>();
                path = new ArrayList<Vertex>();
                foundPath = new ArrayList<String>();
                foundPathWeight = new ArrayList<String>();       
                dijVertices = new ArrayList<Vertex>();

              }



            /*  ------------------------------------------------------------------
                 Method that creates and stores a vertex in the graph with
                 passed key.
                ------------------------------------------------------------------  */
              public void addVertex(String key) {
                Vertex v = new Vertex(key);             // Create node

                if (vertices.get(key) != null) {
                  vertices.replace(key, v);
                } else {
                  vertices.put(key, v);             // Record for future reference
                  adjacent.put(key, new ArrayList<Edge>());     // Record for future adjacency
                }

              }
                
            /*  ------------------------------------------------------------------
                 Method that creates and stores an edge in the graph with
                 passed source, target, and weight. Creates nodes if needed.
                ------------------------------------------------------------------  */

               public void addEdge(String source, String target, double capacity, double flow) {
                if (!vertices.containsKey(source)) {    // If source doesn't exist, create it
                  addVertex(source);
                }

                if (!vertices.containsKey(target)) {    // If target doesn't exist, create it
                  addVertex(target);
                }

                ArrayList<Edge> edges = adjacent.get(source);
                Edge e = new Edge(vertices.get(source), vertices.get(target), capacity, flow);
                edges.add(e);
              }


            /*  ------------------------------------------------------------------
                 Method that removes all edges connected to a target node.
                ------------------------------------------------------------------  */
              public void removeEdges(String target) {
                ArrayList<Edge> edges = adjacent.get(target);
                Vertex f1 = null;
                Edge f2 = null;
                  
                if (edges != null) {            // If there are any outbound edges, remove them
                  edges.clear();
                }

                for (String key : vertices.keySet()) {  // If there is an inbound edge, record it
                  ArrayList<Edge> edges2 = adjacent.get(key);

                  for (Edge e : edges2) {

                    if (e.target.label == target) {
                      f1 = vertices.get(key);
                      f2 = e;
                    }

                  }

                }

                if (f1 != null) {               // If there is a recorded edge, remove it
                  ArrayList<Edge> edges3 = adjacent.get(f1.label);
                  edges3.remove(f2);
                }

              }

            /*  ------------------------------------------------------------------
                 To string method that returns a string in form of a .dot file
                 which can be run in GraphViz. Directed is true if graph
                 is directed, false if undirected.
                ------------------------------------------------------------------  */
              public String toString(boolean directed) {
                if (directed) {             // Directed Graph
                  StringBuilder s = new StringBuilder();
                  s.append("\ndigraph G {\n");

                  for (String key : vertices.keySet()) {    // Record each vertex
                    s.append("\"" + key + "\"" + " [shape = ellipse, label = \"" + vertices.get(key));

                    if (vertices.get(key).color == 2) { // Modify node coloring
                      s.append("\", style = bold, fillcolor = white, color = black];\n");

                    } else if (vertices.get(key).color == 1) {
                      s.append("\", style = filled, fillcolor = gray, color = black];\n");

                    } else {
                      s.append("\", style = dashed, fillcolor = white, color = black];\n");
                    }

                  }

                  for (String key : vertices.keySet()) {    // Record each edge by adjacency
                    ArrayList<Edge> edges = adjacent.get(key);

                    for (Edge e : edges) {
                      s.append("\"" + e.source + "\"" + " -> " + "\"" + e.target + "\"" + " [label = \"" + e.flow + "/" + e.capacity + "\"];\n");
                    }

                  }

                  s.append("}");
                  return s.toString();          // Return .dot code to run in GVEdit

                } else {                    // Undirected Graph
                  StringBuilder s = new StringBuilder();
                  s.append("\ngraph G {\n");

                  for (String key : vertices.keySet()) {    // Record each vertex
                    s.append("\"" + key + "\"" + " [shape = ellipse, label = \"" + vertices.get(key));

                    if (vertices.get(key).color == 2) { // Modify node coloring
                      s.append("\", style = bold, fillcolor = white, color = black];\n");

                    } else if (vertices.get(key).color == 1) {
                      s.append("\", style = filled, fillcolor = gray, color = black];\n");

                    } else {
                      s.append("\", style = dashed, fillcolor = white, color = black];\n");
                    }

                  }

                  for (String key : vertices.keySet()) {    // Record each edge by adjacency
                    ArrayList<Edge> edges = adjacent.get(key);

                    for (Edge e : edges) {
                      s.append("\"" + e.source + "\"" + " -- " + "\"" + e.target + "\"" + " [label = \"" + e.weight + "\"];\n");
                    }

                  }

                  s.append("}");
                  return s.toString();          // Return .dot code to run in GVEdit

                }

              }

          public double maxFlow(String startVertex, String endVertex)
          { 

            // if the edge is 0 then is blocked 

                 Graph temp = makeResidual(); //makes the residual network

                 Vertex s = vertices.get(startVertex);
                 Vertex v = vertices.get(endVertex);

                double maxFlow = 0;
                double maxCap;
                double path_flow = Double.MAX_VALUE;

                List<Vertex> pathway = new ArrayList<Vertex>();
                List<Vertex> anotherOption = new ArrayList<Vertex>();
                ArrayList<Edge> pathEdges = new ArrayList<Edge>();

                for (String key : vertices.keySet()) 
                {// Finds each key      
                      ArrayList<Edge> edges = adjacent.get(key);
                      for (Edge e : edges) 
                      { // Finds each edge per key and sets the flow to 0
                        e.flow = 0;  
                      }
                }
                

                if(breadthFirstSearch(startVertex, endVertex))
                {
                     pathway = temp.path(startVertex, endVertex); // finds the pathway from the starting node to the end node in the residual network 
                     Collections.reverse(pathway);
                     //System.out.println(pathway);
        
                    for (int i =0; i < pathway.size()-1; i++) 
                    { //for every item in the list
                        s = pathway.get(i);
                        v = pathway.get(i+1);

                        //System.out.println("Maxflow "+ i + " - " + maxFlow);
                        pathEdges = adjacent.get(s.label);

                        for (Edge e : pathEdges) 
                        {   // Finds each edge per label
                        
                            if (s.label == e.source.label && v.label == e.target.label)
                            { // comparison between the vertices in path way and the vertices in the Edge list
                                //to check for them being in the path
                                path_flow = Math.min(path_flow, e.capacity);
                            }
                        }
                    }
                    

                    for (int i =0; i < pathway.size()-1; i++) 
                    {
                        s = pathway.get(i);
                        v = pathway.get(i+1);
                        pathEdges = adjacent.get(s.label);

                        for (Edge e : pathEdges) 
                        {   // Finds each edge per label
                            if (s.label == e.source.label && v.label == e.target.label)
                            {   
                                e.flow += path_flow;
                            }else
                            {
                                // e.flow -= path_flow;
                            }
                        }
                    }

                    maxFlow += path_flow; 
                }

                return maxFlow;
            }

            /*  ------------------------------------------------------------------
                 Method that runs if a search method has already been run. 
                 Returns a list of nodes along the path from the starting to the
                 ending vertex. If impossible, return an error message.
                ------------------------------------------------------------------  */
              public List<Vertex> path(String startVertex, String endVertex) {
                try {

                  Vertex s = vertices.get(startVertex);
                  Vertex v = vertices.get(endVertex);
                  
                  if (breadthFirstSearch(startVertex,endVertex))
                  { // if there is a path
                      if (startVertex == endVertex) {
                        path.add(s);
                      } else if (v.parent == null) {
                        path.clear();
                        path.add(null);
                      } else {
                        path.add(v);
                        this.path(startVertex, vertices.get(endVertex).parent.label);
                      }
                  }
                  return path;
              
                } catch (NullPointerException e) {
                  path.clear();
                  path.add(null);
                  return path;
                }

              }

            public Graph makeResidual()
            {
                Graph residual = new Graph();
                for (String key : vertices.keySet()) 
                {// Finds each key      
                      ArrayList<Edge> edges = adjacent.get(key);
                      for (Edge e : edges) 
                      { // Finds each edge per key and creates and edge and an antiparallel
                         residual.addEdge(e.source.label, e.target.label , (e.capacity - e.flow), 0); // forward pointer 
                         residual.addEdge(e.target.label, e.source.label,  e.flow, 0); // backwards pointer 
                      }
                }

                return residual;
            }

            /*  ------------------------------------------------------------------
                 Search method that searches across all adjacent nodes then
                 repeats on each subsequent node if not already marked.
                ------------------------------------------------------------------  */
              public boolean breadthFirstSearch(String startVertex, String endVertex) {
                Vertex s = vertices.get(startVertex);
                Vertex t = vertices.get(endVertex);
                Vertex u = null;
                Vertex v = null;

                for (String key : vertices.keySet()) 
                {       // Reset all nodes
                      u = vertices.get(key);
                      u.color = 0;
                      u.visited = false;
                      u.distance = Double.POSITIVE_INFINITY;
                      u.parent = null;
                }

                Queue<Vertex> q = new LinkedList<Vertex>(); // Queue of vertices
                s.color = 1;                    // Set starting node
                s.distance = 0;
                s.parent = null;
                searchSteps.add(this.toString());   // Record the first color change
                s.visited = true;
                q.add(s);                       // Enqueue the starting node
                
                while (q.peek() != null)
                {
                      u = q.remove();// Dequeue current node
                      ArrayList<Edge> edges = adjacent.get(u.label);// Identify all adjacent nodes

                    for (Edge e : edges) 
                    {
                        v = e.target;

                        if (v.color == 0) 
                        {// If adjacent node is white, update, enqueue and turn gray. Record this change
                             v.color = 1;
                             searchSteps.add(this.toString());
                             v.distance = u.distance + e.weight;
                             v.parent = u;
                             v.discoverStep = searchSteps.size();
                             v.finishStep = searchSteps.size();
                             v.visited = true;
                             q.add(v);
                             //visited [j] = true;
                        }

                    }

                      u.color = 2;                  // Recolor current node as black then record change
                      searchSteps.add(this.toString());
                }

                return (t.visited == true);

              }
             

    
        }

            /*  ------------------------------------------------------------------
                 Class that allows vertices to be sorted based on their distance.
                ------------------------------------------------------------------  */
            class VertexComparator implements Comparator<Vertex> {

              public int compare(Vertex v1, Vertex v2) {
                return Double.compare(v1.distance, v2.distance);
              }

            }

            /*  ------------------------------------------------------------------
                 Class that allows edges to be sorted based on their weights.
                ------------------------------------------------------------------  */
            class EdgeComparator implements Comparator<Edge> {

              public int compare(Edge e1, Edge e2) {
                return Double.compare(e1.weight, e2.weight);
              }

            }




        /*  ------------------------------------------------------------------
             Class that can act as a node in a graph. Can be colored to help
             in searching.
            ------------------------------------------------------------------  */
        class Vertex 
        {
            
              public static final int WHITE = 0;
              public static final int GRAY = 1;
              public static final int BLACK = 2;

              String label;
              int color;   
              double distance;
              Vertex parent;

              boolean visited;

              int discoverStep;
              int finishStep;
              int rank;

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
        class Edge 
        {
            
              Vertex source;
              Vertex target;
              double weight;

              double capacity;
              double flow;
              double residue;

            /*  ------------------------------------------------------------------
                 Constructor that takes a target, source, and weight variable.
                ------------------------------------------------------------------  */
              public Edge(Vertex source, Vertex target, double weight) {
                this.source = source;
                this.target = target;
                this.weight = weight;
              }

              public Edge(Vertex source, Vertex target, double capacity, double flow) {
                this.source = source;
                this.target = target;
                this.capacity = capacity;
                this.flow = flow;
                
              }

            /*  ------------------------------------------------------------------
                 To string method for this class. Returns all attributes.
                ------------------------------------------------------------------  */
              public String toString() {
                return "(" + source + " -> " + target + " : " + flow + ")";
              }
            
        }