package ex2;

import java.util.List;

public class DWGraph_Algo implements dw_graph_algorithms{
	private directed_weighted_graph graph;
	
	public DWGraph_Algo() {
		graph=new DWGraph_DS();
	}

	@Override
	public void init(directed_weighted_graph g) {
		graph=g;
		
	}

	@Override
	public directed_weighted_graph getGraph() {
		return graph;
	}

	@Override
	public directed_weighted_graph copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Shira
	 */

	@Override
	public boolean save(String file) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean load(String file) {
		// TODO Auto-generated method stub
		return false;
	}

}
