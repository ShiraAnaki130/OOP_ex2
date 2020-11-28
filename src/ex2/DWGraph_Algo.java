package ex2;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



public class DWGraph_Algo implements dw_graph_algorithms{
	
	private directed_weighted_graph graph;
	
	public DWGraph_Algo() {
		graph=new DWGraph_DS();
	}
	/**
	 * This function init the graph on which this set of algorithms operates on.
	 */
	@Override
	public void init(directed_weighted_graph g) {
		graph=g;
	}
	/**
	 * This function return the underlying graph of which this class works.
	 * @return returns a graph from the type of directed_weighted_graph. 
	 */
	@Override
	public directed_weighted_graph getGraph() {
		return graph;
	}
	/**
	 * This function creates another graph which is a copy of this graph.
	 * @return returns the new identical graph.
	 */
	@Override
	public directed_weighted_graph copy() {
		directed_weighted_graph answer=new DWGraph_DS(graph);
		return answer;
	}
	/**
	 *This function check if the grath is connected with BFS algorithm for directed graphs
	 * return true if their direction to each vertex
	 */
	@Override
	public boolean isConnected() {
		if(graph.getV().size()<2)
			return true;
		for(node_data node : graph.getV()){
			node.setTag(0);
		}
		Iterator<node_data> next = graph.getV().iterator();
		node_data nxt = next.next();
		Queue<node_data> frontier = new LinkedList<>();
		HashSet<node_data> node_out = new HashSet<>();
		frontier.add(nxt);
		while(!frontier.isEmpty()){
			nxt = frontier.poll();
			for(edge_data ni: graph.getE(nxt.getKey())){
				node_data n = graph.getNode(ni.getDest());
				if(n.getTag() == 0) {
					n.setTag(1);
					frontier.add(n);
				}
			}
			nxt.setTag(2);
			node_out.add(nxt);
		}
		boolean answer = true;
		if(node_out.size()!=graph.getV().size())
			answer = false;
		return answer;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
     * This function saves this weighted directed graph to the given
     * file name - in JSON format.
     * @param file - the file name (may include a relative path).
     * @return return true, if the file was successfully saved, or false in case it wasn't.
     */
	@Override
	public boolean save(String file) {
		boolean ans=false;
		Gson gson=new GsonBuilder().setPrettyPrinting().create();
		String toSave=gson.toJson(graph);
		try {
			 PrintWriter writer=new PrintWriter(new File(file));
			 writer.write(toSave);
			 writer.close();
			 ans=true; 
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		return ans;
	}
	 /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name of JSON file.
     * @return return true, if the file was successfully saved, or false in case it wasn't.
     */
	@Override
	public boolean load(String file) {
		try {
			 directed_weighted_graph graph_New=new DWGraph_DS();
			 GsonBuilder builder = new GsonBuilder();
			 builder.registerTypeAdapter(DWGraph_DS.class, new DWGraph_DSJsonDeserializer());
			 Gson gson = builder.create();
			 FileReader reader=new FileReader(file);
			 graph_New=gson.fromJson(reader,DWGraph_DS.class);
			 graph=graph_New;
			 return true;
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
