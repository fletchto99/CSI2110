/*
 * AdjacencyListGraph: a simple implementation of the Graph interface.
 * 
 * The simplification is that it does not allow deletion of edges and vertices;
 * those methods are dummy and should not be called.
 * 
 * It also minimizes the dependency on the package net.datastructures by
 * by Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser,
 * in that it uses only the interfaces Graph, Edge and Vertices.
 * 
 * Edges are stored in edges, a java.util.LikedList
 * 
 * Vertices are stored in vertices, a java.util.ArrayList
 * Vertices can be accessed by position via getVertexAtPosition(int) in constant time.
 * The position of a vertex can be obtained via getPositionOfVertex(Vertex(V)) in constant time.
 * 
 * Most other methods are based on modifications to methods in
 * net.datastructures.AdjacencyMapGraph
 * 
 * Written by Lucia Moura, 2015
 *
 */

import java.util.ArrayList;
import java.util.LinkedList;
import net.datastructures.Edge;
import net.datastructures.Graph;
import net.datastructures.Vertex;


public class AdjacencyListGraph<V,E> implements Graph<V,E>{
	 private boolean isDirected;
     ArrayList<Vertex<V>> vertices;
     LinkedList<Edge<E>> edges;
     
     AdjacencyListGraph (boolean directed) {
    	 vertices=new ArrayList<>();
    	 edges= new LinkedList<>();
    	 isDirected=directed;
     }
		   
	 /** Returns the number of vertices of the graph */
	  public int numVertices() {return vertices.size(); }

	  /** Returns the number of edges of the graph */
	  public int numEdges() { return edges.size(); }

	  /** Returns the vertices of the graph as an iterable collection */
	  public Iterable<Vertex<V>> vertices() { return vertices; }

	  /** Returns the edges of the graph as an iterable collection */
	  public Iterable<Edge<E>> edges() { return edges; }
	  
	  /** Returns the vertex at position i of the ArrayList of vertices */
	  public Vertex<V> getVertexAtPosition(int i) throws IndexOutOfBoundsException {	  
		  return vertices.get(i);
	  }
	  
	  public int getPositionOfVertex(Vertex<V> v) {
		  InnerVertex<V> vert = validate(v);
		  return vert.getPosition();
	  }

	  /**
	   * Returns the number of edges for which vertex v is the origin.
	   * Note that for an undirected graph, this is the same result
	   * returned by inDegree(v).
	   * @throws IllegalArgumentException if v is not a valid vertex
	   */
	  public int outDegree(Vertex<V> v) throws IllegalArgumentException {
	    InnerVertex<V> vert = validate(v);
	    return vert.getOutgoing().size();
	  }
	  /**
	   * Returns the number of edges for which vertex v is the destination.
	   * Note that for an undirected graph, this is the same result
	   * returned by outDegree
	   * @throws IllegalArgumentException if v is not a valid vertex
	   */

	  public int inDegree(Vertex<V> v) throws IllegalArgumentException {
	    InnerVertex<V> vert = validate(v);
	    return vert.getIncoming().size();
	  }
	  /**
	   * Returns an iterable collection of edges for which vertex v is the origin.
	   * Note that for an undirected graph, this is the same result
	   * returned by incomingEdges.
	   * @throws IllegalArgumentException if v is not a valid vertex
	   */
	  public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) throws IllegalArgumentException {
		    InnerVertex<V> vert = validate(v);
		    return vert.getOutgoing();   // edges are the values in the linked list
		  }

	  /**
	   * Returns an iterable collection of edges for which vertex v is the destination.
	   * Note that for an undirected graph, this is the same result
	   * returned by outgoingEdges(v).
	   * @throws IllegalArgumentException if v is not a valid vertex
	   */
	  public Iterable<Edge<E>> incomingEdges(Vertex<V> v) throws IllegalArgumentException {
	    InnerVertex<V> vert = validate(v);
	    return vert.getIncoming();   // edges are the values in the linked list
	  }
	  
	  /** Removes a vertex and all its incident edges from the graph. */
	  public void removeVertex(Vertex<V> v) {
		  System.out.println("Warning: removeVertext not implemented");
	  }

	  /** Removes an edge from the graph. */
	  public void removeEdge(Edge<E> e) {
		  System.out.println("Warning: removeVertext not implemented");
	  }
	  

	  /** Returns the edge from u to v, or null if they are not adjacent. */
	  public Edge<E> getEdge(Vertex<V> u, Vertex<V> v) throws IllegalArgumentException {
	    InnerVertex<V> origin = validate(u);
	   
	    for (Edge<E> edge:origin.getOutgoing()) {	
	    	if (opposite(origin, edge)==v)
	    		return edge;
	    }
	    return null;    // will be null if no edge from u to v
	  }

	  /**
	   * Returns the vertices of edge e as an array of length two.
	   * If the graph is directed, the first vertex is the origin, and
	   * the second is the destination.  If the graph is undirected, the
	   * order is arbitrary.
	   */
	  public Vertex<V>[] endVertices(Edge<E> e) throws IllegalArgumentException {
	    InnerEdge<E> edge = validate(e);
	    return edge.getEndpoints();
	  }

	  /** Returns the vertex that is opposite vertex v on edge e. */
	  public Vertex<V> opposite(Vertex<V> v, Edge<E> e)
	                                               throws IllegalArgumentException {
	    InnerEdge<E> edge = validate(e);
	    Vertex<V>[] endpoints = edge.getEndpoints();
	    if (endpoints[0] == v)
	      return endpoints[1];
	    else if (endpoints[1] == v)
	      return endpoints[0];
	    else
	      throw new IllegalArgumentException("v is not incident to this edge");
	  }

	  /** Inserts and returns a new vertex with the given element. */
	  public Vertex<V> insertVertex(V element) {
	    InnerVertex<V> v = new InnerVertex<>(element, isDirected);
	    vertices.add(v);
	    v.setPosition(vertices.size()-1);
	    return v;
	  }
	  

	  /**
	   * Inserts and returns a new edge between vertices u and v, storing given element.
	   *
	   * @throws IllegalArgumentException if u or v are invalid vertices, or if an edge already exists between u and v.
	   */
	  public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element)
	                                               throws IllegalArgumentException {
	    if (getEdge(u,v) == null) {
	      InnerEdge<E> e = new InnerEdge<>(u, v, element);
	      edges.addLast(e);
	      InnerVertex<V> origin = validate(u);
	      InnerVertex<V> dest = validate(v);
	      origin.getOutgoing().addLast(e);
	      dest.getIncoming().addLast(e);
	      return e;
	    } else
	      throw new IllegalArgumentException("Edge from u to v exists");
	  }


	  @SuppressWarnings({"unchecked"})
	  private InnerVertex<V> validate(Vertex<V> v) {
	    if (!(v instanceof InnerVertex)) throw new IllegalArgumentException("Invalid vertex");
	    InnerVertex<V> vert = (InnerVertex<V>) v;     // safe cast
	    if (!vert.validate(this)) throw new IllegalArgumentException("Invalid vertex");
	    return vert;
	  }

	  @SuppressWarnings({"unchecked"})
	  private InnerEdge<E> validate(Edge<E> e) {
	    if (!(e instanceof InnerEdge)) throw new IllegalArgumentException("Invalid edge");
	    InnerEdge<E> edge = (InnerEdge<E>) e;     // safe cast
	    if (!edge.validate(this)) throw new IllegalArgumentException("Invalid edge");
	    return edge;
	  }
	  
	  public void print() {
			// printing graph for verification
			System.out.println( "Printing Graph Information\nNumber of Vertices: " + numVertices() + 
					" Number of Edges: " + numEdges()); 
		 
		    for( Vertex<V> vs : vertices() ) {
		      System.out.println("Vertex: "+vs.getElement() );
		    }
		    
		    for( Edge<E> es : edges() ) {
		      System.out.println("Edges: "+es.getElement() );
		    }
	  }
	  
	  
	  
		 private class InnerVertex<V> implements Vertex<V> {
			    private V element;
			    private int pos=-1;
			    private LinkedList<Edge<E>> outgoing, incoming;
			    
			    /** Constructs a new InnerVertex instance storing the given element. */
			    public InnerVertex(V elem, boolean graphIsDirected) {
			      element = elem;
			      outgoing = new LinkedList<Edge<E>>();
			      if (graphIsDirected)
			        incoming = new LinkedList<Edge<E>>();
			      else
			        incoming = outgoing;    // if undirected, alias outgoing map
			    }
			    /** Validates that this vertex instance belongs to the given graph. */
			    public boolean validate(Graph<V,E> graph) {
			      return (AdjacencyListGraph.this == graph && pos != -1);
			    }

			    /** Returns the element associated with the vertex. */
			    public V getElement() { return element; }

			    /** Stores the position of this vertex within the graph's vertex list. */
			    public void setPosition(int p) { pos = p; }

			    /** Returns the position of this vertex within the graph's vertex list. */
			    public int getPosition() { return pos; }

			    /** Returns reference to the underlying map of outgoing edges. */
			    public LinkedList<Edge<E>> getOutgoing() { return outgoing; }

			    /** Returns reference to the underlying map of incoming edges. */
			    public LinkedList<Edge<E>> getIncoming() { return incoming; }
			  } //------------ end of InnerVertex class ------------

		 private class InnerEdge<E> implements Edge<E> {
			        private E element;
			        private Vertex<V>[] endpoints;

			        @SuppressWarnings({"unchecked"})
			        /** Constructs InnerEdge instance from u to v, storing the given element. */
			        public InnerEdge(Vertex<V> u, Vertex<V> v, E elem) {
			          element = elem;
			          endpoints = (Vertex<V>[]) new Vertex[]{u,v};  // array of length 2
			        }
		 
			        /** Returns the element associated with the edge. */
			        public E getElement() { return element; }

			        /** Returns reference to the endpoint array. */
			        public Vertex<V>[] getEndpoints() { return endpoints; }

			        /** Validates that this edge instance belongs to the given graph. */
			        public boolean validate(Graph<V,E> graph) {
			          return AdjacencyListGraph.this == graph && element != null;
			        }


			      } //------------ end of InnerEdge class ------------
	
}
