package gameClient;
import Server.Game_Server_Ex2;
import api.*;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
/**
 * This class represents the 'pokemon game'.
 *This class is getting a server to the chosen game and by using this class the game will 
 *start and run with strategic moves as long as the time of the game is'nt over. 
 * @author Lea.Shira;
 */

public class Ex2_Client implements Runnable {
	private static MyFrame _win;
	private static Arena _ar;
	private int scenario_num ;
	private	int id;
	private long dt;
	public Ex2_Client(int id, int scenario_num) {
		this.scenario_num = scenario_num;
		this.id = id;
	}
	/**
	 * This function runs as long as the game is running.
	 * Before the game starts this function creates the game's graph by loading the JSON string of the game's graph,
	 * inits the agents at their starting locations and starts the game.
	 * While the game is running this function moves the agents according to their chosen
	 * next edge, and chooses for the next agent's edge.
	 * At the end of the game, this function shows the game's results to the player.
	 */
	@Override
	public void run() {
		game_service game = Game_Server_Ex2.getServer(scenario_num);
		//game.login(id);
		String g = game.getGraph();
		directed_weighted_graph graph = new DWGraph_DS();
		try {
			dw_graph_algorithms algo = new DWGraph_Algo();
			FileWriter fileForLoad = new FileWriter("graphOfGame");
			fileForLoad.write(g);
			fileForLoad.flush();
			fileForLoad.close();
			algo.load("graphOfGame");
			graph = algo.getGraph();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			init(game, graph);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		game.startGame();
		_win.setTitle("Game's level number " + scenario_num);
		_win.repaint();
		this.dt=100;
		while (game.isRunning()) {
			synchronized (Ex2_Client.class) {
				try {
					_win.repaint();
					moveAgants(game, graph);
					Thread.sleep(this.dt);
					System.out.println(dt);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		String res = game.toString();
		System.out.println(res);
		System.exit(0);
	}

	/**
	 * This function moves each agent to the next destination that set for him.
	 * Moreover, in case the agent is on a node- the agent's next destination
	 * will be choose by the algorithms which checks which of pokemon's that on
	 * the graph is closer and more preferable for the current agent.
	 * @param game- the game service.
	 * @param gg- the game's graph. 
	 */
	private void moveAgants(game_service game, directed_weighted_graph gg) {
		synchronized (_ar) {
			this.dt = 100;
			dw_graph_algorithms ga = new DWGraph_Algo();
			ga.init(gg);
			String lg = game.move();
			List<CL_Agent> listAgents = Arena.getAgents(lg, gg);
			_ar.setAgents(listAgents);
			String fs = game.getPokemons();
			List<CL_Pokemon> listP = Arena.json2Pokemons(fs);
			_ar.setPokemons(listP);
			pokemonSetEdge(gg, listP);
			List<node_data> path = null;
			for (CL_Agent agent : listAgents) {
				int id = agent.getID();
				int dest = agent.getNextNode();
				int src = agent.getSrcNode();
				double v = agent.getValue();
				if (dest == -1) {
					dest = nextNode(gg, src, listP);
					path = ga.shortestPath(src, dest);
					if (path != null) {
						boolean upS = path.size() > 3 && agent.getSpeed() < 10;
						if (upS) {
							double s = agent.getSpeed();
							agent.setSpeed(s + path.size() * 3);
						}
						setPkemons(listP, path);
						int prev = src;
						for (node_data node : path) {
							edge_data edge = gg.getEdge(prev, node.getKey());
							agent.set_curr_edge(edge);
							setPokemon(agent, listP, node.getKey(), path);
							game.chooseNextEdge(agent.getID(), node.getKey());
							prev = node.getKey();
						}
						if (agent.get_curr_fruit() != null && agent.get_curr_edge() != null) {
							agent.set_SDT(10);
							try {
								System.out.println(agent.get_sg_dt());
								Thread.sleep(agent.get_sg_dt());
							} catch (Exception e) {
								e.printStackTrace();
							}
							if(listAgents.size()>1)
								this.dt = 40;
						}
						if (upS) {
							double s = agent.getSpeed();
							agent.setSpeed(s - path.size() * 3);
						}
					}
				}
				System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest);
			}
		}

	}
	/**
	 * This function sets the target(fruit) of the agent to be the pokemon which chosen on the 'nextNode' function.
	 * @param ag- the current agent.
	 * @param allPo- the current pokemons list.
	 * @param dest- the agent's next destination.
	 * @param path the chosen current path of the agent.
	 */
private void setPokemon(CL_Agent ag,List<CL_Pokemon> allPo, int dest,List<node_data> path){
		if(allPo!=null&&path!=null){
			for(CL_Pokemon p : allPo){
				int src = p.get_edge().getSrc();
				node_data nodeSrc = _ar.getGraph().getNode(src);
				if(p.get_edge().getDest()==dest && path.contains(nodeSrc)){
					ag.set_curr_fruit(p);
					return;
				}
			}
		}
}
	/**
	 *This function chooses the most preferable next destination for the current agent .
	 *The choosing is conducted by prioritize the pokemons by value and choosing the biggest one. 
	 *In case, there is a closer pokemon than the higher value's pokemon, the current agent will go to him.
	 *Otherwise, the agent's next destination will be the destination of the higher value's pokemon's.
	 * @param g- the graph of the game.
	 * @param src- the agent's current node_id.
	 * @param allPo-  the current pokemons list. 
	 * @return - returns the preferable next destination of the 
	 * current agent or -1 if all pokemons's info are "t".
	 */
	public static int nextNode(directed_weighted_graph g, int src, List<CL_Pokemon> allPo) {
		dw_graph_algorithms ga = new DWGraph_Algo();
		ga.init(g);
		int ans = -1;
		double dist ;
		double min = Double.MAX_EXPONENT;
		PriorityQueue<CL_Pokemon> priQ = new PriorityQueue<CL_Pokemon>();
		if (allPo != null) {
			for (CL_Pokemon p : allPo) {
				priQ.add(p);
			}
			while (!priQ.isEmpty()) {
				CL_Pokemon pokemon = priQ.poll();
				dist = ga.shortestPathDist(src, pokemon.get_edge().getDest());
				if (dist == -1)
					dist = ga.shortestPathDist(src, pokemon.get_edge().getSrc());
				if (pokemon.getInfo().equals("f") && dist < min) {
					min = dist;
					ans = pokemon.get_edge().getDest();
					if (ans == src) ans = pokemon.get_edge().getSrc();
				}
			}
		}
		return ans;
	}
	/**
	 * set mark "t" one the pokemon's edges which in the path.
	 * @param allPo- the current pokemon list . 
	 * @param path- the chosen current path of the agent.
	 */
	private void setPkemons(List<CL_Pokemon> allPo,List<node_data> path){
		if(allPo!=null&&path!=null){
			for(CL_Pokemon p : allPo){
				int dest = p.get_edge().getDest();
				int src = p.get_edge().getSrc();
				if(path.contains(dest)&&path.contains(src))
					p.setInfo("t");
			}
		}
	}
	/**
	 * A private function which set for each pokemon the edge that it's on it.
	 * @param g- the graph of the current game.
	 * @param allPo- the pokemons's list
	 */
	private static void pokemonSetEdge(directed_weighted_graph g,List<CL_Pokemon>allPo)  {
		for(CL_Pokemon p: allPo) {
			Arena.updateEdge(p,g);
		}
	}
	/**
	 * This function inits the agents at their starting nodes before beginning of the game.
	 * @param game- the current game service.
	 * @param graph- the graph of the current game
	 * @throws JSONException 
	 */
	private void init(game_service game,directed_weighted_graph graph) throws JSONException {
		String g = game.getGraph();
		String pokemons = game.getPokemons();
		_ar = new Arena();
		_ar.setGraph(graph);
		_ar.setGame(game);
		_ar.setScenario(scenario_num);
		_ar.setPokemons(Arena.json2Pokemons(pokemons));
		_win = new MyFrame("test Ex2");
		_win.setSize(1000, 700);
		_win.update(_ar);
		_win.show();
		String info = game.toString();
		JSONObject line;
		PriorityQueue<CL_Pokemon> priQ= new PriorityQueue<CL_Pokemon>();
		PriorityQueue<edge_data> priQForEdge= new PriorityQueue<edge_data>();
		HashMap<edge_data,Double> hash= new HashMap<edge_data,Double>();
		int des=0;
		double val;
		int max=0;
		try {
			line = new JSONObject(info);
			JSONObject gameserver = line.getJSONObject("GameServer");
			int numOfAgents = gameserver.getInt("agents");
			ArrayList<CL_Pokemon> allPo = Arena.json2Pokemons(game.getPokemons());
			pokemonSetEdge(graph,allPo);
			for(CL_Pokemon p: allPo) { 
				edge_data edge=p.get_edge();
				int tag=edge.getTag();
				if(tag==0) {
					edge.setTag(1); 
					hash.put(edge, p.getValue());
				}
				else {
					  edge.setTag(tag+1); 
					  val= hash.get(edge);
					  hash.put(edge, val+p.getValue());
				}	
			}
			if(allPo!=null) {
				for(CL_Pokemon p: allPo) {
					priQ.add(p);
					priQForEdge.add(p.get_edge());
				}
			}
			for(int a = 0;a<numOfAgents;a++) {
				if(priQForEdge!=null&&priQ!=null) {
					edge_data e= priQForEdge.poll();
					CL_Pokemon p=priQ.poll();
					if(e.getTag()>1) {
						val=hash.get(e);
						if(val>=p.getValue()) {
							des = e.getSrc();
							priQ.add(p);
						}
						else {
							des = p.get_edge().getSrc();
						}
					}
					else {
							if(e.getTag()==0) {
								des = p.get_edge().getSrc();
							}
							else if(e.getTag()==1){
									val=hash.get(e);
									if(val>p.getValue()) {
										des = e.getSrc();
										priQ.add(p);
									}
									else {
										des = p.get_edge().getSrc();
									}	
							}
					}
				}
				else if(priQForEdge!=null) {
						if(max==0)des = priQForEdge.poll().getSrc();
						else {
								des=(max+graph.nodeSize())/2;
							}
						if(des>max) max=des;
				}
				game.addAgent(des);
			}
		}
		catch (JSONException e) {e.printStackTrace();}
	}
}