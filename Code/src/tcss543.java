

import java.util.*;
import java.util.concurrent.TimeUnit;

public class tcss543 {

	public static void main(String[] args) {
		
		if(args.length != 0){
			// Loading graph from input file for the Ford Fulkerson Algorithm
			SimpleGraph graphFordFulkerson = new SimpleGraph();
			GraphInput.LoadSimpleGraph(graphFordFulkerson, args[0]);
			
			//Getting max flow and time taken by Ford Fulkerson Algorithm
			FordFulkerson fordFulkerson = new FordFulkerson();
			long startTimeFF = System.nanoTime();
	        double maxflowFF = fordFulkerson.maxFlowFF(graphFordFulkerson);
	        long endTimeFF = System.nanoTime();
	        System.out.println("Max flow Ford-Fulkerson: " + maxflowFF);
	        System.out.println("Time taken by Ford Fulkerson: " + (endTimeFF - startTimeFF) / 1000000 + " ms");
	        
			// Loading graph from input file for Scaling Ford Fulkerson Algorithm
			SimpleGraph graphScalingFordFulkerson = new SimpleGraph();
			String fileName = args[0];
			
			//Getting max flow and time taken by Scaling Ford Fulkerson Algorithm
			ScalingFordFulkerson scalingFordFulkerson = new ScalingFordFulkerson(fileName);
			long startTimeSFF = System.nanoTime();
			double maxflowSFF = 0;
			try {
				maxflowSFF = scalingFordFulkerson.scalingMaxFlow();
			}
			catch(Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
			long endTimeSFF = System.nanoTime();
			System.out.println("Max flow Scaling Ford-Fulkerson: " + maxflowSFF);
			System.out.println("Time taken by Scaling Ford Fulkerson: " + (endTimeSFF - startTimeSFF) / 1000000 + " ms");
				
			// Loading graph from input file for the Preflow Push Algorithm
			SimpleGraph graphPreflowPush = new SimpleGraph();
			GraphInput.LoadSimpleGraph(graphPreflowPush, args[0]);
			
			//Getting max flow and time taken by Preflow Push Algorithm
			PreFlowPush preFlowPush = new PreFlowPush();
			long startTimePFP = System.nanoTime();
			double maxflowPFP = preFlowPush.maxFlowPFP(graphPreflowPush);
			long endTimePFP = System.nanoTime();
			System.out.println("Max flow PreFlow Push: " + maxflowPFP);
			System.out.println("Time taken by Preflow Push : " + (endTimePFP - startTimePFP ) / 1000000 + " ms");
		}
		else
		{
			System.out.println("Please provide graph input file.");
		}
	}

}
