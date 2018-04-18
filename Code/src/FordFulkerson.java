

import java.util.HashMap;
import java.util.Iterator;

/* Ford Fulkerson algorithm 
 * 
 * Steps:
 * 1. Create residual graph
 * 2. While there is a s-t path in residual graph
 * 		a) Find the augment path with bottleneck
 * 		b) Update the flow and residual graph
 * 3. Return max flow
 * 
 * @Author: Khushboo Baheti
 */

public class FordFulkerson {
	private Vertex s; // Source in residual graph
	private Vertex t; // Sink in residual graph

	// Get max flow using Ford Fulkerson algorithm
	public double maxFlowFF(SimpleGraph G) {
		// Create residual graph and set s, t
		SimpleGraph rGraph = createResidualGraph(G);
		if (s == null || t == null) {
			System.out.println("Not a valid graph");
			System.exit(0);
		}

		Vertex u, v;
		// parent will be used to identify the direction of the graph (the parent of the vertex)
		HashMap<Vertex, Vertex> parent = new HashMap<Vertex, Vertex>();
		// initial value of max flow
		double maxFlow = 0;  
		// while loop will run until there is no st path 
		while (dfs(rGraph, s, t, parent))       // call dfs to find st path in rGraph
		{
			// call augment to get augment path in s-t
			double augment_flow = augment(parent, s, t);

			//Update the residual graph
			for (v = t; v != s; v = parent.get(v))
			{
				u = parent.get(v);
				Edge uv, vu; 
				// decrease the capacity of forward edge(value of flow in original graph) with augment path 
				uv =  getEdge(u,v);
				uv.setData((Double)uv.getData() - augment_flow);
				// increase the capacity of backward edge(reduce flow upto in original) by augment path
				vu =  getEdge(v,u);
				vu.setData((Double)vu.getData() + augment_flow);
			}
			maxFlow += augment_flow;
		}

		return maxFlow;
	}
	
	private SimpleGraph createResidualGraph(SimpleGraph G) {
		// This stores mapping of vertices from original graph to residual graph. Needed for copying the graph
		HashMap<Vertex, Vertex> origToRes = new HashMap<Vertex, Vertex>();

		// copy the G into G'
		SimpleGraph rGraph = new SimpleGraph();				// rGraph representing residual graph G'

		// Copy the vertices
		for(Iterator i = G.vertices(); i.hasNext(); ) {
			Vertex orig = (Vertex)i.next();
			Vertex resi = rGraph.insertVertex(orig.getData(), orig.getName());
			origToRes.put(orig, resi);
			
			// Set source and sink vertex in residual graph
			if (resi.getName().equals("s")) {
				s = resi;
			} else if (resi.getName().equals("t")) {
				t = resi;
			}
		}

		// Copy forward edges
		for(Iterator i = G.edges(); i.hasNext(); ) {
			Edge e = (Edge)i.next();
			Vertex first = origToRes.get(e.getFirstEndpoint());
			Vertex second = origToRes.get(e.getSecondEndpoint());
			rGraph.insertEdge(first, second, e.getData(), e.getName());
			// Since we're adding backward edges we can safely remove this edge from second's incidentEdgeList.
			second.incidentEdgeList.removeLast();
		}

		// Generate backward edges and re-use if there is any backward edge already 
		for(Iterator i = G.edges(); i.hasNext(); ) {
			Edge e = (Edge)i.next();
			Vertex first = origToRes.get(e.getFirstEndpoint());
			Vertex second = origToRes.get(e.getSecondEndpoint());
			if (getEdge(second, first) == null) {
				rGraph.insertEdge(second, first, 0.0, null);
				// Since we've added forward edges we can safely remove this edge from second's incidentEdgeList.
				first.incidentEdgeList.removeLast();
			}
		}
		return rGraph;
	}

	private boolean dfs(SimpleGraph rGraph, Vertex s, Vertex t, HashMap<Vertex, Vertex> parent) {
		HashMap<Vertex, Boolean> visited = new HashMap<Vertex, Boolean>();
		parent.put(s, null);
		// Find if there exist a path from s to t
		return dfsInternal(rGraph, s, t, parent, visited);
	}
	
	private boolean dfsInternal(SimpleGraph rGraph, Vertex s, Vertex t, HashMap<Vertex, Vertex> parent, HashMap<Vertex, Boolean> visited) {
		visited.put(s, true);

		// If source is equal to destination then return
		if (s == t) {
			return true;
		}

        for (Iterator i = s.incidentEdgeList.iterator(); i.hasNext(); ) {
			Edge e = (Edge)i.next();
			if (e.getFirstEndpoint() != s)
				continue;
			Vertex temp = rGraph.opposite(s, e);
			if (!visited.containsKey(temp) && (Double)e.getData() > 0) {
				// Set the parent for newly found vertex
				parent.put(temp, s);
				dfsInternal(rGraph, temp, t, parent, visited);
			}
		}
        // return true if t is successfully found
        return visited.containsKey(t);
	}

	// get edge from u to v
	private Edge getEdge(Vertex u, Vertex v) {
		Edge uv = null; 
		for(Iterator i = u.incidentEdgeList.iterator(); i.hasNext(); ) {
			Edge curEdge = ((Edge)i.next());
			if (curEdge.getFirstEndpoint() == u && curEdge.getSecondEndpoint() == v) {
				uv = curEdge;
				break;
			}
		}
		return uv;
	}

	private double augment(HashMap<Vertex, Vertex> parent, Vertex s, Vertex t)
	{
		double bottleneck = Double.MAX_VALUE; 
		Vertex u,v;
		// for loop to get bottleneck(minimum flow t-s path)
		// initialize v = t and keep changing the value of v by the parent of previous v 
		for (v = t; v != s; v = parent.get(v))       // start from t bcz the parent of t will lie btw t to s path 
		{
			// set the parent_flow value with minimum value of t-s path
			u = parent.get(v);
			// Find edge from u to v
			Edge uv = getEdge(u,v);
			bottleneck = Math.min(bottleneck, (Double)uv.getData());
		}

		return bottleneck;
	}
}
