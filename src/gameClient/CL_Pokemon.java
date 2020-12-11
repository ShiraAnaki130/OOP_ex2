package gameClient;
import gameClient.util.Point3D;

import org.json.JSONObject;

import api.edge_data;

public class CL_Pokemon implements Comparable<CL_Pokemon> {
	private double _value;
	private int _type;
	private Point3D _pos;
	private edge_data _edge;
//	private double min_dist;
//	private int min_ro;
	@Override
	public int compareTo(CL_Pokemon o) {
		int ans=0;
		if(this._value-o.getValue()>0) ans=-1;
		else if(this._value-o.getValue()<0) ans=1;
		return ans;
	}
	
	public CL_Pokemon(Point3D p, int t, double v) {
		_type = t;
		_value = v;
		_pos = p;
		//	_speed = s;
		_edge=null;
	}
	public CL_Pokemon init_from_json(String json) {
		CL_Pokemon ans = null;
		try {
				JSONObject pokemon = new JSONObject(json);
				double value=pokemon.getDouble("value");
				int type=pokemon.getInt("type");
				String pos=pokemon.getString("pos");
				String[] arr=new String[3];
	        	arr=pos.split(",");
	        	double[] arrForPos=new double[3];
	        	for(int k=0;k<3;k++) {
	        		arrForPos[k]=Double.parseDouble(arr[k]);
	        	}
	        	Point3D pos_=new Point3D(arrForPos[0],arrForPos[1],arrForPos[2]);
	        	ans=new CL_Pokemon(pos_,type,value);
	        	return ans;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ans;
	}
	public String toString() {return "F:{v="+_value+", t="+_type+"}";}
	public edge_data get_edge() {
		return _edge;
	}

	public void set_edge(edge_data _edge) {
		this._edge = _edge;
	}

	public Point3D getLocation() {
		return _pos;
	}
	public int getType() {return _type;}
//	public double getSpeed() {return _speed;}
	public double getValue() {return _value;}

//	public double getMin_dist() {
//		return min_dist;
//	}
//
//	public void setMin_dist(double mid_dist) {
//		this.min_dist = mid_dist;
//	}
//
//	public int getMin_ro() {
//		return min_ro;
//	}
//
//	public void setMin_ro(int min_ro) {
//		this.min_ro = min_ro;
//	}
}
