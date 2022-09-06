package graph;
import java.util.ArrayList;
/**
 * This class implements general operations on a graph as specified by UndirectedGraphADT.
 * It implements a graph where data is contained in Vertex class instances.
 * Edges between verticies are unweighted and undirected.
 * A graph coloring algorithm determines the chromatic number. 
 * Colors are represented by integers. 
 * The maximum number of vertices and colors must be specified when the graph is instantiated.
 * You may implement the graph in the manner you choose. See instructions and course material for background.
 */
 
 public class UndirectedUnweightedGraph<T> implements UndirectedGraphADT<T> {
   // private class variables here.
   
   private int MAX_VERTICES;
   private int MAX_COLORS;
   private ArrayList<Vertex<T>> vertices;
   private ArrayList<ArrayList<Vertex<T>>> list;
   private int numEdges = 0;
   
   /**
    * Initialize all class variables and data structures. 
   */   
   public UndirectedUnweightedGraph (int maxVertices, int maxColors){
      MAX_VERTICES = maxVertices;
      MAX_COLORS = maxColors; 
      vertices = new ArrayList<Vertex<T>> (MAX_VERTICES);
      list = new ArrayList<ArrayList<Vertex<T>>>();
      for( int i = 0; i < MAX_VERTICES; i++){
        list.add(new ArrayList<Vertex<T>>());
      }
   }

   /**
    * Add a vertex containing this data to the graph.
    * Throws Exception if trying to add more than the max number of vertices.
   */
   public void addVertex(T data) throws Exception {
      if (vertices.size() >= MAX_VERTICES){
        throw new Exception();
      }
      Vertex<T> vert = new Vertex<T>(data);
      ArrayList<Vertex<T>> vert1 = new ArrayList<Vertex<T>>();
      vertices.add(vert);

      vert1.add(vert);
      list.add(vert1);
   }
   
   /**
    * Return true if the graph contains a vertex with this data, false otherwise.
   */   
   public boolean hasVertex(T data){
      boolean has = false;

      Vertex<T> vert = new Vertex<T>(data);
      for ( int i = 0; i < vertices.size(); i++){
        if ( vertices.get(i).getData().equals(data)){
          has = true;
          break;
        }
      }
      return has;
   } 

   /**
    * Add an edge between the vertices that contain these data.
    * Throws Exception if one or both vertices do not exist.
   */   
   public void addEdge(T data1, T data2) throws Exception{
      
      if ( (hasVertex(data1) == false)  || (hasVertex(data2) == false) ){
        throw new Exception();
      }

      int index1 = getHelp(data1);
      int index2 = getHelp(data2);
      numEdges++;

      
      list.get(index1).add(list.get(index2).get(0));
      list.get(index2).add(list.get(index1).get(0));

   }

   private int getHelp(T data){
     int index = -1;
     for ( int i = 0; i < list.size(); i++){
       if ( !list.get(i).isEmpty() ){
         if ( list.get(i).get(0).getData().equals(data)){
           index = i;
           break;
        }
      }
    }
     return index;
   }

   /**
    * Get an ArrayList of the data contained in all vertices adjacent to the vertex that
    * contains the data passed in. Returns an ArrayList of zero length if no adjacencies exist in the graph.
    * Throws Exception if a vertex containing the data passed in does not exist.
   */   
   public ArrayList<T> getAdjacentData(T data) throws Exception{

      if ( hasVertex(data) == false ){
        throw new Exception();
      }

      int index = getHelp(data);
      ArrayList<T> inner = new ArrayList<T>();
      for ( int i = 1; i < list.get(index).size(); i++){
        inner.add(list.get(index).get(i).getData());
      }
      return inner;
   }
   
   /**
    * Returns the total number of vertices in the graph.
   */   
   public int getNumVertices(){
      return vertices.size();
   }

   /**
    * Returns the total number of edges in the graph.
   */   
   public int getNumEdges(){
      return numEdges;
   }

   /**
    * Returns the minimum number of colors required for this graph as 
    * determined by a graph coloring algorithm.
    * Throws an Exception if more than the maximum number of colors are required
    * to color this graph.
   */   
   public int getChromaticNumber() throws Exception{
      int highestColor = -1;
      int colorToUse = -1;
      Vertex<T> curVertex = new Vertex<T>(null);;

      for ( int i = 0; i < list.size(); i++){
        if ( !list.get(i).isEmpty() ){
          curVertex = list.get(i).get(0);
        }
        if(curVertex.getColor() == -1 ){
          colorToUse = getColorToUse(curVertex);
          curVertex.setColor(colorToUse);
          if ( highestColor < colorToUse ){
            highestColor = colorToUse;
          }
        }
      }
      if ( highestColor >= MAX_COLORS){
        throw new Exception();
      }
      return highestColor;
   }

   private int getColorToUse(Vertex<T> curVertex){
     int colorToUse = -1;
     boolean[] adj = new boolean[MAX_COLORS];
     for ( int i = 0; i < adj.length; i++){
       adj[i] = false;
     }
     ArrayList<Vertex<T>> l1 = getAdjacentVertices(curVertex);
     
     for ( int i = 0; i  < l1.size(); i++){
       if ( l1.get(i).getColor() != -1){
         adj[i] = true;
       }
     } 
     for ( int i = 0; i < adj.length; i++){
       if (adj[i] == false){
        colorToUse = i;
        break;
       }
     }
     return colorToUse+1;
   }

   private ArrayList<Vertex<T>> getAdjacentVertices(Vertex<T> curVertex){

    ArrayList<Vertex<T>> arr = new ArrayList<Vertex<T>>();  
 
    for ( int i = 0; i < list.size(); i++){
      if ( !list.get(i).isEmpty() ){
        if ( list.get(i).get(0).equals(curVertex)){
          for ( int j = 1; j < list.get(i).size(); j++){
            arr.add(list.get(i).get(j));
          }
          break;
        }
      }
    }
    return arr;
   }

   
   
}