package gameClient;
import gameClient.util.Point3D;
import org.json.JSONObject;
import api.edge_data;
/**
 * This class represents a pokemon for the pokemon game.
 * @author Lea&Shira.
 */
public class CL_Pokemon implements Comparable<CL_Pokemon> {
	private double _value;
	private int _type;
	private Point3D _pos;
	private edge_data _edge;
	private int ID;
	private static int id=0;
	private String info="f";
	
	public CL_Pokemon(Point3D p, int t, double v) {
		_type = t;
		_value = v;
		_pos = p;
		_edge=null;
		ID=id;
		id++;
	}
	/**
	 * This function is getting a String object(JSON string) and builds a new pokemon
	 * according to the information in the JSON string.
	 * @param json- JSON string;
	 */
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
	/**
	 * This function returns a string object which provides the value and the type oh this pokemon.
	 * @return returns string of value and type of this pokemon.
	 */
	public String toString() {return "F:{v="+_value+", t="+_type+"}";}
	/**
	 * This function returns the pokemon's current edge.
	 * @return edge_data:the pokemon's current edge.
	 */
	public edge_data get_edge() {
		return _edge;
	}
	/**
	 * This function allows to change this pokemon's  current edge.
	 * @param _edge the new edge.
	 */
	public void set_edge(edge_data _edge) {
		this._edge = _edge;
	}
	/**
	 * This function returns the pokemon's location.
	 * @return returns Point3D:the pokemon's location.
	 */
	public Point3D getLocation() {
		return _pos;
	}
	/**
	 * This function returns the pokemon's type.
	 * @return returns int:the pokemon's type.
	 */
	public int getType() {return _type;}
	/**
	 * This function returns the pokemon's value.
	 * @return returns double: the pokemon's value.
	 */
	public double getValue() {return _value;}
	/**
	 * This function returns the pokemon's info.
	 * @return returns string: the pokemon's info.
	 */
	public String getInfo() {
		return this.info;
	}
	/**
	 * This function allows to change this pokemon's info.
	 * @param info- the new info.
	 */
	public void setInfo(String info) {
		this.info=info;
	}
	/**
	 * This function returns the pokemon's id.
	 * @return returns the pokemon's id.
	 */
	public int getID() {
		return this.ID;
	}
	@Override
	public int compareTo(CL_Pokemon o) {
		int ans=0;
		if(this._value-o.getValue()>0) ans=-1;
		else if(this._value-o.getValue()<0) ans=1;
		return ans;
	}


}
