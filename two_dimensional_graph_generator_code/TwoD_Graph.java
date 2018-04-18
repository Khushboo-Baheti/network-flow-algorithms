// Import required libraries
import java.util.Collections;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;

import java.awt.Dimension;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.ui.ApplicationFrame;
import java.util.Map;
import org.jfree.chart.JFreeChart;

public class TwoD_Graph extends ApplicationFrame {

// Set Panel width and height
public static int WIDTH = 	800;
public static int HEIGHT = 600;
public static String MAIN_TITLE = "Random Graph Analysis";
public String XAXIS_LABEL = "Density";
public String YAXIS_LABEL = "Run Time";
public int vertices; 
public static int INITIALIZE_EDGES = 0;
public static int INITIALIZE_VERTICES = 0;
public int edges;
DefaultCategoryDataset runTimeValues = new DefaultCategoryDataset( );
public static boolean SET = true;
public static boolean NO_SET = false;
public static LinkedHashMap<String, Integer> ford_fulkerson = new LinkedHashMap<String, Integer>();
public static LinkedHashMap<String, Integer> scaling_ford_fulkerson = new LinkedHashMap<String, Integer>();
public static LinkedHashMap<String, Integer> pre_flow_push = new LinkedHashMap<String, Integer>();
public static String ff_legend = "Ford FulkerSon";
public static String sff_legend = "Scaling Ford FulkerSon";
public static String pfp_legend = "Pre Flow Push";



   public static void SetChartProperties(TwoD_Graph chart) {
	      chart.pack();
	      chart.HEIGHT = HEIGHT;
	      RefineryUtilities.centerFrameOnScreen(chart);
	      chart.WIDTH = WIDTH;
	      chart.setVisible(true);
   }

   // Constructor
   public TwoD_Graph() {
      super(MAIN_TITLE);
      vertices = INITIALIZE_VERTICES;
      edges = INITIALIZE_EDGES;
      JFreeChart myGraph = ChartFactory.createLineChart(
         MAIN_TITLE,
         XAXIS_LABEL,
         YAXIS_LABEL,
         storeValues(),
         PlotOrientation.VERTICAL,
         SET,
         NO_SET,
         SET);
      Dimension size = new java.awt.Dimension( WIDTH , HEIGHT);
      ChartPanel randomGraphPanel = new ChartPanel( myGraph );
      randomGraphPanel.setPreferredSize(size);
      edges = INITIALIZE_EDGES;
      vertices = INITIALIZE_VERTICES;
      setContentPane(randomGraphPanel);
   }

   private DefaultCategoryDataset storeValues( ) {
	   for (String key : ford_fulkerson.keySet()){
		   runTimeValues.addValue(ford_fulkerson.get(key),ff_legend, key );
	   }
	   
	   for (String key : scaling_ford_fulkerson.keySet()){
		   runTimeValues.addValue(scaling_ford_fulkerson.get(key),sff_legend, key );
	   }
	   
	   for (String key : pre_flow_push.keySet()){
		   runTimeValues.addValue(pre_flow_push.get(key),pfp_legend, key );
	   }

      return runTimeValues;
   }
   
   public static void main( String[ ] args ) {
	   // Hash map takes x-axis value and y-axis(run time) values as input
	   // More data point can be added if needed
	   //ford_fulkerson.put("6",10);
	   //ford_fulkerson.put("8", 200);
	   //ford_fulkerson.put("10", 50);
	   //ford_fulkerson.put("7",90);
	   //ford_fulkerson.put("3", 120);
	   //ford_fulkerson.put("1", 150);
	   
	     ford_fulkerson.put("20",1);
		 ford_fulkerson.put("40",2);
		 ford_fulkerson.put("60",4);
		 ford_fulkerson.put("80",5);
		 ford_fulkerson.put("100",6);
		 //ford_fulkerson.put("30",14);
		 scaling_ford_fulkerson.put("20", 2);
		 scaling_ford_fulkerson.put("40", 5);
		 scaling_ford_fulkerson.put("60", 7);
		 scaling_ford_fulkerson.put("80", 10);
		 scaling_ford_fulkerson.put("100", 12);
		 //scaling_ford_fulkerson.put("30", 21);
		 pre_flow_push.put("20", 7);
		 pre_flow_push.put("40", 11);
		 pre_flow_push.put("60", 9);
		 pre_flow_push.put("80", 9);
		 pre_flow_push.put("100", 12);
		//pre_flow_push.put("30", 191);

      TwoD_Graph chart = new TwoD_Graph();
      TwoD_Graph chartNew = new TwoD_Graph();
      SetChartProperties(chart);
      
   }
}