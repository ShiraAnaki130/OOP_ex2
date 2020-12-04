package gameClient;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Range;
import gameClient.util.Range2D;


public class MyPanel extends JPanel {


	private Arena _ar;
	private gameClient.util.Range2Range _w2f;
	
	public MyPanel()
	{
		this.setBackground(Color.white);
	}
	public void update(Arena ar) {
		this._ar = ar;
		updatePanel();
	}
	private void updatePanel() {
		Range rx = new Range(20,this.getWidth()-20);
		Range ry = new Range(this.getHeight()-10,150);
		Range2D frame = new Range2D(rx,ry);
		directed_weighted_graph g = _ar.getGraph();
		_w2f = Arena.w2f(g,frame);
	
	}

	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		directed_weighted_graph gg = _ar.getGraph();
		for (node_data node : gg.getV()) 
		{
			g.setColor(Color.black);
			drawNode(node,5,g);
			for(edge_data edge:gg.getE(node.getKey())) {
				g.setColor(Color.gray);
				node_data n=gg.getNode(edge.getDest());
				drawNode(n,5,g);
				drawEdge(edge, g);
				}
			}
		
		
		}
	private void drawNode(node_data n, int r, Graphics g) {
		geo_location pos = n.getLocation();
		geo_location fp = this._w2f.world2frame(pos);
		g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
		g.drawString(""+n.getKey(), (int)fp.x(), (int)fp.y()-4*r);
	}
	private void drawEdge(edge_data e, Graphics g) {
		directed_weighted_graph gg = _ar.getGraph();
		geo_location s = gg.getNode(e.getSrc()).getLocation();
		geo_location d = gg.getNode(e.getDest()).getLocation();
		geo_location s0 = this._w2f.world2frame(s);
		geo_location d0 = this._w2f.world2frame(d);
		g.drawLine((int)s0.x(), (int)s0.y(), (int)d0.x(), (int)d0.y());
	}
}
