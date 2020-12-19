package api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
/**
 * This class represents the algorithms which applicable on a  directional weighted graph.
 * the algorithms including- 
 * 1.a deep copy of the graph.
 * 2.init(graph).
 * 3.getGraph();
 * 4.isConnected();
 * 5. double shortestPathDist(int src, int dest);
 * 6. the shortestPath(int src, int dest);
 * 7. Save(file)- in JSON format;
 * 8. Load(file)- gets a JSON format file;
 * @author Lea.Shira;
 */
public class DWGraph_Algo implements dw_graph_algorithms{
	
	private directed_weighted_graph graph;
	private HashMap<Integer,AlgoNodeInfo> info;

	/**
	 * default constructor crate new directed weighted graph algorithms.
	 */
	public DWGraph_Algo() {
		this.graph=new DWGraph_DS();
	}
	/**
	 * This function init the graph on which this set of algorithms operates on.
	 *  @param g- a directional weighted graph.
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
	 * @return returns the new identical directional weighted graph.
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
	 * @return returns true this graph is a strongly connected component, otherwise returns false.
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
	 * check the shortest weight directed distance from source node to the destination node with Dijkstra's algorithm
	 * @param src - start node
	 * @param dest - end (target) node
	 * @return returns the smallest distance between src(node_id) and dest(node_id).
	 */
	@Override
	public double shortestPathDist(int src, int dest) {
		info = new HashMap<>();
		if(graph.getNode(src) == null || graph.getNode(dest)==null)
			return -1;
		if(src == dest)return -1;
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
	 * returns the shortest path with the lowest distance between src and dest , uses Dijkstra's algorithm
	 * return list of nodes data
	 * @param src - start node
	 * @param dest - end (target) node
	 * @return returns the shortest path with the lowest distance between src and dest 
	 */
	@Override
	public List<node_data> shortestPath(int src, int dest) {
		List<node_data> path = new LinkedList<>();
		info = new HashMap<>();
		if(graph.getNode(src) == null || graph.getNode(dest)==null)
			return null;
		if(src== dest)
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

			Stack<node_data> temp = new Stack<>();
			for (node_data node : path) {
				temp.push(node);
			}
			path.clear();
			while (!temp.isEmpty()) {
				path.add(temp.pop());
			}
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
		JSONObject allArray=new JSONObject();
	    JSONArray Edges=new JSONArray();
	    JSONObject edge;
	    JSONArray Nodes =new JSONArray();
	    JSONObject node;

	    for(node_data node_:getGraph().getV()) {
	    	node=new JSONObject();
	    	String pos=""+node_.getLocation().x()+","+node_.getLocation().y()+","+node_.getLocation().z();
	    	try {
				node.put("pos", pos);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
	    	try {
				node.put("id", node_.getKey());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
	    	Nodes.put(node);
	    	for(edge_data edge_:getGraph().getE(node_.getKey())) {
	    		edge=new JSONObject();
	    		try {
					edge.put("src", edge_.getSrc());
				} catch (JSONException e) {
					e.printStackTrace();
				}
	    		try {
					edge.put("w", edge_.getWeight());
				} catch (JSONException e) {
					e.printStackTrace();
				}
	    		try {
					edge.put("dest", edge_.getDest());
				} catch (JSONException e) {
					e.printStackTrace();
				}
	    		Edges.put(edge);	
	    	}
	    }
	    try {
			allArray.put("Edges",Edges);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
	    try {
			allArray.put("Nodes",Nodes);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
	try {
	    FileWriter file_ = new FileWriter(file);
	    file_.write(allArray.toString());
	    file_.flush();
	    file_.close();
	    ans=true;
	    }
	    catch (IOException e) {
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
			directed_weighted_graph new_graph=new DWGraph_DS();
			Scanner scanner = new Scanner(new File(file));
			String jsonString = scanner.useDelimiter("\\A").next();
			scanner.close();
			JSONObject node = new JSONObject();
			JSONObject edge = new JSONObject();
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArrayEdges= jsonObject.getJSONArray("Edges");
			JSONArray jsonArrayNodes= jsonObject.getJSONArray("Nodes");
			for(int i=0;i<jsonArrayNodes.length();i++) {
				node=jsonArrayNodes.getJSONObject(i);
				int id=node.getInt("id");
				String pos_string=node.getString("pos");
				String[] arr=new String[3];
				arr=pos_string.split(",");
				double[] arrForPos=new double[3];
				for(int k=0;k<3;k++) {
					arrForPos[k]=Double.parseDouble(arr[k]);
				}
				node_data toAdd=new DWGraph_DS.NodeData(id,arrForPos[0],arrForPos[1],arrForPos[2]);
				new_graph.addNode(toAdd);
			}
			for (int j=0; j<jsonArrayEdges.length();j++) {
		    		edge =jsonArrayEdges.getJSONObject(j);
		    		int src=edge.getInt("src");
		    		int dest=edge.getInt("dest");
		    		double w=edge.getDouble("w");
		    		new_graph.connect(src, dest, w);
			}
			this.graph=new_graph;
			return true;
			}
		   catch (FileNotFoundException e) {
			   		e.printStackTrace();
			   		return false;
		    } catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * this function gets a directed weighted graph and a starting node and check
	 * by the BFS algorithm if the graph his connected
	 * @param graph
	 * @param nxt
	 * @return
	 */
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

	/**
	 *create new graph with a swap direction of the edges of this graph
	 * @return
	 */
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

	/**
	 * this class represent the info that the algorithms collectd for each function in DWGraph_Algo
	 */
	private class AlgoNodeInfo implements Comparable<Object>{
		private int _key;
		private double weight;
		private node_data parent;

		/**
		 * default constructor create new algorithm node's info by given node.
		 * @param n
		 */
		public AlgoNodeInfo(node_data n){
			this._key = n.getKey();
			this.weight = Double.MAX_EXPONENT;
			this.parent = null;
		}

		/**
		 * return the key of the node associated to the algorithm info.
		 * @return
		 */
		public int getKey(){
			return this._key;
		}

		/**
		 * return the weight distance of the node used by algorithms
		 * @return
		 */
		public double getWeight() {
			return this.weight;
		}
		/**
		 * set the weight distance of the node used by algorithms
		 * @return
		 */
		public void setWeight(double weight) {
			this.weight = weight;
		}

		/**
		 * set the parent node that the algorithm of shortest path need
		 * to remember for creating a path list of the nodes.
		 * @param parent
		 */
		public void setParent(node_data parent){
			this.parent = parent;
		}

		/**
		 * return the parent of the node associated.
		 * @return
		 */
		public node_data getParent(){
			return this.parent;
		}
		/**
		 * comparing the algorithm info by the weight.
		 * return 1 if the weight of this algorithm info is bigger then the other
		 * return -1 if the weight of the other algorithm info is bigger then this weight
		 * return 0 if they are equals
		 */
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
