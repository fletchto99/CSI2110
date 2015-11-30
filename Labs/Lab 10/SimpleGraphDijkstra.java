// $Id: SimpleGraph.java,v 1.1 2006/11/11 03:16:10 jlang Exp $
// CSI2110 Fall 2006 Laboratory 9: Adjacency List and DFS
// ==========================================================================
// (C)opyright:
//
//   Jochen Lang
//   SITE, University of Ottawa
//   800 King Edward Ave.
//   Ottawa, On., K1N 6N5
//   Canada. 
//   http://www.site.uottawa.ca
// 
// Creator: jlang (Jochen Lang)
// Email:   jlang@site.uottawa.ca
// ==========================================================================
// $Log: SimpleGraphDFS.java,v $
// Revision 1.1  2006/11/11 03:16:10  jlang
// Added Lab9
// Modified by Lachlan Plant 2015/11/18
// Modified by Lucia Moura 2015/11/25
//
// ==========================================================================
import net.datastructures.Edge;
import net.datastructures.Graph;
import net.datastructures.Vertex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.StringTokenizer;


public class SimpleGraphDijkstra {

  Graph<String,String> sGraph;  
  

  /** 
   * Create a SimpleGraph from file
   */
  public SimpleGraphDijkstra( String fileName )
    throws Exception {
    sGraph = new AdjacencyListGraph<>(false);
    read( fileName );
  }


  /**
   * Read a list of edges from file
   */
  protected void read( String fileName ) 
    throws Exception {
    BufferedReader graphFile = 
      new BufferedReader( new FileReader(fileName));
    
    Hashtable<String,Vertex> vertices = new Hashtable<>();
    // Create a hash map to store all the vertices read
  
    // Read the edges and insert
    String line;
    while( ( line = graphFile.readLine( ) ) != null ) {
      StringTokenizer st = new StringTokenizer( line );
      if( st.countTokens() != 2 ) 
	throw new IOException("Incorrect input file at line " 
				    + line );
      String source = st.nextToken( );
      String dest = st.nextToken( );
      Vertex<String> sv = vertices.get( source );
      if ( sv == null ) {
	// Source vertex not in graph -- insert
	sv = sGraph.insertVertex(source); 
	vertices.put( source, sv );
      } 
      Vertex<String> dv = vertices.get( dest );
      if ( dv == null ) {
	// Destination vertex not in graph -- insert
	dv = sGraph.insertVertex(dest); 
	vertices.put( dest, dv );
      }
      // check if edge is already in graph
      if ( sGraph.getEdge( sv, dv )==null) {
	// edge not in graph -- add 
	sGraph.insertEdge(sv, dv, source + " to " + dest ); 
      }
    }
  }

  /**
   * Helper routine to get a Vertex (Position) from a string naming
   * the vertex
   */
  protected Vertex<String> getVertex( String vert ) {
    // Go through vertex list to find vertex -- why is this not a map
    for( Vertex<String> vs : sGraph.vertices() ) {
      if ( vs.getElement().equals( vert )) {
	return vs;
      }
    }
    return null;
  }

  /**
   * Please implement!
   */
  Hashtable<String,Boolean> visited;

  public void printDFS( String vert  ) {
    Vertex<String> vt = getVertex( vert );
    visited = new Hashtable<>();
    DFS(this.sGraph,vt);
  }
  private void DFS(Graph<String,String> graph, Vertex<String> v ) {
    if(visited.get(v.getElement())!=null) return;
    visited.put(v.getElement(), Boolean.TRUE);
    startVisit(v);
    for(Edge<String> e : graph.outgoingEdges(v)){
        Vertex<String> s = graph.opposite(v, e);
        DFS(graph,s);
    }
    finishVisit(v);
  }
  
  private void startVisit( Vertex<String> v ) {
    System.out.println( v.getElement() );
  }
  private void finishVisit( Vertex<String> v ) {
    System.out.println( "---" +  v.getElement() );
  }

  /**
   * Printing all the vertices in the list, followed by printing all
   * the edges
   */
  void print() {
    System.out.println( "Vertices: " + sGraph.numVertices() + 
			" Edges: " + sGraph.numEdges()); 
    
    for( Vertex<String> vs : sGraph.vertices() ) {
      System.out.println( vs.getElement() );
    }
    for( Edge<String> es : sGraph.edges() ) {
      System.out.println( es.getElement() );
    }
    return;
  }


  /** Helper method:
   * Read a String representing a vertex from the console
   */
  public static String readVertex() throws IOException {
    System.out.print( "[Input] Vertex: " );
    BufferedReader reader = 
      new BufferedReader(new InputStreamReader ( System.in ));
    return reader.readLine();
  }

  /**
   * Generate a Graph from File and prints the vertices visited
   * by a DepthFirstSearch
   */
  public static void main( String[] argv ) {
    if ( argv.length < 1 ) {
      System.err.println( "Usage: java SimpleGraph fileName" );
      System.exit(-1);
    }
    try {
      SimpleGraphDijkstra sGraph = new SimpleGraphDijkstra( argv[0] );
      sGraph.print();
      // Ask for vertex to start
      System.out.println( "Start Vertex for DFS:");
      sGraph.printDFS(readVertex());
    }catch ( Exception except ) {
      System.err.println(except);
      except.printStackTrace();
    }
  }
}
