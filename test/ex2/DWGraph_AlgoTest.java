package ex2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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
	toInit.connect(nodes[3].getKey(),nodes[2].getKey(),Math.random()*10);
	toInit.connect(nodes[4].getKey(),nodes[5].getKey(),Math.random()*10);
	toInit.connect(nodes[5].getKey(),nodes[4].getKey(),Math.random()*10);
	toInit.connect(nodes[5].getKey(),nodes[6].getKey(),Math.random()*10);
	toInit.connect(nodes[6].getKey(),nodes[5].getKey(),Math.random()*10);
	toInit.connect(nodes[3].getKey(),nodes[7].getKey(),Math.random()*10);
	toInit.connect(nodes[7].getKey(),nodes[4].getKey(),Math.random()*10);
	toInit.connect(nodes[7].getKey(),nodes[6].getKey(),Math.random()*10);
	boolean b = graph_algo.isConnected();
	assertEquals(true,b);
}
@Test
void isConnected() {
	graph_algo=new DWGraph_Algo();
	boolean bol=graph_algo.isConnected();
	assertEquals(bol, true);
	directed_weighted_graph toInit=new DWGraph_DS();
	node_data node;
	for(int i=0;i<8;i++) {
		node=new DWGraph_DS.NodeData();
		toInit.addNode(node);
	}
	toInit.connect(1, 2, 1);
	toInit.connect(1, 3, 4);
	toInit.connect(3, 1, 3.5);
	graph_algo.init(toInit);
	bol=graph_algo.isConnected();
	assertEquals(bol, false);
	graph_algo.getGraph().removeEdge(1, 3);
	graph_algo.getGraph().removeEdge(3, 1);
	toInit.connect(2, 3, 0.5);
	toInit.connect(3, 4, 2);
	toInit.connect(4, 3, 4);
	toInit.connect(4, 8, 5);
	toInit.connect(8, 4, 6);
	toInit.connect(8, 7, 3);
	toInit.connect(7, 6, 6);
	toInit.connect(6, 7, 4);
	toInit.connect(3, 7, 11.5);
	toInit.connect(2, 6, 10);
	toInit.connect(2, 5, 8);
	toInit.connect(5, 1, 8);
	toInit.connect(5, 6, 7);
	toInit.connect(6, 5, 2.5);
	graph_algo.init(toInit);
	bol=graph_algo.isConnected();
	assertEquals(bol, true);
	graph_algo.getGraph().removeEdge(2, 5);
	bol=graph_algo.isConnected();
	assertEquals(bol, true);
	graph_algo.getGraph().connect(2, 5, 8);
	graph_algo.getGraph().removeEdge(6, 5);
	bol=graph_algo.isConnected();
	assertEquals(bol, false);
	directed_weighted_graph toInit_2=new DWGraph_DS();
	for(int i=1;i<=3;i++) {
		node=new DWGraph_DS.NodeData(i);
		toInit_2.addNode(node);
	}
	toInit.connect(1, 2, 1);
	toInit.connect(1, 3, 4);
	toInit.connect(3, 1, 3.5);
	graph_algo.init(toInit_2);
	bol=graph_algo.isConnected();
	assertEquals(bol, true);
	graph_algo.getGraph().removeEdge(1, 3);
	graph_algo.getGraph().removeEdge(1, 2);
	graph_algo.getGraph().connect(2, 1, 6);
	node=new DWGraph_DS.NodeData(4);
	graph_algo.getGraph().addNode(node);
	graph_algo.getGraph().connect(3, 4, 7.9);
	bol=graph_algo.isConnected();
	assertEquals(bol, false);
	graph_algo.getGraph().connect(1, 2, 6);
	graph_algo.getGraph().connect(1, 3, 3.5);
	graph_algo.getGraph().connect(4, 3, 7.9);
	bol=graph_algo.isConnected();
	assertEquals(bol, true);
	
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
