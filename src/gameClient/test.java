package gameClient;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server_Ex2;
import api.DWGraph_DS;
import api.edge_data;
import api.game_service;

public class test {
	public static void main(String[] args) throws JSONException {
		int scenario_num =11;
		game_service game = Game_Server_Ex2.getServer(scenario_num);
		//System.out.println(game);
		System.out.println(game.getPokemons());
		//System.out.println(game.getGraph());
		//System.out.println(game.getAgents());
	
		edge_data a= new DWGraph_DS.EdgeData(4, 5, 6, "g", 199);
		edge_data b= new DWGraph_DS.EdgeData(4, 8, 6, "g", 100);
		PriorityQueue<edge_data> priQ= new PriorityQueue<edge_data>();
		priQ.add(a);
		a.setTag(9);
		priQ.add(b);
		edge_data p=priQ.poll();
		System.out.println("src "+p.getSrc()+"dest "+p.getDest());
		
		
		
}
}
