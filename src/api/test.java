package api;

import org.json.JSONException;

public class test {
	public static void main(String[] args) throws JSONException {
				final String FILE_NAME="check.json";
				dw_graph_algorithms  graph_algo = new DWGraph_Algo();
				directed_weighted_graph toInit = new DWGraph_DS();
				node_data[] nodes = new node_data[8];
				for(int i =0;i<nodes.length;i++){
					node_data node = new DWGraph_DS.NodeData();
					nodes[i] = node;
					toInit.addNode(node);
				}
				toInit.connect(nodes[0].getKey(),nodes[1].getKey(),Math.random()*10);
				toInit.connect(nodes[4].getKey(),nodes[0].getKey(),Math.random()*10);
				toInit.connect(nodes[1].getKey(),nodes[2].getKey(),Math.random()*10);
				toInit.connect(nodes[1].getKey(),nodes[5].getKey(),Math.random()*10);
				toInit.connect(nodes[1].getKey(),nodes[4].getKey(),Math.random()*10);
				toInit.connect(nodes[2].getKey(),nodes[3].getKey(),Math.random()*10);
				toInit.connect(nodes[2].getKey(),nodes[6].getKey(),Math.random()*10);
				toInit.connect(nodes[3].getKey(),nodes[2].getKey(),Math.random()*10);
				toInit.connect(nodes[3].getKey(),nodes[7].getKey(),Math.random()*10);
				toInit.connect(nodes[7].getKey(),nodes[3].getKey(),Math.random()*10);
				toInit.connect(nodes[7].getKey(),nodes[6].getKey(),Math.random()*10);
				toInit.connect(nodes[6].getKey(),nodes[5].getKey(),Math.random()*10);
				toInit.connect(nodes[5].getKey(),nodes[6].getKey(),Math.random()*10);
				toInit.connect(nodes[5].getKey(),nodes[4].getKey(),Math.random()*10);
				toInit.connect(nodes[4].getKey(),nodes[5].getKey(),Math.random()*10);
				graph_algo.init(toInit);
				graph_algo.save("A1.json");
				for(node_data node_:graph_algo.getGraph().getV()) {
					System.out.println("id "+node_.getKey());
					System.out.println("pos "+node_.getLocation());
					for(edge_data edge:graph_algo.getGraph().getE(node_.getKey())) {
						System.out.println("src "+edge.getSrc());
						System.out.println("dest "+edge.getDest());
						System.out.println("w "+edge.getWeight());
					}
				}
	}
}
