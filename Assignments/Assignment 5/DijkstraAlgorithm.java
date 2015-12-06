import net.datastructures.Edge;
import net.datastructures.Entry;
import net.datastructures.HeapAdaptablePriorityQueue;
import net.datastructures.Vertex;

import java.util.LinkedList;

public class DijkstraAlgorithm<V> {
    
	boolean executed = false;
	
	public<T> ShortestPathsInfo findShortestPaths(Vertex<T> source, AdjacencyListGraph<T, Integral> graph) {
		
		int sourceIndex=graph.getPositionOfVertex(source);
		ShortestPathsInfo path = new ShortestPathsInfo(sourceIndex,graph.numVertices());
		for (int i=0; i< graph.numVertices(); i++) {
		  path.dist[i]=Integer.MAX_VALUE;
		  path.prevVertex[i]=-1;
		  path.prevEdge[sourceIndex]=null;
		}
	    path.dist[sourceIndex]=0;
	    
	    HeapAdaptablePriorityQueue<Integer,Vertex<T>> PQ= new HeapAdaptablePriorityQueue<>();
		Entry[] posInQueue= new Entry[graph.numVertices()];

	    for (int i=0; i< graph.numVertices(); i++) {

			posInQueue[i]=PQ.insert(path.dist[i], graph.getVertexAtPosition(i));
	    }
	  
	    while (!PQ.isEmpty()) {
	    	
	    	Entry<Integer,Vertex<T>> minEntry = PQ.removeMin();
	    	Vertex<T> cloudVertex= minEntry.getValue();
	    	int posCloudVertex=graph.getPositionOfVertex(cloudVertex);
	    	posInQueue[posCloudVertex]=null;
	    	
	    	for (Edge<Integral> e: graph.outgoingEdges(cloudVertex)) {
	           Vertex<T> v=graph.opposite(cloudVertex, e);
	           int posv=graph.getPositionOfVertex(v);
	           
	           if (posInQueue[posv]!=null) {
	        	   int valueEdge=e.getElement().toIntegral();
	        	   if (path.dist[posCloudVertex]+ valueEdge< path.dist[posv]) {
	        		   path.dist[posv]=path.dist[posCloudVertex]+ valueEdge;
	        		   PQ.replaceKey(posInQueue[posv], path.dist[posv]);
	        		   path.prevVertex[posv]=posCloudVertex;
	        		   path.prevEdge[posv]=e;
	        	   }
	           }
	    	}
	    	
	    }
		
		executed=true;	
		return path;
	}
	
	// inner class ShortestPathsInfo to facilitate shortest path info tracking
	public class ShortestPathsInfo {
		private int sourceVertex;
		private int numVertices;
		int [] dist;
		int [] prevVertex;
		Edge [] prevEdge;
		
	
		public ShortestPathsInfo(int sourceVertex, int numVertices) {
			this.sourceVertex=sourceVertex;
			this.numVertices = numVertices;
			dist=new int[numVertices];
			prevVertex= new int[numVertices];
			prevEdge= new Edge[numVertices];
		}
		
		int[] getDist() {return dist;}
		
		int getDist(int i) throws IllegalArgumentException {
			if (i>=dist.length)  throw new IllegalArgumentException();
			return dist[i];
			} 
		
		int [] getPrevVertex() { return prevVertex;}
		
		Edge[] getPrevEdge() { return prevEdge;}

		int getSourceVertex() { return sourceVertex;}

		int getNumVertices() { return numVertices;}
		
		boolean isReachable(int i) { return (prevVertex[i]!=-1); }
		
		int [] pathFromSourceTo(int i) throws IllegalArgumentException{
			if (i>=numVertices)  throw new IllegalArgumentException();
			LinkedList<Integer> pathFound= new LinkedList<>();
			int last=i;
			while (last!=-1) {
				pathFound.addFirst(last);
				last=prevVertex[last];
			}
			
			int[] path=new int[pathFound.size()];
			int k=0;
			for (int value:pathFound) {
				path[k]=value; 
				// debugging printouts System.out.print(path[k]);
				k++;
			}
			// debugging printouts System.out.println();
	
			return path;
		}
		
	
       public String stringPathFromSourceToVertex(Vertex<V> v,AdjacencyListGraph<V, Integral> graph) {
    	   int index=graph.getPositionOfVertex(v);
    	   String s="";
    	   int[] path=pathFromSourceTo(index);
    	   s=s+graph.getVertexAtPosition(path[0]).getElement()+"\n";
    	   for (int i=1; i < path.length; i++) {
    		   s = s+prevEdge[path[i]].getElement()+"\n"
    	           +graph.getVertexAtPosition(path[i]).getElement()+"\n";
    	   }
    	   return s+"\n";    	   
       }
       
       
	}

}
