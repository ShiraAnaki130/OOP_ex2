package gameClient;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server_Ex2;
import api.DWGraph_Algo;
import api.DWGraph_DS;
import api.directed_weighted_graph;
import api.dw_graph_algorithms;
import api.edge_data;
import api.game_service;
import api.node_data;
import gameClient.util.Point3D;

public class test {
	public static void main(String[] args) throws JSONException {
		int scenario_num =11;
		game_service game = Game_Server_Ex2.getServer(scenario_num);
		//System.out.println(game);
		System.out.println(game.getPokemons());
		//String g=game.getGraph();
		//System.out.println(game.getAgents());
//		dw_graph_algorithms algo = new DWGraph_Algo();
//		try {
//			algo.load(g);
//		} 
//		catch (JSONException e1) {
//				e1.printStackTrace();
//		}
//		directed_weighted_graph graph = algo.getGraph();
//		for(node_data n:graph.getV()) {
//			System.out.println("k "+n.getKey());
//			for(edge_data e: graph.getE(n.getKey())) {
//				System.out.println("e "+e.getDest());
//			}
//			
//		}
		
//	
//		Point3D p1= new Point3D(2,3,4);
//		Point3D p2= new Point3D(5,7,1);
//		CL_Pokemon a= new CL_Pokemon(p1,1,45);
//		
//		CL_Pokemon b= new CL_Pokemon(p2,1,78);
//		PriorityQueue<CL_Pokemon> priQ= new PriorityQueue<CL_Pokemon>();
//		priQ.add(b);
//		priQ.add(a);
//		//a.setTag(9);
//	
//		CL_Pokemon p=priQ.poll();
//		System.out.println();
//		System.out.println("p "+ p.getValue());
//		HashMap<edge_data,Double> hash= new HashMap<edge_data,Double>();
//		hash.put(a, 5.5);
//		hash.put(b, 770.0);
//		System.out.println(hash.get(a));
//		
//		String g="11ss";
//		try {
//		int r=Integer.parseInt(g);
//		System.out.println(r);
//		} catch(NumberFormatException e) {
//			e.printStackTrace();
//		}
		String g="222";
		if(g==null) System.out.println("yesss");
		
		
		
}
}
