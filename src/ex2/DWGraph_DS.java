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
private class edges_direction{
	HashMap<Integer,edge_data> edgesNi;
	public edges_direction() {
		edgesNi=new HashMap<Integer,edge_data>();
	}
	public boolean hasNi(int other) {
		if(edgesNi.containsKey(other)) return true;
		return false;
	}
	public void addNi(int other, edge_data edge) {
		edgesNi.put(other, edge);
	}
	/**
	 * This function removes the edge this<-->other.
	 * @param other- this is the node which the edge ends at.
	 */
	public void removeNode(int other) {
		if(edgesNi.containsKey(other)) 
			edgesNi.remove(other);
		
	}
	/**
	 * This function returns the weight of the edge between 
	 * this<-->other,in case the edge between them is existent.
	 * @param other- this is the other node,which the edge is connected between.
	 * @return the weight of the edge between this<-->other,or -1 in case the edge isn't existent. 
	 */
	public double getWeight(int other) {
		if(hasNi(other)) {
			return edgesNi.get(other).getWeight();
		}
		return -1;
	}
}
//DWGraph_DS:

HashMap<Integer,node_data> map;
HashMap<Integer,edges_direction> nodes_edges;

@Override
public node_data getNode(int key) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public edge_data getEdge(int src, int dest) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void addNode(node_data n) {
	// TODO Auto-generated method stub
	
}

@Override
public void connect(int src, int dest, double w) {
	// TODO Auto-generated method stub
	
}

@Override
public Collection<node_data> getV() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Collection<edge_data> getE(int node_id) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public node_data removeNode(int key) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public edge_data removeEdge(int src, int dest) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public int nodeSize() {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public int edgeSize() {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public int getMC() {
	// TODO Auto-generated method stub
	return 0;
}
}

