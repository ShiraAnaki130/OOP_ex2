package ex2;

import java.util.Collection;
import java.util.HashMap;
public class DWGraph_DS implements directed_weighted_graph {
	
private static class NodeData implements node_data{
	private int key;
	private double weight;
	private String info;
	private int tag;
	private static int key1=0;
	private geo_location location;
	
	public NodeData() {
		this.key=key1++;
		this.info="f";
		this.tag=Integer.MAX_VALUE;
		this.weight = 0;
		this.location=new Geo_Location();
		
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

	private class Geo_Location implements geo_location{
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

private class EdgeData implements edge_data{
	private int src;
	private int dest;
	private double weight;
	private String info;
	private int tag;
	
	public EdgeData(int src,int dest,double weight) {
		this.src=src;
		this.dest=dest;
		this.weight=weight;
		this.info="f";
		this.tag=Integer.MAX_VALUE;
	}
	@Override
	public int getSrc() {
		return this.src;
	}

	@Override
	public int getDest() {
		return this.dest;
	}

	@Override
	public double getWeight() {
		return this.weight;
	}

	@Override
	public String getInfo() {
		return this.info;
	}

	@Override
	public void setInfo(String s) {
		this.info = s;
		
	}

	@Override
	public int getTag() {
		return this.tag;
	}

	@Override
	public void setTag(int t) {
		this.tag = t;
	}
	
}
/**
 * This private class represents all the edges getting out of a given node.
 * uses a data structure from the type of HahMap.
 */
private class edges_direction{
	HashMap<Integer,edge_data> edgesNi;
	public edges_direction() {
		edgesNi=new HashMap<Integer,edge_data>();
	}
	/**
	 * This function returns the collection with all the edges which getting out of this given node.  
	 * @return HashMap-a data structure which representing the edges collection of this given node.
	 */
	public HashMap<Integer,edge_data> getNi() {
		return edgesNi;
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
	public edge_data getE(int key){
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
			return nodes_edges.get(src).getE(dest);
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
@Override
public void connect(int src, int dest, double w) {
	if(map.containsKey(src)&&map.containsKey(dest)&&(src!=dest)){
		if(!nodes_edges.get(src).hasNi(dest)) {
	       edge_data toAdd=new EdgeData(src,dest,w);
	       nodes_edges.get(src).addNi(dest, toAdd);
	       MC++;
	       e_size++;
		}	
	}
	return;
}

@Override
public Collection<node_data> getV() {
	return map.values();
}

@Override
public Collection<edge_data> getE(int node_id) {
	if(map.containsKey(node_id)) {
		return nodes_edges.get(node_id).getNi().values();
	}
	return null;
}

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

