package gameClient;
import Server.Game_Server_Ex2;
import api.*;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Ex2_Client implements Runnable {
	private static MyFrame _win;
	private static Arena _ar;
	private int scenario_num ;
	private	int id;

	private static int count=0;
	private static HashMap<Integer,CL_Pokemon> takenPokemons;
//	public static void main(String[] args) {
//		Thread client = new Thread(new Ex2_Client());
//		client.start();
//	}
	public Ex2_Client(int id,int scenario_num){
		this.scenario_num = scenario_num;
		this.id = id;
	}
	@Override
	public void run() {
		game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
		//	game.login(id);
		String g = game.getGraph();
		directed_weighted_graph graph=new DWGraph_DS();
		try {
			dw_graph_algorithms algo = new DWGraph_Algo();
			FileWriter fileForLoad= new FileWriter("graphOfGame");
			fileForLoad.write(g);
			fileForLoad.flush();
			fileForLoad.close();
			algo.load("graphOfGame");
			graph = algo.getGraph();
			}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		try {
			init(game,graph);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		takenPokemons =new HashMap<Integer,CL_Pokemon>();
		game.startGame();
		_win.setTitle("Game's level number " + scenario_num);
		int dt = 115;
		int count=0;
		while (game.isRunning()) {
			synchronized (game_service.class) {
				try {	
					takenPokemons =new HashMap<Integer,CL_Pokemon>();
					moveAgants(game, graph);
					System.out.println("count "+count);
					count++;
					_win.repaint();
					Thread.sleep(dt);
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
	 * Moves each of the agents along the edge,
	 * in case the agent is on a node the next destination (next edge) is chosen (randomly).
	 *
	 * @param game
	 * @param gg
	 * @param
	 * @throws JSONException
	 */
	private void moveAgants(game_service game, directed_weighted_graph gg) {
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
				if (dest == -1) {
					dest = randomDest(src, listP);
					path = ga.shortestPath(src, dest);
				} else
					path = ga.shortestPath(src, dest);
				if (path != null) {
					for (node_data node : path) {
						game.chooseNextEdge(agent.getID(), node.getKey());
					}
				}
			}
			System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest);
		}
	}




private static int nextNode(directed_weighted_graph g, int src, List<CL_Pokemon> allPo) {
	dw_graph_algorithms ga = new DWGraph_Algo();
	ga.init(g);
	int ans = -1;
	double dist;
	double min = 1000000.0;
	int index=0;
	for (int i=0;i<allPo.size();i++) {
		CL_Pokemon pokemon= allPo.get(i);
		dist = ga.shortestPathDist(src, pokemon.get_edge().getDest());
		if (pokemon.get_edge().getInfo().equals("f")&&dist > -1 && dist < min) {
			min = dist;
			ans = pokemon.get_edge().getDest();
			if(ans==src) ans = pokemon.get_edge().getSrc();
			index=i;
		}
	}
	allPo.get(index).get_edge().setInfo("t");
	return ans;
}

private int randomDest(int src,List<CL_Pokemon> allPo) {
PriorityQueue<CL_Pokemon> priQ= new PriorityQueue<CL_Pokemon>();
int index = 0;
if(allPo!=null) {
	for(CL_Pokemon p: allPo) {
		priQ.add(p);
	}
	CL_Pokemon p=priQ.poll();
	while (p.get_edge().getInfo().equals("f")&&!priQ.isEmpty()) {
		p = priQ.poll();
		index = allPo.indexOf(p);
	}
	allPo.get(index).get_edge().setInfo("t");
	if(p.get_edge().getDest()==src) return p.get_edge().getSrc();
	return p.get_edge().getDest();
	}
return -1;

}
	/**
	 * A private function witch set to each pokemon the edge the pokemon is on it.
	 * @param g
	 * @param allPo
	 */
	private static void pokemonSetEdge(directed_weighted_graph g,List<CL_Pokemon>allPo)  {
		for(CL_Pokemon p: allPo) {
			Arena.updateEdge(p,g);
		}
	}
	/**
	 * a very simple random walk implementation!
	 * @param game
	 * @return
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
