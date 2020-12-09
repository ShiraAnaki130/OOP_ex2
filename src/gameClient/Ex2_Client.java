package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class Ex2_Client implements Runnable{
	private static MyFrame _win;
	private static Arena _ar;
	public static void main(String[] args) {
		Thread client = new Thread(new Ex2_Client());
		client.start();
	}
	
	@Override
	public void run() {
		int scenario_num = 14;
		game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
	//	int id = 999;
	//	game.login(id);
		String g = game.getGraph();
		dw_graph_algorithms algo= new DWGraph_Algo();
		try {
			algo.load(g);
		} 
		catch (JSONException e2) {
			e2.printStackTrace();
		}
		directed_weighted_graph graph = algo.getGraph();
		try {
			init(game);
		} 
		catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		game.startGame();
		_win.setTitle("Ex2 - OOP: (NONE trivial Solution) "+game.toString());
		int ind=0;
		long dt=100;
		
		while(game.isRunning()) {
			try {
				moveAgants(game, graph);
				if(ind%1==0) {_win.repaint();}
				Thread.sleep(dt);
				ind++;
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		String res = game.toString();
		System.out.println(res);
		System.exit(0);
	}
	/** 
	 * Moves each of the agents along the edge,
	 * in case the agent is on a node the next destination (next edge) is chosen (randomly).
	 * @param game
	 * @param gg
	 * @param
	 * @throws JSONException 
	 */
	private void moveAgants(game_service game, directed_weighted_graph gg) throws JSONException {
		dw_graph_algorithms ga= new DWGraph_Algo();
		ga.init(gg);
		String lg = game.move();
		List<CL_Agent> listAgents = Arena.getAgents(lg, gg);
		_ar.setAgents(listAgents);
		String fs =  game.getPokemons();
		List<CL_Pokemon> listP = Arena.json2Pokemons(fs);
		_ar.setPokemons(listP);
		pokemonSetEdge(gg,listP);
		for(CL_Agent agent :listAgents) {
			if(!agent.isMoving()) {
				int id = agent.getID();
				int dest = agent.getNextNode();
				int src = agent.getSrcNode();
				double v = agent.getValue();
				agent.setCount(1);
				if (dest == -1) {
					dest = nextNode(gg, src, listP);
					List<node_data> path = ga.shortestPath(src, dest);
					for (node_data node : path) {
						System.out.println(node.getKey());
						game.chooseNextEdge(agent.getID(), node.getKey());
					}
					System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest);
				}
			}
		}
	}

	private static int nextNode(directed_weighted_graph g,int src,List<CL_Pokemon>allPo ) {
		dw_graph_algorithms ga= new DWGraph_Algo();
		ga.init(g);
		int ans=-1;
		double dist;
		double min= Double.MAX_VALUE;
		for(CL_Pokemon currentPokemon:allPo){
	        dist = ga.shortestPathDist(src, currentPokemon.get_edge().getDest());
	        if(dist>0&&dist < min) {
	        	min = dist;
	        	ans = currentPokemon.get_edge().getDest();
	        }
		}
		return ans;
	  }	
	/**
	 * A private function witch set to each pokemon the edge the pokemon is on it.
	 * @param g
	 * @param allPo
	 */
	private static void pokemonSetEdge(directed_weighted_graph g,List<CL_Pokemon>allPo){
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
	private void init(game_service game) throws JSONException {
		String g = game.getGraph();
		String pokemons = game.getPokemons();
		dw_graph_algorithms algo= new DWGraph_Algo();
		algo.load(g);
		directed_weighted_graph graph = algo.getGraph();
		_ar = new Arena();
		_ar.setGraph(graph);
		_ar.setPokemons(Arena.json2Pokemons(pokemons));
		_win = new MyFrame("test Ex2");
		_win.setSize(1000, 700);
		_win.update(_ar);
		_win.show();
		String info = game.toString();
		PriorityQueue<edge_data> priQ= new PriorityQueue<edge_data>();
		HashMap< Integer,edge_data> hash= new HashMap<Integer,edge_data>();
		JSONObject line;
		int des;
		int type=0;
		try {
			line = new JSONObject(info);
			JSONObject gameserver = line.getJSONObject("GameServer");
			int numOfAgents = gameserver.getInt("agents");
			System.out.println(info);
			System.out.println(game.getPokemons());
			ArrayList<CL_Pokemon> allPo = Arena.json2Pokemons(game.getPokemons());
			for(int i = 0;i<allPo.size();i++) { 
				Arena.updateEdge(allPo.get(i),graph);
				edge_data edge = allPo.get(i).get_edge();
				int tag=edge.getTag();
				if(tag==Integer.MAX_VALUE) edge.setTag(1); 
				else edge.setTag(tag+1); 
			}
			for(int i = 0;i<allPo.size();i++) {
				priQ.add(allPo.get(i).get_edge());
			}
			for(int i = 0;i<allPo.size();i++) { 
				edge_data a=allPo.get(i).get_edge();
				System.out.println("src "+a.getSrc()+"dest "+a.getDest());
			}
			for(int a = 0;a<numOfAgents;a++) {
				edge_data edge=priQ.poll();
				if(edge.getTag()==1) {
					int ind = a%allPo.size();
					CL_Pokemon c = allPo.get(ind);
					type =c.getType();
					if(type<0 ) {des = c.get_edge().getSrc();}
					else {des = c.get_edge().getDest();}
					game.addAgent(des);
				}
				else {
					if(type<0 ) {des = edge.getSrc();}
				    else {des =edge.getDest();}
					game.addAgent(des);
				}
			}
		}
		catch (JSONException e) {e.printStackTrace();}
	}
	
}
