/*  ------------------------------------------------------------------
       THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING  
        CODE - except previously written code in previous assignments -
        WRITTEN BY OTHER STUDENTS. Isac Simkin
    ------------------------------------------------------------------  */


import java.util.*;

public class AirportGraph {
    int time = 0;

    Map<String, Vertex> vertices;
    
    Map<String, ArrayList<Edge>> adjacent;
    
    List<String> path;

    List<Vertex> dijkstraVertices;

   
    public static void main (String [] args)
    {
        

        Graph2 airportGraph = new Graph2();             // Graph for other tests

            airportGraph.addVertex("ATL");
            airportGraph.addVertex("JFK");
            airportGraph.addVertex("LAX");
            airportGraph.addVertex("DCA");
            airportGraph.addVertex("MIA");
            
            airportGraph.addEdge("ATL", "JFK", 880);          // Directed Edges
            airportGraph.addEdge("ATL", "LAX", 2199.5);
            airportGraph.addEdge("ATL", "MIA", 664);
            airportGraph.addEdge("ATL", "DCA", 636);

            airportGraph.addEdge("JFK", "LAX", 2475);
            airportGraph.addEdge("JFK", "DCA", 213);
             airportGraph.addEdge("JFK", "MIA", 1090);



            airportGraph.addEdge("LAX", "DCA", 2311);



            System.out.println(airportGraph.toString(true));

            airportGraph.bellmanFord("ATL");              // Bellman-Ford Test Cases
            System.out.println("\nFrom ATL to JFK " + airportGraph.pathWeight("ATL", "JFK"));
            System.out.println("From ATL to LAX " + airportGraph.pathWeight("ATL", "LAX"));
            System.out.println("From ATL to DCA " + airportGraph.pathWeight("ATL", "DCA"));
            airportGraph.dijkstra("ATL");                 // Dijkstra Test Cases
            System.out.println("\nFrom ATL to JFK " + airportGraph.pathWeight("ATL", "JFK"));
            System.out.println("From ATL to LAX " + airportGraph.pathWeight("ATL", "LAX"));

            System.out.println("From ATL to DCA " + airportGraph.pathWeight("ATL", "DCA"));

    }


    public AirportGraph() {
        vertices = new TreeMap<String, Vertex>();
        adjacent = new HashMap<String, ArrayList<Edge>>();
        path = new ArrayList<String>();
    }

    public void addVertex(String key) {
        Vertex v = new Vertex(key);
        vertices.put(key, v); // creates the vertex in the map
        adjacent.put(key, new ArrayList<Edge>()); // creates an empty list intended for the edges of the newly created node 
    }

    public void addEdge(String source, String target, double weight) {
        if (!vertices.containsKey(source)) 
        { // if the map does not contain 'source' then add it 
            addVertex(source);
        }
        if (!vertices.containsKey(target)) 
        { // if the map does not contain 'target' then add it 
            addVertex(target);
        }
        ArrayList<Edge> edges = adjacent.get(source); // adds the source to the list 'edges'
        Edge e = new Edge(vertices.get(source), vertices.get(target), weight);// created the connection between the two vertices
        edges.add(e); // add the connection to the list 
    }

    public String toString(boolean isDirected) {
        if (isDirected) 
        {             // Directed Graph
              StringBuilder s = new StringBuilder();
              s.append("\ndigraph G {\n");

              for (String key : vertices.keySet()) 
              {    // Record each vertex
                    s.append("\"" + key + "\"" + " [shape = ellipse, label = \"" + vertices.get(key));

                    if (vertices.get(key).color == 2) 
                    { // Modify node coloring
                      s.append("\", style = bold, fillcolor = white, color = black];\n");

                    } 
                    else if (vertices.get(key).color == 1) 
                    {
                      s.append("\", style = filled, fillcolor = gray, color = black];\n");

                    } 
                    else 
                    {
                      s.append("\", style = dashed, fillcolor = white, color = black];\n");
                    }

              }

              for (String key : vertices.keySet()) 
              {    // Record each edge by adjacency
                    ArrayList<Edge> edges = adjacent.get(key);

                    for (Edge e : edges) 
                    {
                      s.append("\"" + e.source + "\"" + " -> " + "\"" + e.target + "\"" + " [label = \"" + e.weight + "\"];\n");
                    }

              }

              s.append("}");
              return s.toString();          // Return .dot code to run in GVEdit

        } 
        else 
        {                    // Undirected Graph
            StringBuilder s = new StringBuilder();
            s.append("\ngraph G {\n");

              for (String key : vertices.keySet()) 
              {    // Record each vertex
                s.append("\"" + key + "\"" + " [shape = ellipse, label = \"" + vertices.get(key));

                if (vertices.get(key).color == 2) 
                { // Modify node coloring
                  s.append("\", style = bold, fillcolor = white, color = black];\n");

                } else if (vertices.get(key).color == 1) 
                {
                  s.append("\", style = filled, fillcolor = gray, color = black];\n");

                } else 
                {
                  s.append("\", style = dashed, fillcolor = white, color = black];\n");
                }

              }

              for (String key : vertices.keySet()) {    // Record each edge by adjacency
                ArrayList<Edge> edges = adjacent.get(key);

                for (Edge e : edges) 
                {
                  s.append("\"" + e.source + "\"" + " -- " + "\"" + e.target + "\"" + " [label = \"" + e.weight + "\"];\n");
                }

              }

              s.append("}");
              return s.toString();          // Return .dot code to run in GVEdit

        }

    }
    public double pathWeight(String startVertex, String endVertex) 
    {
        if (this.path(startVertex, endVertex).remove(0) == "Error, Invalid/Unfound Node Key" || this.path(startVertex, endVertex).remove(0) == "Null: Path doesn't Exist") {
          return Double.POSITIVE_INFINITY;
        }

        Vertex s = vertices.get(startVertex);
        Vertex v = vertices.get(endVertex);
        double x = v.distance - s.distance;

        return x;
     }
    public List<String> path(String startVertex, String endVertex) 
    {
        try 
        {
          Vertex s = vertices.get(startVertex);
          Vertex v = vertices.get(endVertex);

          if (startVertex == endVertex) 
          {
            path.add(s.label);
          } else if (v.parent == null) 
          {
            path.clear();
            path.add("Null: Path doesn't Exist");
          } else 
          {
            path.add(v.label);
            this.path(startVertex, vertices.get(endVertex).parent.label);
          }

          return path;

        } catch (NullPointerException e) 
        {
          path.clear();
          path.add("Error, Invalid/Unfound Node Key");
          return path;
        }

    }
    
    public double totalWeight()
    {
        double sum = 0;
         for (String key : vertices.keySet()) // for each vertex
            {
                Vertex x = vertices.get(key);
                if (x != null)// if its not null
                {
                    ArrayList<Edge> edges = adjacent.get(x.label);
                    for (Edge e : edges) 
                    {   
                        sum += e.weight;
                    }

                }
            }
        return sum;
    }
     public double totalWeight(boolean isDirected) 
     {
        double totalWeight = 0;

        for (String key : vertices.keySet()) {      // Finds each key       
          ArrayList<Edge> edges = adjacent.get(key);

          for (Edge e : edges) {                // Finds each edge per key
            totalWeight += e.weight;
          }

        }
            
        if (!isDirected) {
          totalWeight /= 2;
        }

        return totalWeight;                 // Return
  }
    public Graph2 prim(String root)
    {
            Vertex r = vertices.get(root);
            Vertex u = null;
            Vertex v = null;
            PriorityQueue<Vertex> primpq = new PriorityQueue<Vertex>(new VertexComparator());
            Graph2 primGraph = new Graph2();

        for (String key : vertices.keySet()) 
        {  // Starts each vertex as white, infinite distance, and with no parent
              u = vertices.get(key);
              u.color = 0;
              u.distance = Double.POSITIVE_INFINITY;
              u.parent = null;

              if (u.label == r.label) {         // Starts root vertex with 0 distance
                u.distance = 0;
              }

              primpq.add(u);                 // Adds each vertex to a priority queue

        }

            while (primpq.size() != 0) 
            {// While queue isn't empty
              u = primpq.poll();
              ArrayList<Edge> edges = adjacent.get(u.label);    // Check all potential edges

              for (Edge e : edges) {
                v = e.target;
                if (v.color != 2 && e.weight < v.distance) 
                {    // If not black and has a shorter/lighter edge, connect
                  primpq.remove(v);
                  v.parent = u;
                  v.distance = e.weight;
                  v.color = 1;
                  primpq.add(v);
                  primGraph.removeEdges(v.label); // Remove previously attached edges
                  primGraph.addEdge(u.label, v.label, v.distance);// Adds edge
                }

              }

              u.color = 2; //Once explored, make black
              primGraph.addVertex(u.label);
            }


            return primGraph;                 
    }

      public void removeEdges(String target) 
      {
            ArrayList<Edge> edges = adjacent.get(target);
            Vertex v = null;
            Edge e2 = null;
              
            if (edges != null) 
            {  // If there are any outbound edges, remove them
              edges.clear();
            }

            for (String key : vertices.keySet()) 
            {  // If there is an inbound edge, record it
              ArrayList<Edge> edges2 = adjacent.get(key);

              for (Edge e : edges2) 
              {
                if (e.target.label == target) 
                {
                  v = vertices.get(key);
                  e2 = e;
                }

              }

            }

            if (v != null) 
            {//Remove it if its a recorded edge
              ArrayList<Edge> e3 = adjacent.get(v.label);
              e3.remove(e2);
            }

    }

    public Graph2 kruskal()
    {
        Graph2 newGraph = new Graph2();
        PriorityQueue<Edge> kruspq = new PriorityQueue<Edge>(new EdgeComparator());

        for (String key: vertices.keySet()) 
        {
          makeSet(vertices.get(key));
        }

        ArrayList<Edge> edges;

        for (String key: vertices.keySet()) 
        {
          ArrayList<Edge> indEdges = adjacent.get(vertices.get(key).label);
        
              for (Edge e: indEdges) 
              {
                kruspq.add(e);
              }

        }
            while (!kruspq.isEmpty()) 
            {
              Edge e = kruspq.poll();

              if (findSet(e.source) != findSet(e.target)) 
              {
                newGraph.addEdge(e.source.label, e.target.label, e.weight);
                union(e.source, e.target);
              }

            }

        return newGraph;

    }

    public boolean bellmanFord(String source)
    {
        iss(source);
        Vertex u = vertices.get(source);
        ArrayList<Edge> edges = adjacent.get(u.label);

        for (int i = 1; i < edges.size() -1; i++) 
        {

              for (Edge e : edges) 
              { 
                relax(e.source, e.target, e.weight);
              }
        }
            for (Edge e : edges) 
            {
              if(e.target.distance > (e.source.distance + e.weight)) 
              {
                return false;
              }
            }

        return true;

    }
    public void dijkstra (String source) 
    {
            iss(source);
            dijkstraVertices.clear();
            PriorityQueue<Vertex> q = new PriorityQueue<Vertex>(new VertexComparator());

            for (String key : vertices.keySet()){
              q.add(vertices.get(key));
            }

            while (!q.isEmpty()) {
              Vertex u = q.poll();
              dijkstraVertices.add(u);
              ArrayList<Edge> edges = adjacent.get(u.label);

              for (Edge e: edges) {
                relax(e.source, e.target, e.weight);
              }

            }
    }

    public void relax (Vertex u, Vertex v, double weight)
    {
         if(v.distance > (u.distance + weight))
         {
            v.distance = u.distance + weight;
            v.parent = u;
         }
    } 
    public void iss(String source)
    {   
        for (String key : vertices.keySet()) {   // Reset all nodes
          Vertex u;
          u = vertices.get(key);
          u.distance = Double.POSITIVE_INFINITY;
          u.parent = null;
        }
                                
        Vertex u = vertices.get(source);        //source distance set to 0
        u.distance = 0;
    }


/*
All the set functions 
*/
    public void makeSet(Vertex x)
    {
        x.parent = x;
        x.rank = 0;
    }

    public void union (Vertex x , Vertex y)
    {
        link(findSet(x), findSet(y));
    }

    public void link (Vertex x, Vertex y)
    {
        if (x.rank > y.rank)
        {
            y.parent = x;
        }else
        {
            x.parent = y;
            if (x.rank == y.rank)
            {
                y.rank = y.rank + 1;
            }
        }
    }

    public Vertex findSet (Vertex x)
    {
        if(x != x.parent)
        {
            x.parent = findSet(x.parent);
        }
        return x.parent;
    }
/*
Comparators for comparing the weights of the edges and the distance of the vertices
*/
 class EdgeComparator implements Comparator<Edge>
{
    public int compare (Edge e1, Edge e2)
    {
        return Double.compare(e1.weight,e2.weight);
    }
}
class VertexComparator implements Comparator<Vertex> {

  public int compare(Vertex v1, Vertex v2) {
    return Double.compare(v1.distance, v2.distance);
  }

}
  
}


    

// ________________________________________________________________________________________________________

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

    int rank;

    public Vertex(String label) 
    {// initializes a vertex with the 'unvisited' status
        this.label = label;
        color = WHITE;
        distance = Double.POSITIVE_INFINITY;
        parent = null;
        discoverStep = 0;
        finishStep = 0;
    }
    
    public String toString() {
        return label;
    }

}

class Edge { 
    
    Vertex source;
    
    Vertex target;
    
    double weight;

    public Edge(Vertex source, Vertex target, double weight) 
    {// constructor for an edge 
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    public String toString() 
    { // prints out the edges with its respective target and weight 
        return "(" + source + " -> " + target + " : " + weight + ")";
    }
        public double getWeight()
        {
            return weight;
        }
    
}
