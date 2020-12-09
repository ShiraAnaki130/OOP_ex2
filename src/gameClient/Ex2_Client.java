package gameClient;

import Server.Game_Server_Ex2;
import api.DWGraph_Algo;
import api.directed_weighted_graph;
import api.dw_graph_algorithms;
import api.edge_data;
import api.game_service;
import api.node_data;
import gameClient.util.Point3D;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Ex2_Client implements Runnable{
	private static MyFrame _win;
	private static Arena _ar;
	public static void main(String[] a) {
		Thread client = new Thread(new Ex2_Client());
		client.start();
	}
	
	@Override
	public void run() {
		int scenario_num = 9;
		game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
	//	int id = 999;
	//	game.login(id);
		String g = game.getGraph();
		String pks = game.getPokemons();
		directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
		init(game);
		
		game.startGame();
		_win.setTitle("Ex2 - OOP: (NONE trivial Solution) "+game.toString());
		int ind=0;
		long dt=100;
		
		while(game.isRunning()) {
			try {
				moveAgants(game, gg);
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
	private static void moveAgants(game_service game, directed_weighted_graph gg) throws JSONException {
		dw_graph_algorithms ga= new DWGraph_Algo();
		ga.init(gg);
		String lg = game.move();
		List<CL_Agent> listAgents = Arena.getAgents(lg, gg);
		_ar.setAgents(listAgents);
		String fs =  game.getPokemons();
		List<CL_Pokemon> listP = Arena.json2Pokemons(fs);
		_ar.setPokemons(listP);
		pokemonSetEdge(gg,game,listP);
		for(int i=0;i<listAgents.size();i++) {
			CL_Agent ag = listAgents.get(i);
			int id = ag.getID();
			int dest = ag.getNextNode();
			int src = ag.getSrcNode();
			double v = ag.getValue();
			ag.setCount(0);
			if(dest==-1) {
				dest = nextNode(gg, src,listP);
				game.chooseNextEdge(ag.getID(), dest);
				System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+dest);
			}
		}
		//game.move();
		}
	private static int nextNode(directed_weighted_graph g,int src,List<CL_Pokemon>allPo ) throws JSONException {
		dw_graph_algorithms ga= new DWGraph_Algo();
		ga.init(g);
		int ans=-1;
		double dist;
		double min= Double.MAX_VALUE;
		int index=0;
		for(int i=0;i<allPo.size();i++){
			CL_Pokemon currentPokemon= allPo.get(i);
	        dist=ga.shortestPathDist(src, currentPokemon.get_edge().getDest());
	        if(dist<min) {
	        	min=dist;
	        	ans=currentPokemon.get_edge().getDest();
	        	index=i;
	        }
	      }
		allPo.remove(index);
		return ans;
	  }	
	/**
	 * A private function witch set to each pokemon the edge the pokemon is on it.
	 * @param g
	 * @param game
	 * @param allPo
	 * @throws JSONException
	 */
	private static void pokemonSetEdge(directed_weighted_graph g,game_service game,List<CL_Pokemon>allPo) throws JSONException {
		dw_graph_algorithms ga= new DWGraph_Algo();
		ga.init(g);
		String getgraph= game.getGraph();
		ExtendPokemon extedntPokemon= new Ex2_Client.ExtendPokemon();
		HashMap<String,edge_data> hash=extedntPokemon.getHash(getgraph);
		Point3D point;
		double moduloX;
		String key;
		double type;
		String[] arr;
		double[] arrForPos;
		double moduloY;
		double check0=0;
		double check3=0;
		double check1=0;
		double check2=0;
		for(CL_Pokemon currentPokemon :allPo) {
			point=currentPokemon.getLocation();
			moduloX=point.x()%1;
			moduloY=point.y()%1;
			type=(double)currentPokemon.getType();
			for(Map.Entry<String,edge_data> e: hash.entrySet()) {
					key=e.getKey();
					arr=new String[5];
					arr=key.split(",");
					arrForPos=new double[5];
					for(int k=0;k<5;k++) {
						arrForPos[k]=Double.parseDouble(arr[k]);
					}
					if(arrForPos[4]==type) {
						if(moduloX>arrForPos[0]&& moduloX<arrForPos[1]&&moduloY>arrForPos[2]&&moduloY<arrForPos[3]) {
							if(currentPokemon.get_edge()==null) {
							   currentPokemon.set_edge(e.getValue());
							   check1=arrForPos[1];
							   check0=arrForPos[0];
							   check3=arrForPos[3];
							   check2=arrForPos[2];
							}
							else if(currentPokemon.get_edge()!=null){
									if(check0==arrForPos[0]&& check3==arrForPos[3]) {
										if(check1>arrForPos[1]) {
											currentPokemon.set_edge(e.getValue());
											check1=arrForPos[1];
											check0=arrForPos[0];
											check3=arrForPos[3];
											check2=arrForPos[2];
										}
									}
									else if(check1==arrForPos[1]&& check3==arrForPos[3]) {
											if(check0<arrForPos[0]) {
												currentPokemon.set_edge(e.getValue());
												check1=arrForPos[1];
												check0=arrForPos[0];
												check3=arrForPos[3];
												check2=arrForPos[2];
											}
										}
									
									else {
										if(check1>arrForPos[1]&&check3>arrForPos[3]) {
											currentPokemon.set_edge(e.getValue());
											check1=arrForPos[1];
											check0=arrForPos[0];
											check3=arrForPos[3];
											check2=arrForPos[2];
										}
									}
								}	
					}
					}
			}
		}
	}
	/**
	 * a very simple random walk implementation!
	 * @param g
	 * @param src
	 * @return
	 * @throws JSONException 
	 */
	private void init(game_service game) {
		String g = game.getGraph();
		String fs = game.getPokemons();
		directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
		//gg.init(g);
		_ar = new Arena();
		_ar.setGraph(gg);
		_ar.setPokemons(Arena.json2Pokemons(fs));
		_win = new MyFrame("test Ex2");
		_win.setSize(1000, 700);
		_win.update(_ar);

		_win.show();
		String info = game.toString();
		JSONObject line;
		try {
			line = new JSONObject(info);
			JSONObject ttt = line.getJSONObject("GameServer");
			int rs = ttt.getInt("agents");
			System.out.println(info);
			System.out.println(game.getPokemons());
			int src_node = 0;  // arbitrary node, you should start at one of the pokemon
			ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
			for(int a = 0;a<cl_fs.size();a++) { Arena.updateEdge(cl_fs.get(a),gg);}
			for(int a = 0;a<rs;a++) {
				int ind = a%cl_fs.size();
				CL_Pokemon c = cl_fs.get(ind);
				int nn = c.get_edge().getDest();
				if(c.getType()<0 ) {nn = c.get_edge().getSrc();}
				
				game.addAgent(nn);
			}
		}
		catch (JSONException e) {e.printStackTrace();}
	}
	/**
	 * 
	 * @author User
	 *
	 */
	private static class ExtendPokemon{
		private HashMap<String,edge_data> hash;
		public  ExtendPokemon() {
			hash= new HashMap<String,edge_data>();
		}
		public HashMap<String,edge_data> getHash(String json) throws JSONException{
			dw_graph_algorithms algo= new DWGraph_Algo();
			algo.load(json);
			directed_weighted_graph graph=algo.getGraph();
			double moduloXSrc;
			double moduloXDest;
			String srcX=null;
			String destX=null;
			String srcY=null;
			String destY=null;
			String str;
			double moduloYSrc;
			double moduloYDest;
			for(node_data node:graph.getV()) {
				moduloXSrc=node.getLocation().x()%1;
				moduloYSrc=node.getLocation().y()%1;
				for(edge_data edge:graph.getE(node.getKey())) {
					moduloXDest=graph.getNode(edge.getDest()).getLocation().x()%1;
					moduloYDest=graph.getNode(edge.getDest()).getLocation().y()%1;
				   if(moduloYSrc<moduloYDest) {
					   srcY=Double.toString(moduloYSrc);
					   destY=Double.toString(moduloYDest);
				   }
				   else if(moduloYSrc>moduloYDest) {
					   srcY=Double.toString(moduloYDest);
					   destY=Double.toString(moduloYSrc);
				   }
				   if(moduloXSrc<moduloXDest) {
					   srcX=Double.toString(moduloXSrc);
					   destX=Double.toString(moduloXDest);
				   }
				   else if(moduloXSrc>moduloXDest) {
					   srcX=Double.toString(moduloXDest);
					   destX=Double.toString(moduloXSrc);
				   }
				   if(node.getKey()<edge.getDest()) {
					   str=""+srcX+","+destX+","+srcY+","+destY+","+"1";
					   hash.put(str, edge);
				   }
				   else if(node.getKey()>edge.getDest()) {
					   str=""+srcX+","+destX+","+srcY+","+destY+","+"-1";
					   hash.put(str, edge);
				   }
				}
			}
			return hash;
		}
		
	
}
}
