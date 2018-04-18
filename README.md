# NETWORK-FLOW-ALGORITHMS

Implemented Three Network Flow Algorithms and Analyzed Performance Varying the Input Parameters

SOFTWARE REQUIREMENTS : JAVA [JDK1.7 and above]

The graph_code folder contains the following folders:
1. "bin/Code" folder contains the compiled class files for each of the java files.
2. "input_testing_files" folder contains input graph files for each of the 4 graphs namely, bipartite,
    fixed degree, mesh, random in bipartite_graphs, fixeddegree_graphs, mesh_graphs and random_graphs subfolder.
    Input files for bipartite graphs are present in 3 different subfolders according to parameters taken as input:
    1.Fixed_Vertices_Varying_Capacities 
    2.Fixed_Vertices_Varying_Probability 
    3.Varying_Vertices_Fixed_Probability 

    likewise, input files for fixed degree graphs re present in 3 subfolders according to parameters taken as input:
    1.Fixed_Vertices_Varying_Capacities 
    2.Fixed_Vertices_Varying_Probability 
    3.Varying_Vertices_Fixed_Probability

    Input files for mesh graphs are present in "mesh_graphs" folder.
    Input files for random graphs are present in "Input_Files_Random_Graph" folder.

3. Code/Makefile:
Update this JVM version to be the one installed on machine. Check using java --version or more verbose command java -XshowSettings:properties -version

STEPS TO RUN CODE:
1) Go to the command line and navigate to the "class_files" folder and execute the following command:
"java tcss543 path\filename.txt"
Where path is the path of the input files in "input_testing_files" subfolders provided and filename refers to each of the input file for bipartite,mesh,random and fixed degree graphs.
for example: the output of a file in mesh graph can be seen by executing the following command:

java tcss543 graph_code\input_testing_files\mesh_graphs\mesh_5row_10col.txt

Output should be like:
C:\Users\chhay\Documents\TCSS_543_Adv_algo\Project\final_docs_for_algo\graph_code\class_files>java tcss543 ..\input_testing_files\mesh_graphs\mesh_5row_10col.txt
Successfully loaded 135 lines.
Max flow Ford-Fulkerson: 5.0
Time taken by Ford Fulkerson: 5 ms
Successfully loaded 135 lines.
Max flow Scaling Ford-Fulkerson: 5.0
Time taken by Scaling Ford Fulkerson: 3 ms
Successfully loaded 135 lines.
Max flow PreFlow Push: 5.0
Time taken by Preflow Push : 4 ms

Note: we have updated Edge.java file to include the function 
public void setName(Object name): This function sets the name of the edge.

CODE DOCUMENTATION: 
FordFulkerson.java
Ford Fulkerson algorithm is implemented in FordFulkerson.java. It contains these methods:

double maxFlowFF(SimpleGraph G): 
Description:
This is the only public method exposed by FordFulkerson class. It takes a SimpleGraph object as input and the return value is max flow for that graph. The steps to calculate maximum flow includes 1) Create a residual graph 2) while there is s-t path get the bottleneck edge and augment the residual graph. These tasks are performed using private helper methods explained later.

Input:
An instance of SimpleGraph class. The graph must have vertices named “s” and “t”.

Output:
Maximum flow for the given input graph. The return type is ‘double’.

The above method maxFlowFF uses these private helper methods to calculate max flow:

1. SimpleGraph createResidualGraph(SimpleGraph G):
The first step of the algorithm is to create a residual graph. This helper method is used to create a new residual graph from given input graph. All the operations are performed on residual graph.
2. boolean dfs(SimpleGraph rGraph, Vertex s, Vertex t, HashMap<Vertex, Vertex> parent):
This method returns true if there exist a s-t path in the graph. This gets run repeatedly until a bottleneck edge can be found and residual graph can be augmented.
3. double augment(HashMap<Vertex, Vertex> parent, Vertex s, Vertex t)
Task of this method is to find the bottleneck edge and perform augmentation step of the algorithm.

ScalingFordFulkerson.java
ScalingFordFulkerson.java file contains the implementation details of Scaling max flow algorithm. The algorithm contains two methods namely: 
1. ScalingFordFulkerson(String filepath) for loading the input graph and labelling the source and sink as s      and t, and 
2. scalingMaxFlow() calculates the scaling parameter and returns the maximum flow. 
   scalingMaxFlow contains two methods: calculateScalingParameter(source, graph) for calculating the scaling parameter and getFlowSFF(scalingParameter) for calculating the bottleneck capacity and returning maximum flow by finding augmenting paths using Depth First Search(DFS).
   
   calculateScalingParameter(source, graph): This method calculates the scaling parameter which is the largest power of 2 that is no larger than the maximum capacity of edges out of source.
   
   getFlowSFF(scalingParameter): This method calls findPathDFS(source, sink, graph, scalingParameter) which finds the edges having capacity at least equal to scaling parameter starting from source until we reach the sink and store them in an ArrayList.Then bottleneck is found by choosing the minimum capacity edge by using the method getBottleneck(scalingParameter). And lastly in augmentingPath(bottleneckCapacity) method we increase the flow of edge in the path if it is forward edge and decrease the flow of the edge if it is a backward edge.

PreflowPush.java

1. Function: public double maxFlowPFP(SimpleGraph g)
   This is the main method which performs tasks by calling all the sub methods and returns max flow as output
2. Function: public void preprocessInputGraph(SimpleGraph sg)
   This method takes simple graph as input and works on preprocessing the graph. 
3. Function: public void preprocessFlow()
   This function is responsible for initializing the flow values. 
4. Function: public void findSourceSinkIndex(String vertexName)
   This method takes vertex as input and finds if the given input is a source or sink.
5. Function: public int findEdge(Vertex start, Vertex end)
   Input parameters to this function are start and end vertices, it returns index of the edge for the given input.
6. Function: public void setFlows()
   This function set the initial flow values to 0 for all the edges
7. Function: public void setExcess()
   This function set the initial excess values equal to capacity for all the edges
8. Function: public void pushAndrelabel()
   This function calls the push and relabel functionality by setting all the parameters for both the functions.
9. Function: public void Push(Double excessU, Double cEdgeUV, int indexEdgeUV, int indexEdgeVU, int excessUindex,int excessVindex)
   This function updates the flow and excess values after pushing the flow. When there is a forward edge, flow is added. when there is backward edge flow is reduced.
10. Function: public void Relabel(int relabelVertex)
  This function increses the height of the relabel vertex by 1
