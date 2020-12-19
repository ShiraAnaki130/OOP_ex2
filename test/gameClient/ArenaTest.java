package gameClient;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import api.DWGraph_Algo;
import api.DWGraph_DS;
import api.directed_weighted_graph;
import api.node_data;


public class ArenaTest {
	private Arena arena=new Arena();
	private directed_weighted_graph toInit = new DWGraph_DS();
	@BeforeEach
	void setUp() {
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
	  
	  }	      
	@Test
	void getGraph() {
		arena.setGraph(toInit);
		directed_weighted_graph fromArena=arena.getGraph();
		assertEquals(toInit,fromArena);
	}
	@Test
	void getAgents() {
		List<CL_Agent> fromArena=null;
		String file="data/agentTest";
		fromArena=arena.getAgents(file, toInit);
		CL_Agent agent=new CL_Agent(toInit,2);
		agent.setID(0);
		assertEquals(agent,fromArena.get(0));
	}

}
