package ex2;
import java.util.Collection;
import java.util.HashMap;
public class DWGraph_DS implements directed_weighted_graph{
	/**
	 * This class represents a node (vertex) in an directional weighted graph.
	 * Every node consist of a unique key,weight, location, and tag and info for marking.
	 * @author Lea&shira.
	 */
	public static class NodeData implements node_data{
		private int key;
		private double weight;
		private String info;
		private int tag;
		private static int key1=1;
		private geo_location location;
		public NodeData() {
			this.key=key1++;
			this.info="f";
			this.tag=Integer.MAX_VALUE;
			this.weight = 0;
			this.location=new Geo_Location();
		}
		
		public NodeData(int key) {
			this.key=key;
			this.info="f";
			this.tag=Integer.MAX_VALUE;
			this.weight = 0;
			this.location=new Geo_Location();
		}
		public NodeData(int key, String info,int tag, double weight,double x, double y, double z) {
			this.key=key;
			this.info=info;
			this.tag=tag;
			this.weight = weight;
			this.location=new Geo_Location(x,y,z);
		}
		@Override
		public int getKey() {
			return key;
		}

		@Override
		public geo_location getLocation() {
			return location;
		}

		@Override
		public void setLocation(geo_location p) {
			location=new Geo_Location(p);
		}

		@Override
		public double getWeight() {
			return this.weight;
		}

		@Override
		public void setWeight(double w) {
			this.weight=w;
			
		}

		@Override
		public String getInfo() {
			return this.info;
		}

		@Override
		public void setInfo(String s) {
			this.info=""+s;
			
		}

		@Override
		public int getTag() {
			return this.tag;
		}

		@Override
		public void setTag(int t) {
			tag=t;
			
		}
	

		public class Geo_Location implements geo_location{
			private double _x;
			private double _y;
			private double _z;
			
			public Geo_Location() {
				this._x=0.0;
				this._y=0.0;
				this._z=0.0;
			}
			public Geo_Location(geo_location p) {
				this._x=p.x();
				this._y=p.y();
				this._z=p.z();
			}
			public Geo_Location(double x,double y,double z) {
				this._x=x;
				this._y=y;
				this._z=z;
			}



			@Override
			public double x() {
				return this._x;
			}

			@Override
			public double y() {
				return this._y;
			}

			@Override
			public double z() {
				return this._z;
			}

			@Override
			public double distance(geo_location g) {
				double dis_x=Math.pow(this._x-g.x(), 2);
				double dis_y=Math.pow(this._y-g.y(), 2);
				double dis_z=Math.pow(this._z-g.z(), 2);
				return Math.sqrt(dis_x+dis_y+dis_z);
			}
			
		}
	}
/*private class EdgeLocation implements edge_location{
	private edge_data edge;
	private double ratio;
	
	public EdgeLocation(edge_data edge) {
		this.edge = edge;
		this
	}

	@Override
	public edge_data getEdge() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getRatio() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}*/
/**
 * This public class represents an edge in an directional weighted graph.
 * Every edge consist of src(edge's start node_id), dest(edge's end node_id), edge's weight,
 * tag and info(for marking).
 * @author Lea&Shira.
 *
 */

public static class EdgeData implements edge_data{
	private int src;
	private int dest;
	private double weight;
	private String info;
	private int tag;
	public EdgeData(int src,int dest,double weight,String info,int tag) {
		this.src=src;
		this.dest=dest;
		this.weight=weight;
		this.info=info;
		this.tag=tag;
	}
	
	public EdgeData(int src,int dest,double weight) {
		this.src=src;
		this.dest=dest;
		this.weight=weight;
		this.info="f";
		this.tag=Integer.MAX_VALUE;
	}
	/**
	 * The function returns the id of the source node of this edge.
	 * @return
	 */
	@Override
	public int getSrc() {
		return this.src;
	}
	/**
	 * This function returns the id of the destination node of this edge.
	 * @return 
	 */
	@Override
	public int getDest() {
		return this.dest;
	}
	/**
	 *This function returns the weight of this edge (only positive value).
	 *  @return
	 */
	@Override
	public double getWeight() {
		return this.weight;
	}
	/**
	 * Returns the remark (meta data) associated with this edge.
	 * @return
	 */
	@Override
	public String getInfo() {
		return this.info;
	}
	/**
	 * Allows changing the remark (meta data) associated with this edge.
	 * @param s
	 */
	@Override
	public void setInfo(String s) {
		this.info = s;
		
	}
	/**
	 * This function returns the Temporal data of this edge.
	 */
	@Override
	public int getTag() {
		return this.tag;
	}
	/**
	 * This function allows to change the value of the tag.
	 * @param t- this is the new value of the tag. 
	 */
	@Override
	public void setTag(int t) {
		this.tag = t;
	}
}
/**
 * This private class represents all the edges getting out of a given node.
 * uses a data structure from the type of HahMap.
 */
public static class edges_direction{
	HashMap<Integer,edge_data> edgesNi;
	public edges_direction() {
		this.edgesNi=new HashMap<Integer,edge_data>();
	}
	public edges_direction(HashMap<Integer,edge_data> edges) {
		this.edgesNi=edges;
	}

	/**
	 * This function returns the collection with all the edges which getting out of this given node.  
	 * @return HashMap-a data structure which representing the edges collection of this given node.
	 */
	public HashMap<Integer,edge_data> getNi() {
		return this.edgesNi;
	}
	/**
	 * This function checks if there is an edge from this to other
	 * @param other- this is a node_id which this node may be connected to. 
	 * @return returns true in case there is an edge from this to other,or false in case there isn't.
	 */
	public boolean hasNi(int other) {
		if(edgesNi.containsKey(other)) return true;
		return false;
	}
	/**
	 * This function adds node other to be another neighbor of this current node_info. 
	 * @param other- this is the node which need to be add as a new neighbor.
	 */
	public void addNi(int other, edge_data edge) {
		edgesNi.put(other, edge);
	}
	/**
	 * This function removes the edge this -->other.
	 * @param other- this is the node which the edge ends at.
	 */
	public void removeNode(int other) {
		if(edgesNi.containsKey(other)) 
			edgesNi.remove(other);
		
	}
	/**
	 * This function returns the weight of the edge from this to other: 
	 * this-->other,in case the edge is existent.
	 * @param other- this is the other node_id,which may the edge ends at.
	 * @return the weight of the edge from this-->(to)other,or -1 in case the edge isn't existent. 
	 */
	public double getWeight(int other) {
		if(hasNi(other)) {
			return edgesNi.get(other).getWeight();
		}
		return -1;
	}
	/**
	 * This function returns the edge_data of the edge from this to the node of this given id.
	 * @param key- this is the id of the node which the edge from this node ends at.
	 * @return the edge_data of this edge, or null if the edge isn't existent.
	 */
	public edge_data getEdge_(int key){
		return edgesNi.get(key);
	}
}
//DWGraph_DS:

private HashMap<Integer,node_data> map;
private HashMap<Integer,edges_direction> nodes_edges;
private int MC;
private int e_size;

public DWGraph_DS(){
	this.map = new HashMap<>();
	this.nodes_edges = new HashMap<>();
	this.MC = 0;
	this.e_size= 0;
}
public DWGraph_DS(HashMap<Integer,node_data>map,HashMap<Integer,edges_direction>nodes_edges, int MC,int e_size){
	this.map = map;
	this.nodes_edges =nodes_edges;
	this.MC = MC;
	this.e_size= e_size;
}
/**
 * This function is a copy constructor of DWGraph_DS.
 * The function is getting a graph from the type of directed_weighted_graph and 
 * builds a new one by copying all of the values of the excepted graph to the new graph.  
 * Note: needed for DWGraph_Algo.
 * @param graph- this is the graph which the function makes an identical copy of.
 */
public DWGraph_DS(directed_weighted_graph other) {
	this.map = new HashMap<Integer,node_data>();
	this.nodes_edges = new HashMap<Integer,edges_direction>();
	node_data nodeToAddSrc;
	node_data nodeToAddDest;
	edges_direction ni;
	int src;int dest;double w;
	for(node_data node:other.getV()){
		src=node.getKey();
		if(!this.map.containsKey(src)) {
			nodeToAddSrc=new NodeData(src);
			this.map.put(src, nodeToAddSrc);
			ni=new edges_direction();
			this.nodes_edges.put(src, ni);
		}
		for(edge_data edge:other.getE(src)) {
			dest=edge.getDest();
			w=edge.getWeight();
			if(!this.map.containsKey(dest)) {
				nodeToAddDest=new NodeData(dest);
				this.map.put(dest, nodeToAddDest);
				ni=new edges_direction();
				this.nodes_edges.put(dest, ni);
			}
			connect(src,dest,w);
			nodes_edges.get(src).getEdge_(dest).setInfo(edge.getInfo());
			nodes_edges.get(src).getEdge_(dest).setTag(edge.getTag());
	}
	}
	this.MC=other.getMC();
	this.e_size=other.edgeSize();
	
}
/**
 * This function returns the node_data by the node_id.
 * @param key - this is the node_id.
 * @return the node_data by the node_id, null if none.
 */
@Override
public node_data getNode(int key) {
	return map.get(key);
}
/**
 * This function returns,in O(1),the data of the edge (src-->dest), null if none.
 * @param src- this is the node_id which the edge start at.
 * @param dest- this is the nide_id which the edge ends at.
 * @return the data of the edge getting out of src to dest, or null if this edge isn't existent.
 */
@Override
public edge_data getEdge(int src, int dest) {
	if(src!=dest && map.containsKey(src)&&map.containsKey(dest)){
		if(nodes_edges.get(src).hasNi(dest)){
			return nodes_edges.get(src).getEdge_(dest);
		}
		return null;
	}
	return null;
}
/**
 * This function adds,in O(1),a new node to the graph with the given node_data- 
 * in case there is already a node with such a key.
 * @param key- this is the id of the new vertex to add.
 */
@Override
public void addNode(node_data n) {
	if(!map.containsKey(n.getKey())){
		map.put(n.getKey(),n);
		edges_direction e = new edges_direction();
		nodes_edges.put(n.getKey(),e);
		MC++;
	}
	
}
/**
 *This function connects,in O(1),an edge between src to dest with weight w. 
 * @param src - the source of the edge.
 * @param dest - the destination of the edge.
 * @param w - positive weight representing the cost.
 */
@Override
public void connect(int src, int dest, double w) {
	if(this.map.containsKey(src)&&this.map.containsKey(dest)&&(src!=dest)){
		if(!nodes_edges.get(src).hasNi(dest)) {
	       edge_data toAdd=new EdgeData(src,dest,w);
	       nodes_edges.get(src).addNi(dest, toAdd);
	       MC++;
	       e_size++;
		}	
	}
	return;
}
/**
 * This function creates in O(1), another pointer for the collection which
 * representing all the nodes in the graph.
 * @return return Collection<node_data>- the another pointer. 
 */
@Override
public Collection<node_data> getV() {
	return map.values();
}
/**
 *This method returns a collection containing all the 
 *edges which getting out of the given node_id.
 * @return Collection<node_data>.
 */
@Override
public Collection<edge_data> getE(int node_id) {
	if(map.containsKey(node_id)) {
		return nodes_edges.get(node_id).getNi().values();
	}
	return null;
}
/**
 * This function removes the node from the graph by it's node_id.
 * In addition, the function removes all the edges which starts or ends at this node.
 * the function run in 0(|v|) times. v -number of the nodes in the graph.
 * @param key- this is the node_id which need to be removed.
 * @return returns the node which removed, or null if the node does'nt exist in the graph.
 */
@Override
public node_data removeNode(int key) {
	node_data toRemove=getNode(key);
	if(toRemove==null) return toRemove;
	for(node_data node:getV()) {
		 if(nodes_edges.get(node.getKey()).hasNi(key)) {
			  nodes_edges.get(node.getKey()).removeNode(key);
			  e_size--;
		   }
	   }
	e_size-=getE(key).size();
	nodes_edges.remove(key);
	MC++;
	return map.remove(key);
}
/**
 * This function deletes,in O(1), the edge src-->dest from the graph,
* @param src - the source of the edge.
 * @param dest - the destination of the edge.
 * @return the data of the removed edge or null if the edge does'nt exist in the graph.
 */
@Override
public edge_data removeEdge(int src, int dest) {
	edge_data toRemove=getEdge(src,dest);
	if(toRemove!=null) {
		nodes_edges.get(src).removeNode(dest);
		MC++;
		e_size--;
	}
	return toRemove;
}
/**
 * This function finds in O(1), the numbers of the nodes in the graph.
 */
@Override
public int nodeSize() {
	return this.map.size();
}
/**
 * This function returns in O(1), the number of the edges in the graph.
 * @return return the number of the edges.
 */
@Override
public int edgeSize() {
	return this.e_size;
}
/**
 * This function returns in O(1), the number of the changes that has been made if the graph.
 * @return return MC- the number of the changes.
 */
@Override
public int getMC() {
	return this.MC;
}
}


