package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.*;


public class DWGraph_Algo implements dw_graph_algorithms{
	
	private directed_weighted_graph graph;
	private HashMap<Integer,AlgoNodeInfo> info;
	
	public DWGraph_Algo() {
		graph=new DWGraph_DS();
	}
	/**
	 * This function init the graph on which this set of algorithms operates on.
	 */
	@Override
	public void init(directed_weighted_graph g) {
		this.graph=g;
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
	 * if the algorithm finished with answer ture the algorithm swap the directions of the edges and
	 * do the BFS algorithm again to ensure the connection
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
		boolean connected = BFS(graph,nxt);
		if(connected){
			for(node_data node : graph.getV()){
				node.setTag(0);
			}
			directed_weighted_graph copy_graph = swapDirections();
			connected = BFS(copy_graph,nxt);
		}
		return connected;
	}

	/**
	 * check the shortest weight directed distance from source node to the destination node with dijkstra's algorithm
	 * @param src - start node
	 * @param dest - end (target) node
	 * @return
	 */
	@Override
	public double shortestPathDist(int src, int dest) {
		info = new HashMap<>();
		if(graph.getNode(src) == null || graph.getNode(dest)==null)
			return -1;
		for(node_data node : graph.getV()){
			info.put(node.getKey(),new AlgoNodeInfo(node));
		}
		PriorityQueue<AlgoNodeInfo> frontier = new PriorityQueue<>();
		AlgoNodeInfo nxt = info.get(src);
		nxt.setWeight(0);
		frontier.add(nxt);
		HashSet<AlgoNodeInfo> out_nodes = new HashSet<>();
		while (!frontier.isEmpty()) {
			nxt = frontier.poll();
			if (nxt.getKey() != dest) {
				for (edge_data edge : graph.getE(nxt.getKey())) {
					if (!out_nodes.contains(edge.getDest())) {
						double ew = edge.getWeight();
						double nw = nxt.getWeight();
						DecimalFormat df = new DecimalFormat("###0.00000000000000");
						String temp = df.format(ew+nw);
						double t = Double.parseDouble(temp);
						if (t < info.get(edge.getDest()).getWeight()) {
							info.get(edge.getDest()).setWeight(t);
							frontier.add(info.get(edge.getDest()));
						}
					}
				}
			}
			else {
				out_nodes.add(nxt);
				break;
			}
			out_nodes.add(nxt);
		}
		if (!out_nodes.contains(info.get(dest)))
			return -1;
		return info.get(dest).getWeight();
	}
	/**
	 * check the shortest weight directed path from source node to the destination node with dijkstra's algorithm
	 * return list of nodes data
	 * @param src - start node
	 * @param dest - end (target) node
	 * @return
	 */
	@Override
	public List<node_data> shortestPath(int src, int dest) {
		List<node_data> path = new LinkedList<>();
		info = new HashMap<>();
		if(graph.getNode(src) == null || graph.getNode(dest)==null)
			return null;
		for(node_data node : graph.getV()){
			info.put(node.getKey(),new AlgoNodeInfo(node));
		}
		PriorityQueue<AlgoNodeInfo> frontier = new PriorityQueue<>();
		AlgoNodeInfo nxt = info.get(src);
		nxt.setWeight(0);
		frontier.add(nxt);
		HashSet<node_data> out_nodes = new HashSet<>();
		while (!frontier.isEmpty()) {
			nxt = frontier.poll();
			if (nxt.getKey() != dest) {
				for (edge_data edge : graph.getE(nxt.getKey())) {
					if (!out_nodes.contains(edge.getDest())) {
						double t = nxt.getWeight() + edge.getWeight();
						if (t < info.get(edge.getDest()).getWeight()) {
							info.get(edge.getDest()).setWeight(t);
							frontier.add(info.get(edge.getDest()));
							info.get(edge.getDest()).setParent(graph.getNode(nxt.getKey()));
						}
					}
				}
			}
			else {
				out_nodes.add(graph.getNode(nxt.getKey()));
				break;
			}
			out_nodes.add(graph.getNode(nxt.getKey()));
		}
		node_data start = graph.getNode(dest);
		node_data stop = graph.getNode(src);
		path.add(start);
		if(out_nodes.contains(start)) {
			while (start != null && start != stop) {
				node_data p = info.get(start.getKey()).getParent();
				path.add(p);
				start = p;
			}
		}
		Stack<node_data> temp = new Stack<>();
		for(node_data node: path){
			temp.push(node);
		}
		path.clear();
		while(!temp.isEmpty()){
			path.add(temp.pop());
		}
		return path;
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

			 GsonBuilder builder = new GsonBuilder();
			 builder.registerTypeAdapter(DWGraph_DS.class, new DWGraph_DSJsonDeserializer());
			 Gson gson = builder.create();
			 FileReader reader=new FileReader(file);
			 graph=gson.fromJson(reader,DWGraph_DS.class);
			 return true;
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	//BFS algorithm using given graph and starting node (nxt)
	private boolean BFS(directed_weighted_graph graph,node_data nxt) {
		Queue<node_data> frontier = new LinkedList<>();
		HashSet<node_data> node_out = new HashSet<>();
		nxt.setTag(1);
		frontier.add(nxt);
		while (!frontier.isEmpty()) {
			nxt = frontier.poll();
			for (edge_data ni : graph.getE(nxt.getKey())) {
				node_data n = graph.getNode(ni.getDest());
				if (n.getTag() == 0) {
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
	//The function is swapping the edges in new graph with the same vertex
	private directed_weighted_graph swapDirections(){
		directed_weighted_graph new_graph = new DWGraph_DS();
		for(node_data n : graph.getV()){
			new_graph.addNode(n);
			for(edge_data edge: graph.getE(n.getKey())){
				if(new_graph.getNode(edge.getDest())==null)
						new_graph.addNode(graph.getNode(edge.getDest()));
				new_graph.connect(edge.getDest(),edge.getSrc(),edge.getWeight());
			}
		}
		return new_graph;
	}
	private class AlgoNodeInfo implements Comparable<Object>{
		private int _key;
		private double weight;
		private node_data parent;

		public AlgoNodeInfo(node_data n){
			this._key = n.getKey();
			this.weight = Double.MAX_EXPONENT;
			this.parent = null;
		}
		public int getKey(){
			return this._key;
		}
		public double getWeight() {
			return this.weight;
		}
		public void setWeight(double weight) {
			this.weight = weight;
		}
		public void setParent(node_data parent){
			this.parent = parent;
		}
		public node_data getParent(){
			return this.parent;
		}
		@Override
		public int compareTo(Object o) {
			int ans = 0;
			if(o instanceof AlgoNodeInfo) {
				AlgoNodeInfo n =(AlgoNodeInfo) o;
				if(this.getWeight() - n.getWeight()>0)ans = 1;
				if(this.getWeight() - n.getWeight()<0)ans = -1;
			}
			return ans;
		}
	}
}
