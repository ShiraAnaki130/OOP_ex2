package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;
import org.json.JSONObject;
import java.util.List;
/**
 * This class represents an agent for the pokemon game.
 * @author Lea.Shira;
 */
public class CL_Agent {
		public static final double EPS = 0.0001;
		private  int _count;
		private static int _seed = 3331;
		private int _id;
		private geo_location _pos;
		private double _speed;
		private edge_data _curr_edge;
		private node_data _curr_node;
		private directed_weighted_graph _gg;
		private CL_Pokemon _curr_fruit;
		private long _sg_dt;
		private double _value;
		

		public CL_Agent(directed_weighted_graph g, int start_node) {
			_gg = g;
			setMoney(0);
			this._curr_node = _gg.getNode(start_node);
			_pos = _curr_node.getLocation();
			_id =-1;
			setSpeed(0);
		}
		@Override
		public boolean equals(Object ag){
		if(!(ag instanceof CL_Agent)) return false;
		CL_Agent other = (CL_Agent)ag;
		if(other.getID()==this.getID()&&other.getValue()==this.getValue()&&other.getLocation()==this.getLocation()&&other.getSpeed()==this.getSpeed()&&other.getSrcNode()==this.getSrcNode()&&other.getNextNode()==this.getNextNode()) return true;
		return false;
		}
		/**
		 * This function is getting a String object(JSON string) and set this agent' fields
		 * according to the information in the JSON string.
		 * @param json- JSON string;
		 */
		public void update(String json) {
			JSONObject line;
			try {
				line = new JSONObject(json);
				JSONObject Agent = line.getJSONObject("Agent");
				int id = Agent.getInt("id");
				if(id==this.getID() || this.getID() == -1) {
					if(this.getID() == -1) {_id = id;}
					double speed = Agent.getDouble("speed");
					String p = Agent.getString("pos");
					Point3D pp = new Point3D(p);
					int src = Agent.getInt("src");
					int dest = Agent.getInt("dest");
					double value = Agent.getDouble("value");
					this._pos = pp;
					this.setCurrNode(src);
					this.setSpeed(speed);
					this.setNextNode(dest);
					this.setMoney(value);
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * This function build a JSON string according to the fields's values of this agent.
		 * @return returns the JSON string according to the fields's values of this agent.
		 */
		public String toJSON() {
			int d = this.getNextNode();
			String ans = "{\"Agent\":{"
					+ "\"id\":"+this._id+","
					+ "\"value\":"+this._value+","
					+ "\"src\":"+this._curr_node.getKey()+","
					+ "\"dest\":"+d+","
					+ "\"speed\":"+this.getSpeed()+","
					+ "\"pos\":\""+_pos.toString()+"\""
					+ "}"
					+ "}";
			return ans;	
		}
		/**
		 * This function returns the key of the node this agent is on.
		 * @return the key of the agent's current node.
		 */
		public int getSrcNode() {return this._curr_node.getKey();}
		/**
		 * This function allows to change this agent's value.
		 * @param v- the new value.
		 */
		private void setMoney(double v) {_value = v;}
		/**
		 * This function allows to set the next destination of this agent.
		 * @param dest- the new destination.
		 * @return true: if the change succeed or false if not.
		 */
		public boolean setNextNode(int dest) {
			boolean ans = false;
			int src = this._curr_node.getKey();
			this._curr_edge = _gg.getEdge(src, dest);
			if(_curr_edge!=null) {
				ans=true;
			}
			else {_curr_edge = null;}
			return ans;
		}
		/**
		 * This function allows to change the node of this agent.
		 * @param src- the new node_id.
		 */
		public void setCurrNode(int src) {
			this._curr_node = _gg.getNode(src);
		}
		/**
		 * This function checks if the agents is moving.
		 * @return returns true if the agent is moving or false if not.
		 */
		public boolean isMoving() {
			return this._curr_edge!=null;
		}
		/**
		 * This function make a string of this agent.
		 * @return string which describes this agent.
		 */
		public String toString() {
			return toJSON();
		}
		/**
		 * This function make a string of this agent with only 
		 * fields of id,pos,isMoving and value.
		 * @return string which describes this agent.
		 */
		public String toString1() {
			String ans=""+this.getID()+","+_pos+", "+isMoving()+","+this.getValue();	
			return ans;
		}
		/**
		 * This function return this agent id.
		 * @return returns the agent's id
		 */
		public int getID() {
			return this._id;
		}
		public void setID(int id) {
			 this._id=id;
		}
		/**
		 * This function returns the location of this agent.
		 * @return the agent's location.
		 */
		public geo_location getLocation() {
			return _pos;
		}
		/**
		 * This function returns the value of this egent.
		 * @return returns the agent's value.
		 */
		
		public double getValue() {
			return this._value;
		}
		/**
		 * This function returns the next node on the edge that this agent is on.
		 * @return returns the next node's key,or -1 if the edge is null.
		 */
		public int getNextNode() {
			int ans = -2;
			if(this._curr_edge==null) {
				ans = -1;}
			else {
				ans = this._curr_edge.getDest();
			}
			return ans;
		}
		/**
		 * This function returns the current speed of this agent.
		 * @return he current speed of this agent.
		 */
		public double getSpeed() {
			return this._speed;
		}
		/**
		 * This function allows to set this agent's speed.
		 * @param v- the new agent's speed
		 */
		public void setSpeed(double v) {
			this._speed = v;
		}
		/**
		 * This function returns the agent's current fruit(pokemon).
		 * @return the current fruit(pokemon).
		 */
		public CL_Pokemon get_curr_fruit() {
			return _curr_fruit;
		}
		/**
		 * This function allows to set the fruit.
		 * @param curr_fruit - the new fruit.
		 */
		public void set_curr_fruit(CL_Pokemon curr_fruit) {
			this._curr_fruit = curr_fruit;
		}
		public void set_SDT(long ddtt) {
			long ddt = ddtt;
			if(this._curr_edge!=null) {
				double w = get_curr_edge().getWeight();
				geo_location dest = _gg.getNode(get_curr_edge().getDest()).getLocation();
				geo_location src = _gg.getNode(get_curr_edge().getSrc()).getLocation();
				double de = src.distance(dest);
				double dist = _pos.distance(dest);
				if(this.get_curr_fruit().get_edge()==this.get_curr_edge()) {
					 dist = _curr_fruit.getLocation().distance(this._pos);
				}
				double norm = dist/de;
				double dt = w*norm / this.getSpeed(); 
				ddt = (long)(dt*ddt);
			}
			this.set_sg_dt(ddt);
		}
		
		public edge_data get_curr_edge() {
			return this._curr_edge;
		}
		public void set_curr_edge(edge_data edge) {
			this._curr_edge=edge;
		}
		public long get_sg_dt() {
			return _sg_dt;
		}
		public void set_sg_dt(long _sg_dt) {
			this._sg_dt = _sg_dt;
		}

	}

