package api;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import gameClient.CL_Pokemon;
/**
 * This class represents a directional weighted graph.
 * Each vertex in the graph is from the type of node_data and
 * every edge is from the type of edge_data has a positive weight.
 * This class represents the graph uses the data structure from the type of HahMap.
 * The class uses two HashMap: one for the vertexes in the graph with the pair of (key,node)
 * and another (key,edges_direction) for the collections containing all the edges 
 * which getting out of every vertex in the graph. 
 * The object edges_direction stores for every vertex the collections containing all the edges 
 * which getting out of it.
 * The class supports several operations applicable on a graph,
 * such as removing or adding a vertex or an edge, returning the weight of existing edge and etc.
 * @author Lea.Shira;
 */
public class DWGraph_DS implements directed_weighted_graph{
private HashMap<Integer,node_data> Nodes;
private HashMap<Integer,edges_direction> Edges;
private HashMap<Integer,Collection<Integer>> meAsDest;
private int MC;
private int e_size;

	/**
	 * default constructor create a empty graph.
	 */
	public DWGraph_DS(){
	this.Nodes = new HashMap<>();
	this.Edges = new HashMap<>();
	this.meAsDest = new HashMap<>();
	this.MC = 0;
	this.e_size= 0;
}
/**
 * This function is a copy constructor of DWGraph_DS.
 * The function is getting a graph from the type of directed_weighted_graph and 
 * builds a new one by copying all of the values of the excepted graph to the new graph.  
 * Note: needed for DWGraph_Algo.
 * @param other- this is the graph which the function makes an identical copy of.
 */
public DWGraph_DS(directed_weighted_graph other) {
	this.Nodes = new HashMap<Integer,node_data>();
	this.Edges = new HashMap<Integer,edges_direction>();
	this.meAsDest = new HashMap<Integer, Collection<Integer>>();
	node_data nodeToAddSrc;
	node_data nodeToAddDest;
	edges_direction ni;
	Collection<Integer> as_dest;
	int src;int dest;double w;
	for(node_data node:other.getV()){
		src=node.getKey();
		if(!this.Nodes.containsKey(src)) {
			nodeToAddSrc=new NodeData(src);
			this.Nodes.put(src, nodeToAddSrc);
			ni=new edges_direction();
			this.Edges.put(src, ni);
			as_dest = new HashSet<>();
			this.meAsDest.put(src,as_dest);
		}
		for(edge_data edge:other.getE(src)) {
			dest=edge.getDest();
			w=edge.getWeight();
			if(!this.Nodes.containsKey(dest)) {
				nodeToAddDest=new NodeData(dest);
				this.Nodes.put(dest, nodeToAddDest);
				ni=new edges_direction();
				this.Edges.put(dest, ni);
				as_dest = new HashSet<>();
				this.meAsDest.put(dest,as_dest);
			}
			connect(src,dest,w);
			this.Edges.get(src).getEdge_(dest).setInfo(edge.getInfo());
			this.Edges.get(src).getEdge_(dest).setTag(edge.getTag());
			this.meAsDest.get(dest).add(src);
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
	return Nodes.get(key);
}
/**
 * This function returns,in O(1),the data of the edge (src--dest), null if none.
 * @param src- this is the node_id which the edge start at.
 * @param dest- this is the nide_id which the edge ends at.
 * @return the data of the edge getting out of src to dest, or null if this edge isn't existent.
 */
@Override
public edge_data getEdge(int src, int dest) {
	if(src!=dest && Nodes.containsKey(src)&&Nodes.containsKey(dest)){
		if(Edges.get(src).hasNi(dest)){
			return Edges.get(src).getEdge_(dest);
		}
		return null;
	}
	return null;
}
/**
 * This function adds,in O(1),a new node to the graph with the given node_data- 
 * in case there is already a node with such a key.
 * @param n- this is the id of the new vertex to add.
 */
@Override
public void addNode(node_data n) {
	if(!Nodes.containsKey(n.getKey())){
		Nodes.put(n.getKey(),n);
		edges_direction e = new edges_direction();
		Collection<Integer> me_as_dest = new HashSet<>() ;
		meAsDest.put(n.getKey(),me_as_dest);
		Edges.put(n.getKey(),e);
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
	if(this.Nodes.containsKey(src)&&this.Nodes.containsKey(dest)&&(src!=dest)){
		if(!Edges.get(src).hasNi(dest)) {
	       edge_data toAdd=new EdgeData(src,dest,w);
	       meAsDest.get(dest).add(src);
	       Edges.get(src).addNi(dest, toAdd);
	       MC++;
	       e_size++;
		}	
	}
	return;
}
/**
 * This function creates in O(1), another pointer for the collection which
 * representing all the nodes in the graph.
 * @return returns another pointer to the collection of the graph's vertexes. 
 */
@Override
public Collection<node_data> getV() {
	return Nodes.values();
}
/**
 *This method returns a collection containing all the 
 *edges which getting out of the given node_id.
 *@param node_id- this is the vertex_id in which the edge's collection belong to.
 * @return another pointer to the edge's collection of the chosen vertex's id. 
 */
@Override
public Collection<edge_data> getE(int node_id) {
	if(Nodes.containsKey(node_id)) {
		return Edges.get(node_id).getNi().values();
	}
	return null;
}
/**
* This function removes the node from the graph by it's node_id,
* and removes all edges which starts or ends at this node.
* This method should run in O(k), V.degree=k, as all the edges should be removed.
* @return the data of the removed node (null if none).
* @param key- this is the node_id which need to be removed. 
 */
@Override
public node_data removeNode(int key) {
	node_data toRemove=getNode(key);
	if(toRemove==null) return toRemove;
	for(Integer k :meAsDest.get(key)) {
			 Edges.get(k).removeNode(key);
			  e_size--;
			  MC++;
		   }
	e_size-=getE(key).size();
	MC+=getE(key).size();
	Edges.remove(key);
	meAsDest.remove(key);
	MC++;
	return Nodes.remove(key);
}
/**
 * This function deletes,in O(1), the edge src--dest from the graph,
 * @param src - the source of the edge.
 * @param dest - the destination of the edge.
 * @return the data of the removed edge or null if the edge doesn't exist in the graph.
 */
@Override
public edge_data removeEdge(int src, int dest) {
	edge_data toRemove=getEdge(src,dest);
	if(toRemove!=null) {
		Edges.get(src).removeNode(dest);
		meAsDest.get(dest).remove(src);
		MC++;
		e_size--;
	}
	return toRemove;
}
/**
 * This function finds in O(1), the numbers of the nodes in the graph.
 * @return returns the number of vertices (nodes) in the graph.
 */
@Override
public int nodeSize() {
	return this.Nodes.size();
}
/**
 * This function returns in O(1), the number of the edges in the graph.
 * @return returns the number of the edges in the graph.
 */
@Override
public int edgeSize() {
	return this.e_size;
}
/**
 * This function returns in O(1), the number of the changes that have been made in the graph.
 * @return returns MC- the number of the changes.
 */
@Override
public int getMC() {
	return this.MC;
}

	/**
	 * This function check each node in the graph ,for each node the function checks if the graphs as the same edges
	 * Associated, and the weight of the edges is the same.
	 * @param g- this is the other object.
	 * @return returns true if this  DWGraph_DS and the other object are equals, otherwise returns false.
	 */
	@Override
	public boolean equals(Object g){
	boolean answer = true;
	if(g==null||!(g instanceof DWGraph_DS)) return false;
	directed_weighted_graph other = (directed_weighted_graph)g;
	for(node_data node :this.getV()){
		if(other.getNode(node.getKey()) != null){
			for(edge_data edge: this.getE(node.getKey())){
				if(other.getEdge(edge.getSrc(),edge.getDest())==null){
					answer = false;
					break;
				}
			}
			}
		else {
			answer = false;
			break;
		}
	}
	return answer;
	}
	/**
	 * This class represents a node (vertex) in an directional weighted graph.
	 * Every node consist of a unique key,weight, location, and tag and info for marking.
	 * @author Lea.shira;
	 */
	public static class NodeData implements node_data{
		private int id;
		private double weight;
		private String info;
		private int tag;
		private static int key1=1;
		private geo_location pos;

		/**
		 *
		 * Default constructor of node data create a new node data with a unique key and default location
		 */
		public NodeData() {
			this.id=key1++;
			this.info="f";
			this.tag=Integer.MAX_VALUE;
			this.weight = 0;
			this.pos=new Geo_Location();
		}

		/**
		 * constructor by key, create new node data with a given key and a default location
		 * @param key- this is the given node_id.
		 */
		public NodeData(int key) {
			this.id=key;
			this.info="f";
			this.tag=Integer.MAX_VALUE;
			this.weight = 0;
			this.pos=new Geo_Location();
		}

		/**
		 * constructor by key and geo_location.
		 * @param key- this is the given node_id.
		 * @param x- this is the x field of the geo_location.
		 * @param y- this is the y field of the geo_location.
		 * @param z- this is the z field of the geo_location.
		 */
		public NodeData(int key,double x, double y, double z) {
			this.id=key;
			this.info="f";
			this.tag=Integer.MAX_VALUE;
			this.weight = 0;
			this.pos=new Geo_Location(x,y,z);
		}

		/**
		 * This function returns the node's key
		 * @return returns the node's key
		 */
		@Override
		public int getKey() {
			return id;
		}

		/**
		 * This function returns the 3D point location of the node.
		 * @return returns the node's location.
		 */
		@Override
		public geo_location getLocation() {
			return pos;
		}

		/**
		 * This function sets the 3D point location of the node by given location
		 * @param p -new location (position) of this node.
		 */
		@Override
		public void setLocation(geo_location p) {
			pos=new Geo_Location(p);
		}

		/**
		 * This function returns the weight of the node.
		 * @return returns the node's weight.
		 */
		@Override
		public double getWeight() {
			return this.weight;
		}

		/**
		 * This function sets the weight of the node by a given weight.
		 * @param w - the new weight.
		 */
		@Override
		public void setWeight(double w) {
			this.weight=w;
		}

		/**
		 * This function returns the info of this node.
		 * @return returns node's info.
		 */
		@Override
		public String getInfo() {
			return this.info;
		}

		/**
		 * This function sets the info of the node by a given string.
		 * @param s - the new node's info.
		 */
		@Override
		public void setInfo(String s) {
			this.info=""+s;	
		}

		/**
		 *This function returns a tag mark of the node (used for algorithms)
		 * @return the node's tag.
		 */
		@Override
		public int getTag() {
			return this.tag;
		}

		/**
		 * This function sets a tag mark of the node (used for algorithms)
		 * @param t - the new value of the tag.
		 */
		@Override
		public void setTag(int t) {
			this.tag=t;
		}

		/**
		 * This class represent a geographic 3D point location of a node data.
		 * @author Lea.shira;
		 */
		public class Geo_Location implements geo_location{
			private double _x;
			private double _y;
			private double _z;

			/**
			 * Default geographic location constructor(0.0,0.0,0.0)
			 */
			public Geo_Location() {
				this._x=0.0;
				this._y=0.0;
				this._z=0.0;
			}

			/**
			 * copy constructor of the graph set the this location to a given location.
			 * @param p- the other geo_location.
			 */
			public Geo_Location(geo_location p) {
				this._x=p.x();
				this._y=p.y();
				this._z=p.z();
			}

			/**
			 * this constructor create a new geographic location by given (x,y,z) values
			 * @param x- this is the x field of the geo_location.
			 * @param y- this is the y field of the geo_location.
			 * @param z- this is the z field of the geo_location.
			 */
			public Geo_Location(double x,double y,double z) {
				this._x=x;
				this._y=y;
				this._z=z;
			}

			/**
			 * This function returns the x position of the node.
			 * @return returns the x position of the node.
			 */
			@Override
			public double x() {
				return this._x;
			}

			/**
			 *This function returns the y position of the node.
			 * @return returns the y position of the node.
			 */
			@Override
			public double y() {
				return this._y;
			}

			/**
			 * This function returns the z position of the node.
			 * @return returns the z position of the node.
			 */
			@Override
			public double z() {
				return this._z;
			}

			/**
			 * This function calculates the distance between this node geographic location to a given geographic location.
			 * @param g- the other geo_location.
			 * @return returns the distance between this node geographic location to a given geographic location.
			 */
			@Override
			public double distance(geo_location g) {
				double dis_x=Math.pow(this._x-g.x(), 2);
				double dis_y=Math.pow(this._y-g.y(), 2);
				double dis_z=Math.pow(this._z-g.z(), 2);
				return Math.sqrt(dis_x+dis_y+dis_z);
			}
			/**
			 * This function checks the given object is equals to this geo_location.
			 * @param pos- this is the other object.
			 * @return returns true if this  geo_location and the other object are equals, otherwise returns false.
			 */
			@Override
			public boolean equals(Object pos){
			if(pos==null||!(pos instanceof Geo_Location)) return false;
			Geo_Location other = (Geo_Location)pos;
			if(other.x()==this.x()&&other.y()==this.y()&&other.z()==this.z()) return true;
			return false;
			}

		}
	}

/**
 * This public class represents an edge in an directional weighted graph.
 * Every edge consist of src(edge's start node_id), dest(edge's end node_id), edge's weight,
 * tag and info(for marking).
 * @author Lea.Shira;
 *
 */

public static class EdgeData implements edge_data,Comparable<edge_data>{
	private int src;
	private int dest;
	private double w;
	private String info;
	private int tag;

	/**
	 * default constructor create a new edge.
	 * @param src- the node_id of the edge's starting point.
	 * @param dest- the node_id of the edge's destination.
	 * @param weight- the edge's weight.
	 */
	public EdgeData(int src,int dest,double weight) {
		this.src=src;
		this.dest=dest;
		this.w=weight;
		this.info="f";
		this.tag=0;
	}
	/**
	 * The function returns the id of the source node of this edge.
	 * @return returns id of the source node of this edge.
	 */
	@Override
	public int getSrc() {
		return this.src;
	}
	/**
	 * This function returns the id of the destination node of this edge.
	 * @return returns id of the destination node of this edge.
	 */
	@Override
	public int getDest() {
		return this.dest;
	}
	/**
	 *This function returns the weight of this edge (only positive value).
	 * @return returns the edge's weight.
	 */
	@Override
	public double getWeight() {
		return this.w;
	}
	/**
	 * Returns the remark (meta data) associated with this edge.
	 * @return returns the edge's info.
	 */
	@Override
	public String getInfo() {
		return this.info;
	}
	/**
	 * Allows changing the remark (meta data) associated with this edge.
	 * @param s - the new edge's info.
	 */
	@Override
	public void setInfo(String s) {
		this.info = s;
		
	}
	/**
	 * This function returns the Temporal data of this edge.
	 *  @return returns the edge's tag.
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

	/**
	 * This function checks if this edge equals to a given edge by source and destination keys and the edge weight.
	 * @param other- this is the other object.
	 * @return returns true if this edge and the other object are equals, otherwise returns false.
	 */
	@Override
	public boolean equals(Object other) {
		if(other==null||!(other instanceof edge_data)) return false;
		edge_data edge=(edge_data)other;
		if(getSrc()==edge.getSrc()&&getDest()==edge.getDest()&&getWeight()==edge.getWeight()) return true;
		return false;
	}

	/**
	 * This function is comparing this edge with other edge by the associated tag mark
	 * @param o- the other edge.
	 * @return returns -1: if this tag is bigger then o.tag,
	 * return 1: if this tag is smaller then o.tag, or returns 0 if the tags are equals.
	 */
	@Override
	public int compareTo(edge_data o) {
		int ans=0;
		if(this.tag-o.getTag()>0) ans=-1;
		else if(this.tag-o.getTag()<0) ans=1;
		return ans;
	}
}
/**
 * This private class represents all the edges getting out of a given node.
 * uses a data structure from the type of HahMap.
 * @author Lea.shira;
 */
public static class edges_direction{
	HashMap<Integer,edge_data> edgesNi;

	/**
	 * default constructor create new set of edges by destination for one course node.
	 */
	public edges_direction() {
		this.edgesNi=new HashMap<Integer,edge_data>();
	}

	/**
	 * copy constructor that set a given set of set of edges.
	 * @param edges - HashMap that represent the edges with the same source node.
	 */
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
	 * This function adds edge to be another neighbor of this current node_id. 
	 * @param other- this is the id of the destination node of this edge.
	 * @param edge- this is the new edge which this node is the source 
	 * of this edge and 'other' is the id of the edge's destination.
	 */
	public void addNi(int other, edge_data edge) {
		edgesNi.put(other, edge);
	}
	/**
	 * This function removes the edge this --other.
	 * @param other- this is the node which the edge ends at.
	 */
	public void removeNode(int other) {
		if(edgesNi.containsKey(other)) 
			edgesNi.remove(other);
		
	}
	/**
	 * This function returns the weight of the edge from this to other: 
	 * this--other,in case the edge is existent.
	 * @param other- this is the other node_id,which may the edge ends at.
	 * @return the weight of the edge from this--(to)other,or -1 in case the edge isn't existent. 
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
}


