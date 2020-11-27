package ex2;

public class testt {
	public static void main(String[] args) {
		final String FILE_NAME="check.json";
		directed_weighted_graph graph=new DWGraph_DS();
		node_data node;
		for(int i=0;i<6;i++) {
			node=new DWGraph_DS.NodeData(i);
			graph.addNode(node);
		}
		
		
		graph.connect(1, 3, 6);
		graph.connect(1, 4, 7);
		graph.connect(2, 1, 5);
		graph.connect(2, 4, 3.5);
		graph.connect(3, 2, 7);
		graph.connect(4, 3, 4);
		graph.connect(4, 5, 12);
		graph.connect(5, 2, 10);
		dw_graph_algorithms algo=new DWGraph_Algo();
		algo.init(graph);
		algo.save(FILE_NAME);
		directed_weighted_graph graph2=new DWGraph_DS();
		for(int i=0;i<2;i++) {
			node=new DWGraph_DS.NodeData(i);
			graph2.addNode(node);
		}
		graph2.connect(6,7,7);
		graph2.connect(7,6,89);
		algo.init(graph2);
		algo.load(FILE_NAME);
		for(node_data n:algo.getGraph().getV()) {
			System.out.println("key: "+n.getKey());
			for(edge_data k:algo.getGraph().getE(n.getKey())) {
				System.out.println("ni "+ k.getDest());
				System.out.println("ni "+ k.getWeight());
			}
		}
		
		
		
	}
	

}
