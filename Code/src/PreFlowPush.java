

import java.util.Iterator;
import java.util.LinkedList;

public class PreFlowPush {
    //double[] heights;
    double[] excess;
    int index = 0;
    Vertex v1;
    int indexofsource = -1;
    int indexofsink = -1;
    Double maxflow = (double) 0;

    // Graph vertices & edges for pre-processing
    LinkedList<Vertex> vertexList;
    LinkedList<Edge> edgeList;

    /**
     * This function returns the max flow for the input graph
     * @param g is the input graph
     * @return max flow for the input graph
     */
    @SuppressWarnings("unchecked")
    public double maxFlowPFP(SimpleGraph g) {

        // pre-processing input graph
        preprocessInputGraph(g);
        // pre-processing for Flow
        preprocessFlow();
        // pushing flow and re-labelling height
        pushAndrelabel();
       
        //finding the max flow of the input graph 
        Vertex sinkVertex = (Vertex) vertexList.get(indexofsink);
        for (Iterator<Edge> i = sinkVertex.incidentEdgeList.iterator(); i.hasNext();) {
            Edge e = i.next();
            maxflow = maxflow + (Double) e.getName();
        }
        return maxflow;
    }
    
    /**
     * This function finds the back edges and adds them to the graph 
     * @param sg is the Input graph
     */
    @SuppressWarnings("unchecked")
    public void preprocessInputGraph(SimpleGraph sg) {
        LinkedList<Edge> reverseEdgeList = new LinkedList<Edge>();

        // Copy the vertex list & edge list
        this.vertexList = sg.vertexList;
        this.edgeList = sg.edgeList;

        // update the incident edge list of each vertex so that it only contains
        // edges that start with current vertex.
        for (Iterator<Vertex> vIterator = vertexList.iterator(); vIterator.hasNext();) {
            Vertex v = vIterator.next();
            String vname = v.getName().toString();
            for (int index = 0; index < v.incidentEdgeList.size();) {
                Edge edge = (Edge) v.incidentEdgeList.get(index);
                if (!vname.equals(edge.getFirstEndpoint().getName())) {
                    v.incidentEdgeList.remove(edge);
                    reverseEdgeList.add(new Edge(edge.getSecondEndpoint(), edge.getFirstEndpoint(), 0.0, null));
                } else {
                    index++;
                }
            }
        }

        // Add the new reverse edges to the incidence edge list of each vertex &
        // to the edge list.
        for (int index = 0; index < reverseEdgeList.size(); index++) {
            Edge edge = reverseEdgeList.get(index);
            Vertex v = edge.getFirstEndpoint();
            v.incidentEdgeList.add(edge);
            edgeList.add(edge);
        }

        // Uncomment this to print the update graph.
       //  printUpdatedGraph();
    }

    private void printUpdatedGraph() {
        // print the updated graph
        System.out.println("updated graph");
        System.out.println("Iterating through vertices...");
        for (Iterator<Vertex> i = this.vertexList.iterator(); i.hasNext();) {
            Vertex v = i.next();
            System.out.println("found vertex " + v.getName());
        }

        System.out.println("Iterating through adjacency lists...");
        for (Iterator<Vertex> i = this.vertexList.iterator(); i.hasNext();) {
            Vertex v = i.next();
            System.out.println("Vertex " + v.getName());
            Iterator<Edge> j;
            for (j = v.incidentEdgeList.iterator(); j.hasNext();) {
                Edge e = j.next();
                System.out.println(e.getFirstEndpoint().getName());
                System.out.println(e.getSecondEndpoint().getName());
                System.out.println(e.getData());
            }
        }
    }
    /**
     * This function set the initial flow values and excess values
     */
    public void preprocessFlow(){
        // find start index
        findSourceSinkIndex("t");
        // find end index
        findSourceSinkIndex("s");

        // initially set excess to 0 for all the vertices
        excess = new double[vertexList.size()];
        for (Iterator<Vertex> i = vertexList.iterator(); i.hasNext();) {
            v1 = i.next();
            v1.setData((double) 0);
            excess[index] = 0;
            index++;
        }

        v1 = (Vertex) vertexList.getFirst();
        v1.setData((double) vertexList.size());

        // setting flow to 0 for all the edges
        setFlows();
        // setting excess equal to the capacity
        setExcess(); 
    }
    
    /**
     * This function sets the initial flow values
     */
    @SuppressWarnings("unchecked")
    public void setFlows() {
        double edgeName;
        int index;

        for (Iterator<Vertex> i = vertexList.iterator(); i.hasNext();) {
            Vertex vertex = i.next();
            for (Iterator<Edge> j = vertex.incidentEdgeList.iterator(); j.hasNext();) {
                Edge e = j.next();
                e.setName(e.getData());
            }
        }

        Vertex start = (Vertex) vertexList.getFirst();
        for (Iterator<Edge> k = start.incidentEdgeList.iterator(); k.hasNext();) {
            Edge e1 = k.next();
            edgeName = (Double) e1.getName();
            e1.setName((double) 0.0);
            Vertex end = e1.getSecondEndpoint();
            index = findEdge(end, start);
            Edge e2 = (Edge) edgeList.get(index);
            e2.setName(edgeName);
        }
    }

    /**
     * This function sets the initial excess values
     */
    @SuppressWarnings("unchecked")
    public void setExcess() {
        double newExcess = new Double(0);
        Vertex start = (Vertex) vertexList.getFirst();
        for (Iterator<Edge> j = start.incidentEdgeList.iterator(); j.hasNext();) {
            Edge e = j.next();
            Vertex end = e.getSecondEndpoint();
            int ind = vertexList.indexOf(end);
            excess[ind] = (Double) e.getData();
            newExcess = newExcess + (Double) e.getData();
        }
        excess[0] = -newExcess;
    }

    /**
     * This function sets does the push and relabel operation on the flow 
     */
    public void pushAndrelabel(){
        // setting the excess index value
        boolean noRelabel = false;
        index = 0;
        int excessUindex = -10;
        int excessCount = 0;
        while (true) {
            for (Iterator<Vertex> i = vertexList.iterator(); i.hasNext();) {
                Vertex v = i.next();
                if (excess[index] > 0 && !(v.getName().equals("t"))) {
                    v1 = v;
                    excessUindex = index;
                    excessCount++;
                    break;
                }
                index++;
            }
            index = 0;
            if (excessCount == 0) {
                break;
            }
            excessCount = 0;

            for (Iterator<Edge> k = v1.incidentEdgeList.iterator(); k.hasNext();) {
                Edge edgev1v2 = k.next();
                Double flowv1v2 = (Double) edgev1v2.getName();
                Vertex v2 = edgev1v2.getSecondEndpoint();
                Double heightv1 = (Double) v1.getData();
                Double heightv2 = (Double) v2.getData();
                int excessVindex = vertexList.indexOf(v2);
                int indexEdgev1v2 = edgeList.indexOf(edgev1v2);
                Double capacityEdgev1v2 = (Double) edgev1v2.getName();
                Double excessU = excess[excessUindex]; 
                if (flowv1v2 > 0 && (heightv1 > heightv2)) {
                    int indexEdgev2v1 = findEdge(v2, v1);
                    Push(excessU, capacityEdgev1v2, indexEdgev1v2, indexEdgev2v1, excessUindex, excessVindex);
                    noRelabel = true;
                    break;
                }
            }
            if (noRelabel != true) {
                Relabel(vertexList.indexOf(v1));
            }
            noRelabel = false;
        }

    }

    /**
     * This function add's flow when there is forward edge and deletes flow when there is reverse edge 
     * @param excessU is the excess at vertex u
     * @param cEdgeUV is the capacity of edge uv
     * @param indexEdgeUV is the index of edge uv
     * @param indexEdgeVU is the index of edge vu
     * @param excessUindex is the index of excess of vertex u
     * @param excessVindex is the index of excess of vertex v
     */
    public void Push(Double excessU, Double cEdgeUV, int indexEdgeUV, int indexEdgeVU, int excessUindex,
            int excessVindex) {
        Double deltaFlowUV = Math.min(excessU, cEdgeUV);
        Edge e1 = (Edge) edgeList.get(indexEdgeUV);
        Edge e2 = (Edge) edgeList.get(indexEdgeVU);
        Double flowVU = (Double) e1.getName();
        Double flowUV = (Double) e2.getName();
        e1.setName(flowVU - deltaFlowUV);
        e2.setName(flowUV + deltaFlowUV);
        excess[excessUindex] = excess[excessUindex] - deltaFlowUV;
        excess[excessVindex] = excess[excessVindex] + deltaFlowUV;
    }
    
    /**
     * This function increases the height of vertex v by 1
     * @param relabelVertex
     */
    public void Relabel(int relabelVertex) {
        Vertex v = (Vertex) vertexList.get(relabelVertex);
        v.setData((Double) v.getData() + 1);
    }
   
    /**
     * This function finds the index of the edge
     * @param start is the start vertex of edge
     * @param end is the end vertex of edge
     * @return
     */
    @SuppressWarnings("unchecked")
    public int findEdge(Vertex start, Vertex end) {
        for (Iterator<Edge> j = start.incidentEdgeList.iterator(); j.hasNext();) {
            Edge e = j.next();
            if (e.getSecondEndpoint() == end) {
                return edgeList.indexOf(e);
            }
        }
        return -1;
    }

    /**
     * This function finds the source and sink vertex indexes
     * @param vertexName is the name of the vertex
     */
    public void findSourceSinkIndex(String vertexName) {
        int k = 0;
        for (Iterator<Vertex> iterator = vertexList.iterator(); iterator.hasNext(); k++) {
            Vertex v = iterator.next();
            if (vertexName.equals(v.getName())) {
                if (vertexName.equals("t")) {
                    indexofsink = k;
                    break;
                } else if (vertexName.equals("s")) {
                    indexofsource = k;
                    break;
                }
            }
        }
    }

}
