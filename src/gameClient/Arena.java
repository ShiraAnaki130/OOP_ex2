package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import api.game_service;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents the arena of pokemon game.
 * The arena includes:
 * 1.The directional weighted graph where the game takes place.
 * 2.The agents which move on a graph and grabs pokemons.
 * 3.The pokemons in which scattered randomly on the graph.
 * @author Lea.Shira;
 */
public class Arena {
	public static final double EPS1 = 0.001, EPS2=EPS1*EPS1, EPS=EPS2;
	private directed_weighted_graph _gg;
	private List<CL_Agent> _agents;
	private List<CL_Pokemon> _pokemons;
	private List<String> _info;
	private game_service _game;
	private int _scenario;
	private static Point3D MIN = new Point3D(0, 100,0);
	private static Point3D MAX = new Point3D(0, 100,0);

	public Arena() {;
		_info = new ArrayList<String>();
	}
	private Arena(directed_weighted_graph g, List<CL_Agent> r, List<CL_Pokemon> p,game_service game, int scenario) {
		_gg = g;
		this._game = game;
		this._scenario = scenario;
		this.setAgents(r);
		this.setPokemons(p);
	}
	/**
	 * This function allows to set the pokemon's list.
	 * @param f- the pokemon's list.
	 */
	public void setPokemons(List<CL_Pokemon> f) {
		this._pokemons = f;
	}
	/**
	 * This function allows to set the agent's list.
	 * @param f- the agent's list.
	 */
	public void setAgents(List<CL_Agent> f) {
		this._agents = f;
	}
	/**
	 * This function allows to set the game's graph.
	 * @param g- the game's graph.
	 */
	public void setGraph(directed_weighted_graph g) {this._gg =g;}
	/**
	 * This function returns the the agent's list
	 * @return the agent's list.
	 */
	public List<CL_Agent> getAgents() {return _agents;}
	/**
	 * This function returns the the pokmons's list
	 * @return the pokmons's list.
	 */
	public List<CL_Pokemon> getPokemons() {return _pokemons;}
	/**
	 * This function returns the current game.
	 * @return the pokmons's list.
	 */
	public game_service getGame(){
		return this._game;
	}
	/**
	 * This function allows to game's server.
	 * @param game- the new game's server.
	 */
	public void setGame(game_service game){
		this._game = game;
	}
	
	public int getScenario(){
		return this._scenario;
	}
	/**
	 * This function allows to change the game's scenario.
	 * @param scen- the new game's scenario.
	 */
	public void setScenario(int scen){
		this._scenario = scen;
	}
	/**
	 * This function returns the the game's current graph.
	 * @return  the game's current graph.
	 */
	public directed_weighted_graph getGraph() {
		return _gg;
	}
	/**
	 * This function returns the the arena's info.
	 * @return  the arena's info.
	 */
	public List<String> get_info() {
		return _info;
	}
	/**
	 * This function allows to change the arena's info.
	 * @param _info- the new arena's info.
	 */
	public void set_info(List<String> _info) {
		this._info = _info;
	}

/**
 * This function builds the agents list of this current game.
 * @param game- a String object which describe the current game.
 * @param graph- the game's graph
 * @return returns the game's agents list.
 */
	public static List<CL_Agent> getAgents(String game, directed_weighted_graph graph) {
		ArrayList<CL_Agent> ans = new ArrayList<CL_Agent>();
		try {
			JSONObject gamejson = new JSONObject(game);
			JSONArray agsents = gamejson.getJSONArray("Agents");
			for(int i=0;i<agsents.length();i++) {
				CL_Agent c = new CL_Agent(graph,0);
				c.update(agsents.get(i).toString());
				ans.add(c);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ans;
	}
	/**
	 * This function builds the pokemons list of the current game.
	 * @param json- a String object which describes the pokemons on every level in the game.
	 * @return returns the game's pokemons list. 
	 */
	public static ArrayList<CL_Pokemon> json2Pokemons(String json) {
		ArrayList<CL_Pokemon> ans = new  ArrayList<CL_Pokemon>();
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONArray Pokemons = jsonObject.getJSONArray("Pokemons");
			for(int i=0;i<Pokemons.length();i++) {
				JSONObject current = Pokemons.getJSONObject(i);
				JSONObject pokemon = current.getJSONObject("Pokemon");
				int type = pokemon.getInt("type");
				double value = pokemon.getDouble("value");
				String p = pokemon.getString("pos");
				CL_Pokemon f = new CL_Pokemon(new Point3D(p), type, value);
				ans.add(f);

			}
		}
		catch (JSONException e) {e.printStackTrace();}
		return ans;
	}
	/**
	 * This function finds the edge which the current pokemon is on and set the pokemon's edge.
	 * This function uses the private functions below for the calculation.
	 * @param pokemon - the current pokemon.
	 * @param g - the graph  of the game.
	 */
	public static void updateEdge(CL_Pokemon pokemon, directed_weighted_graph g) {
		Iterator<node_data> itr = g.getV().iterator();
		while(itr.hasNext()) {
			node_data v = itr.next();
			Iterator<edge_data> iter = g.getE(v.getKey()).iterator();
			while(iter.hasNext()) {
				edge_data e = iter.next();
				boolean bol = isOnEdge(pokemon.getLocation(), e,pokemon.getType(), g);
				if(bol) {pokemon.set_edge(e);}
			}
		}
	}
	
	private static boolean isOnEdge(geo_location p, geo_location src, geo_location dest ) {
		boolean ans = false;
		double dist = src.distance(dest);
		double d1 = src.distance(p) + p.distance(dest);
		if(dist>d1-(EPS2*EPS2)) {ans = true;}
		return ans;
	}
	private static boolean isOnEdge(geo_location p, int s, int d, directed_weighted_graph g) {
		geo_location src = g.getNode(s).getLocation();
		geo_location dest = g.getNode(d).getLocation();
		return isOnEdge(p,src,dest);
	}
	private static boolean isOnEdge(geo_location p, edge_data e, int type, directed_weighted_graph g) {
		int src = g.getNode(e.getSrc()).getKey();
		int dest = g.getNode(e.getDest()).getKey();
		if(type<0 && dest>src) {return false;}
		if(type>0 && src>dest) {return false;}
		return isOnEdge(p,src, dest, g);
	}

	private static Range2D GraphRange(directed_weighted_graph g) {
		Iterator<node_data> itr = g.getV().iterator();
		double x0=0,x1=0,y0=0,y1=0;
		boolean first = true;
		while(itr.hasNext()) {
			geo_location p = itr.next().getLocation();
			if(first) {
				x0=p.x(); x1=x0;
				y0=p.y(); y1=y0;
				first = false;
			}
			else {
				if(p.x()<x0) {x0=p.x();}
				if(p.x()>x1) {x1=p.x();}
				if(p.y()<y0) {y0=p.y();}
				if(p.y()>y1) {y1=p.y();}
			}
		}
		Range xr = new Range(x0,x1);
		Range yr = new Range(y0,y1);
		return new Range2D(xr,yr);
	}
	public static Range2Range w2f(directed_weighted_graph g, Range2D frame) {
		Range2D world = GraphRange(g);
		Range2Range ans = new Range2Range(world, frame);
		return ans;
	}

}
