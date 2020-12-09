package gameClient;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server_Ex2;
import api.game_service;

public class test {
	public static void main(String[] args) throws JSONException {
		int scenario_num =11;
		game_service game = Game_Server_Ex2.getServer(scenario_num);
		//System.out.println(game);
		System.out.println(game.getPokemons());
		//System.out.println(game.getGraph());
		//System.out.println(game.getAgents());
		double r=35.189541903742466;
		System.out.println(r);
		
}
}
