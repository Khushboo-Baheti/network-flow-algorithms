

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

/* Implementation of Scaling Max Flow algorithm 
 * 
 * The Algorithm follow below steps:
 * 1. Start with flow = 0 (f(e) = 0) for all edges e in Graph
 * 2. Set scaling parameter to be the largest power of 2 which is
 * 		less than or equal to the maximum capacity out of s 
 * 3. While scaling parameter is greater than equal to 1, we will
 * 		keep find augmenting paths in residual graph with large residual 
 * 		capacity for as long as possible(capacity at least equal to scaling parameter)
 * 		and updating the flow in original graph.
 * 4. Decrease the scaling parameter with a factor of 2 in each iteration.
 * 5. Returning the flow at the end which is maximum flow from the algorithm
 * 
 * Author: Chhaya Choudhary
 */

public class ScalingFordFulkerson {

	/*
	 * Goal is to find augmenting paths efficiently and with fewer iterations.
	 * Inputs: graph = input graph, s = source, and t = sink
	 * Output: Returns maximum flow in the graph
	 */
	private SimpleGraph graph;
	private ArrayList<Edge> augmentPath;
	private Vertex source;
	private Vertex sink;
	
	public ScalingFordFulkerson(String filePath){
		graph = new SimpleGraph();
		Hashtable table = GraphInput.LoadSimpleGraph(graph, filePath);
		
		/* Assume that the source and sink are labeled as s and t */
		source = (Vertex)table.get("s");
		sink = (Vertex)table.get("t");
	}
	
	/* This function calculates scaling parameter and returns maximum flow */
	double scalingMaxFlow() {
		
		/* ScalingParameter = the largest power of 2 that is no longer than 
		   the maximum capacity out of source */
		double scalingParameter = calculateScalingParameter(source, graph);
		double maxFlow = 0;
		double flow = 0;

		while(scalingParameter >= 1){
			flow = getFlowSFF(scalingParameter);
			maxFlow += flow;
			/* decreasing scaling parameter with a factor of 2 with each iteration */
			if (flow == 0) {
				scalingParameter = scalingParameter/2;
			}
		}
		return maxFlow;
	}
	
	
	/* calculateScalingParameter function returns the largest power of 2 
	   that is no larger than the maximum capacity for the edges out of s */
	private double calculateScalingParameter(Vertex source, SimpleGraph graph){
		double maxCapacity = Double.MIN_VALUE;
		Iterator edges = graph.incidentEdges(source);
		while(edges.hasNext()){
			Edge e = (Edge)edges.next();
			maxCapacity = Math.max(maxCapacity, getType(e.getData()));
		}
		double delta = 0;
		double j = 1;
		while(j <= maxCapacity) {
			j *= 2;
		}
		delta = j/2;
		return delta;
 	}
	
	/* This function calculates max flow by finding augmenting paths using Depth first Search */
	double getFlowSFF(double scalingParameter) {
		augmentPath = null;
		findPathDFS(source, sink, graph, scalingParameter);
		if(augmentPath == null){
			return 0;
		}
			
		/* This function finds bottleneck */
		double bottleneckCapacity = getBottleneck(scalingParameter);
		/* This function finds augmenting paths */
		augumentingPath(bottleneckCapacity);
		return bottleneckCapacity;
	}
	
	/**
	 * This function recursively look for path in the SimpleGraph
	 * @param source:  Starting vertex 
	 * @param sink:    destination vertex
	 * @param graph:   The simple graph
	 * @param scalingParameter:  minimum capacity to look for in the path
	 * @return:  the ArrayList of edges in the graph
	 */
	private void findPathDFS(Vertex source, Vertex sink, SimpleGraph graph, double scalingParameter){
		ArrayList<Edge> path = new ArrayList<Edge>();
		findPathRecord(source, sink, graph, new HashSet<Vertex>(), scalingParameter, path);
	}

	private void findPathRecord(Vertex source, Vertex sink, SimpleGraph graph, Set<Vertex> visited, double scalingParameter, ArrayList<Edge> path) {
		/* If already visited the vertex */
		if(visited.contains(source)){
			return;
		}
		
		if (augmentPath != null) {
			return;
		}
		
		/* marking the vertex as visited */
		visited.add(source);
		
		if(source.equals(sink)){
			augmentPath = new ArrayList<>(path);
			return;
		}
		
		Iterator edges = graph.incidentEdges(source);
		while(edges.hasNext()){
			Edge edge = (Edge) edges.next();
			
			if(edge.getFirstEndpoint().getName().equals(source.getName())) {
				if(getType(edge.getData()) >= scalingParameter){
					path.add(edge);
					findPathRecord(edge.getSecondEndpoint(), sink, graph, visited, scalingParameter, path);
					path.remove(path.size() - 1);
				}
			}
		}
	}
	
	private double getBottleneck(double scalingParameter){
		double minCap = scalingParameter * 2;
		Iterator i = augmentPath.iterator();
		while(i.hasNext()){
			Edge e = (Edge) i.next();
			minCap = Math.min(scalingParameter, getType(e.getData()));
		}
		return minCap;
	}
	
	private void augumentingPath(double bottleneckCapacity){
		Iterator i = augmentPath.iterator();
		
		/* Adding flow along each path */
		while(i.hasNext()){
			Edge e = (Edge) i.next();
			
			e.setData((double)e.getData()-bottleneckCapacity);
			Vertex v1 = e.getFirstEndpoint();
			Vertex v2 = e.getSecondEndpoint();
			
			Iterator k = graph.incidentEdges(v2);
			boolean backwardEdge = false;
			
			/* finds if there is a backward edge from vertex v2 to  vertex v1 */
			while(k.hasNext()){
				Edge e2 = (Edge)k.next();
				
				if(e2.getFirstEndpoint().equals(v2) && e2.getSecondEndpoint().equals(v1)){
					/* Adding backward edge to the path */
					backwardEdge = true;
					e2.setData(getType(e2.getData()) + bottleneckCapacity);
					break;
				}
			}
			
			/* Incase of no backward edge */
			if(!backwardEdge){
				graph.insertEdge(v2, v1, bottleneckCapacity, null);
			}	
		}	
	}
	
	public static int getType(Object object)
	{
	  if (object instanceof Integer) {
	    return (int) object;
	  } else if (object instanceof Double) {
	    return ((Double) object).intValue();
	  }
	  System.err.print("Object is not an Integer or a Double.");
	  return 0;
	}
}