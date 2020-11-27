package ex2;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map.Entry;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import ex2.DWGraph_DS.edges_direction;
public class DWGraph_DSJsonDeserializer implements JsonDeserializer<DWGraph_DS>{
	@Override
	public DWGraph_DS deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2)throws JsonParseException {
		 
		 JsonObject jsonObjet=json.getAsJsonObject();
		 JsonObject map=jsonObjet.get("map").getAsJsonObject();
		 JsonObject nodes_edges=jsonObjet.get("nodes_edges").getAsJsonObject();
		 int MC=jsonObjet.get("MC").getAsInt();
		 int e_size=jsonObjet.get("e_size").getAsInt();
		 HashMap<Integer,node_data> map_= new HashMap<Integer,node_data>();
		 HashMap<Integer,edge_data>edges=new HashMap<Integer,edge_data>();
		 HashMap<Integer,edges_direction> nodes_edges_= new HashMap<Integer,edges_direction>();
		 int dest;


		 for(Entry<String,JsonElement> set: map.entrySet()) {
			 JsonElement jsonValueElement=set.getValue();
			 int key=jsonValueElement.getAsJsonObject().get("key").getAsInt();
			 String info=jsonValueElement.getAsJsonObject().get("info").getAsString();
			 int tag=jsonValueElement.getAsJsonObject().get("tag").getAsInt();
			 double weight=jsonValueElement.getAsJsonObject().get("weight").getAsDouble();
			 double x=jsonValueElement.getAsJsonObject().get("location").getAsJsonObject().get("_x").getAsDouble();
			 double y=jsonValueElement.getAsJsonObject().get("location").getAsJsonObject().get("_y").getAsDouble();
			 double z=jsonValueElement.getAsJsonObject().get("location").getAsJsonObject().get("_z").getAsDouble();
			 node_data toAdd=new DWGraph_DS.NodeData(key,info,tag,weight,x,y,z);
			 map_.put(key, toAdd);	
		 }
		 for(Entry<String,JsonElement> set: nodes_edges.entrySet()) {
			 JsonElement jsonValueElement=set.getValue();
			 String key=set.getKey();
			 JsonObject edgesNi=jsonValueElement.getAsJsonObject().get("edgesNi").getAsJsonObject();
			 for(Entry<String,JsonElement> set_1: edgesNi.entrySet()) {
				 JsonElement jsonValueElement_1=set_1.getValue();
				 int src=jsonValueElement_1.getAsJsonObject().get("src").getAsInt();
				 dest=jsonValueElement_1.getAsJsonObject().get("dest").getAsInt();
				 String info=jsonValueElement_1.getAsJsonObject().get("info").getAsString();
				 int tag=jsonValueElement_1.getAsJsonObject().get("tag").getAsInt();
				 double weight=jsonValueElement_1.getAsJsonObject().get("weight").getAsDouble();
				 edge_data toAdd_= new DWGraph_DS.EdgeData(src,dest,weight,info,tag);
				 edges.put(dest, toAdd_);
			 }
			 edges_direction toAdd= new edges_direction(edges);
			 nodes_edges_.put(Integer.parseInt(key), toAdd);
			 edges=new HashMap<Integer,edge_data>();
		 }
		
		 DWGraph_DS ss=new DWGraph_DS(map_, nodes_edges_,MC,e_size);
		 return ss;
	}
}
