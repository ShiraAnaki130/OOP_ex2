package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DWGraph_AlgoTest {
private DWGraph_Algo graph_algo;
@Test
void copy(){
	graph_algo = new DWGraph_Algo();
	directed_weighted_graph toInit = new DWGraph_DS();
	node_data[] nodes = new node_data[10];
	for(int i =0;i<nodes.length;i++){
		node_data node = new DWGraph_DS.NodeData();
		nodes[i] = node;
		toInit.addNode(node);
	}
	for(int i = 0;i<nodes.length-2;i++){
		toInit.connect(nodes[i].getKey(),nodes[i+1].getKey(),Math.random()*10);
		toInit.connect(nodes[i].getKey(),nodes[i+2].getKey(),Math.random()*10);
	}
	graph_algo.init(toInit);
	directed_weighted_graph copy_graph = graph_algo.copy();
	assertEquals(toInit,copy_graph);
}

@Test
	void minGraphConnected(){
	graph_algo = new DWGraph_Algo();
	directed_weighted_graph toInit = new DWGraph_DS();
	node_data[] nodes = new node_data[8];
	for(int i =0;i<nodes.length;i++){
		node_data node = new DWGraph_DS.NodeData();
		nodes[i] = node;
		toInit.addNode(node);
	}
	toInit.connect(nodes[0].getKey(),nodes[1].getKey(),Math.random()*10);
	toInit.connect(nodes[0].getKey(),nodes[4].getKey(),Math.random()*10);
	toInit.connect(nodes[1].getKey(),nodes[2].getKey(),Math.random()*10);
	toInit.connect(nodes[1].getKey(),nodes[5].getKey(),Math.random()*10);
	toInit.connect(nodes[2].getKey(),nodes[5].getKey(),Math.random()*10);
	toInit.connect(nodes[2].getKey(),nodes[7].getKey(),Math.random()*10);
	toInit.connect(nodes[2].getKey(),nodes[3].getKey(),Math.random()*10);
	toInit.connect(nodes[3].getKey(),nodes[2].getKey(),Math.random()*10);
	toInit.connect(nodes[4].getKey(),nodes[5].getKey(),Math.random()*10);
	toInit.connect(nodes[5].getKey(),nodes[4].getKey(),Math.random()*10);
	toInit.connect(nodes[5].getKey(),nodes[6].getKey(),Math.random()*10);
	toInit.connect(nodes[6].getKey(),nodes[5].getKey(),Math.random()*10);
	toInit.connect(nodes[3].getKey(),nodes[7].getKey(),Math.random()*10);
	toInit.connect(nodes[7].getKey(),nodes[4].getKey(),Math.random()*10);
	toInit.connect(nodes[7].getKey(),nodes[6].getKey(),Math.random()*10);
	toInit.connect(nodes[6].getKey(),nodes[3].getKey(),Math.random()*10);
	toInit.connect(nodes[5].getKey(),nodes[0].getKey(),Math.random()*10);
	graph_algo.init(toInit);
	boolean b = graph_algo.isConnected();
	assertEquals(true,b);
}

	@Test
	void shortDist(){
		graph_algo = new DWGraph_Algo();
		directed_weighted_graph toInit = new DWGraph_DS();
		node_data[] nodes = new node_data[8];
		for(int i =0;i<nodes.length;i++){
			node_data node = new DWGraph_DS.NodeData();
			nodes[i] = node;
			toInit.addNode(node);
		}
		toInit.connect(nodes[0].getKey(),nodes[1].getKey(),1.2);
		toInit.connect(nodes[0].getKey(),nodes[4].getKey(),1.6);
		toInit.connect(nodes[1].getKey(),nodes[2].getKey(),1.8);
		toInit.connect(nodes[1].getKey(),nodes[5].getKey(),3.2);
		toInit.connect(nodes[2].getKey(),nodes[5].getKey(),6.4);
		toInit.connect(nodes[2].getKey(),nodes[7].getKey(),3.3);
		toInit.connect(nodes[2].getKey(),nodes[3].getKey(),3.8);
		toInit.connect(nodes[3].getKey(),nodes[2].getKey(),6.8);
		toInit.connect(nodes[4].getKey(),nodes[5].getKey(),4.1);
		toInit.connect(nodes[5].getKey(),nodes[4].getKey(),4.6);
		toInit.connect(nodes[5].getKey(),nodes[6].getKey(),1);
		toInit.connect(nodes[6].getKey(),nodes[5].getKey(),1.2);
		toInit.connect(nodes[3].getKey(),nodes[7].getKey(),3.8);
		toInit.connect(nodes[7].getKey(),nodes[4].getKey(),1.3);
		toInit.connect(nodes[7].getKey(),nodes[6].getKey(),5.6);
		toInit.connect(nodes[6].getKey(),nodes[3].getKey(),1.4);
		toInit.connect(nodes[5].getKey(),nodes[0].getKey(),2.6);
		graph_algo.init(toInit);
		double ans = graph_algo.shortestPathDist(nodes[0].getKey(),nodes[6].getKey());
		assertEquals(5.4,ans);
		toInit.removeEdge(nodes[1].getKey(),nodes[5].getKey());
		ans = graph_algo.shortestPathDist(nodes[0].getKey(),nodes[6].getKey());
		assertEquals(6.7,ans);
	}
	@Test
	void saveLoad(){
		graph_algo = new DWGraph_Algo();
		directed_weighted_graph toInit = new DWGraph_DS();
		node_data[] nodes = new node_data[10];
		for(int i =0;i<nodes.length;i++){
			node_data node = new DWGraph_DS.NodeData();
			nodes[i] = node;
			toInit.addNode(node);
		}
		for(int i = 0;i<nodes.length-2;i++){
			toInit.connect(nodes[i].getKey(),nodes[i+1].getKey(),Math.random()*10);
			toInit.connect(nodes[i].getKey(),nodes[i+2].getKey(),Math.random()*10);
		}
		graph_algo.init(toInit);
		graph_algo.save("graph.obj");
		dw_graph_algorithms Load = new DWGraph_Algo();
		directed_weighted_graph new_graph  = new DWGraph_DS();
		Load.init(new_graph);
		Load.load("graph.obj");
		assertEquals(toInit,Load.getGraph());
	}
}
