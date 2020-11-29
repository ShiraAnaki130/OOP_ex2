package ex2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DWGraph_AlgoTest {
private DWGraph_Algo graph_algo;

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
	
}
