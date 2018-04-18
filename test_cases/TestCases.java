import java.util.Hashtable;

import org.junit.Assert;
import org.junit.Test;

public class TestCases {

	// Creating the required objects 
	SimpleGraph InputGraph = new SimpleGraph();
	PreFlowPush preFlowPush = new PreFlowPush();
	FordFulkerson fordFulkerson = new FordFulkerson();
		
	// Pre Flow Push
	//Graph with no augmented path should return zero flow
	@Test
	public void testGraphWithNoAugmentedPathPFP() {
		String filename = "C:/Users/kondu/workspaceNeon/AlgoProject/src/GraphWithNoAugmentedPath.txt";
		GraphInput.LoadSimpleGraph(InputGraph, filename);
	    int maxflow = (int)preFlowPush.maxFlowPFP(InputGraph);
		Assert.assertEquals(maxflow, 0);
	}
	
	//Graph with all edges with equal capacity
	@Test
	public void testGraphWithAllEdgesEqualPFP() {
		String filename = "C:/Users/kondu/workspaceNeon/AlgoProject/src/GraphWithAllEdgesEqual.txt";
		GraphInput.LoadSimpleGraph(InputGraph, filename);
	    int maxflow = (int)preFlowPush.maxFlowPFP(InputGraph);
		Assert.assertEquals(maxflow, 8);
	}
	
	//Normal graph
	@Test
	public void testNormalGraphPFP() {
		String filename = "C:/Users/kondu/workspaceNeon/AlgoProject/src/NormalGraph.txt";
		GraphInput.LoadSimpleGraph(InputGraph, filename);
	    int maxflow = (int)preFlowPush.maxFlowPFP(InputGraph);
		Assert.assertEquals(maxflow, 99);
	}
	
	//Graph with source Node not connected
	@Test
	public void testGraphWithSourceNotConnectedPFP() {
		String filename = "C:/Users/kondu/workspaceNeon/AlgoProject/src/GraphWithSourceNotConnected.txt";
		GraphInput.LoadSimpleGraph(InputGraph, filename);
		int maxflow = (int)preFlowPush.maxFlowPFP(InputGraph);
		Assert.assertEquals(maxflow, 0);
	}
	
	// Scaling Ford Fulkerson
	//Graph with no augmented path should return zero flow
	@Test
	public void testGraphWithNoAugmentedPathSFF() {
	    String filename = "C:/Users/kondu/workspaceNeon/AlgoProject/src/GraphWithNoAugmentedPath.txt";
		ScalingFordFulkerson scalingFordFulkerson = new ScalingFordFulkerson(filename);

	    GraphInput.LoadSimpleGraph(InputGraph, filename);
		int maxflow=0;
		try {
			maxflow = (int)scalingFordFulkerson.scalingMaxFlow();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(maxflow, 0);
	}
	
	//Graph with all edges with equal capacity
	@Test
	public void testGraphWithAllEdgesEqualSFF() {
		String filename = "C:/Users/kondu/workspaceNeon/AlgoProject/src/GraphWithAllEdgesEqual.txt";
		ScalingFordFulkerson scalingFordFulkerson = new ScalingFordFulkerson(filename);
		GraphInput.LoadSimpleGraph(InputGraph, filename);
		int maxflow=0;
		try {
			maxflow = (int)scalingFordFulkerson.scalingMaxFlow();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		Assert.assertEquals(maxflow, 8);
		        
	}
	
	//Normal graph
	@Test
	public void testNormalGraphSFF() {
		String filename = "C:/Users/kondu/workspaceNeon/AlgoProject/src/NormalGraph.txt";
		ScalingFordFulkerson scalingFordFulkerson = new ScalingFordFulkerson(filename);
		GraphInput.LoadSimpleGraph(InputGraph, filename);
		int maxflow=0;
		try {
			maxflow = (int)scalingFordFulkerson.scalingMaxFlow();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		Assert.assertEquals(maxflow, 99);
	}
	
	//Graph with source Node not connected
	@Test
	public void testGraphWithSourceNotConnectedSFF() {
		String filename = "C:/Users/kondu/workspaceNeon/AlgoProject/src/GraphWithSourceNotConnected.txt";
		ScalingFordFulkerson scalingFordFulkerson = new ScalingFordFulkerson(filename);
		GraphInput.LoadSimpleGraph(InputGraph, filename);
		int maxflow=0;
		try {
			maxflow = (int)scalingFordFulkerson.scalingMaxFlow();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		Assert.assertEquals(maxflow, 0);
		
	}

	
	// Ford fulkerson
	//Graph with no augmented path should return zero flow
	@Test
	public void testGraphWithNoAugmentedPathFF() {
		String filename = "C:/Users/kondu/workspaceNeon/AlgoProject/src/GraphWithNoAugmentedPath.txt";
		GraphInput.LoadSimpleGraph(InputGraph, filename);
	    int maxflow = (int)fordFulkerson.maxFlowFF(InputGraph);
	    Assert.assertEquals(maxflow, 0);
	}
		
	//Graph with all edges with equal capacity
	@Test
	public void testGraphWithAllEdgesEqualFF() {
		String filename = "C:/Users/kondu/workspaceNeon/AlgoProject/src/GraphWithAllEdgesEqual.txt";
		GraphInput.LoadSimpleGraph(InputGraph, filename);
	    int maxflow = (int)fordFulkerson.maxFlowFF(InputGraph);
		Assert.assertEquals(maxflow, 8);
	}
		
	//Normal graph
	@Test
	public void testNormalGraphFF() {
		String filename = "C:/Users/kondu/workspaceNeon/AlgoProject/src/NormalGraph.txt";
		GraphInput.LoadSimpleGraph(InputGraph, filename);
	    int maxflow = (int)fordFulkerson.maxFlowFF(InputGraph);
		Assert.assertEquals(maxflow, 99);
	}
		
	//Graph with source Node not connected
	@Test
	public void testGraphWithSourceNotConnectedFF() {
		String filename = "C:/Users/kondu/workspaceNeon/AlgoProject/src/GraphWithSourceNotConnected.txt";
		GraphInput.LoadSimpleGraph(InputGraph, filename);
	    int maxflow = (int)fordFulkerson.maxFlowFF(InputGraph);
		Assert.assertEquals(maxflow, 0);
	}
	
}
