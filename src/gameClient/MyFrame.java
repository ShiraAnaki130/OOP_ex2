package gameClient;

import api.directed_weighted_graph;
import gameClient.util.Range;
import gameClient.util.Range2D;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class represents a very simple GUI class to present a
 * game on a graph - you are welcome to use this class - yet keep in mind
 * that the code is not well written in order to force you improve the
 * code and not to take it "as is".
 *
 */
public class MyFrame extends JFrame {
	private int _ind;
	private Arena _ar;
	private gameClient.util.Range2Range _w2f;
	private MyPanel panel;

	MyFrame(String a) {
		super(a);
		_ind = 0;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void update(Arena ar) {
		this._ar = ar;
		panel = new MyPanel();
		this.add(panel);
		updateFrame();
	}
	private void updateFrame() {
		Range rx = new Range(20, this.getWidth() - 20);
		Range ry = new Range(this.getHeight() - 10, 150);
		Range2D frame = new Range2D(rx, ry);
		directed_weighted_graph g = _ar.getGraph();
		_w2f = Arena.w2f(g, frame);
		panel.update(_ar);
		panel.repaint();
	}

	public void paint(Graphics g) {
		updateFrame();
		drawInfo(g);
	}

	private void drawInfo(Graphics g) {
		List<String> str = _ar.get_info();
		String dt = "none";
		for(int i=0;i<str.size();i++) {
			g.drawString(str.get(i)+" dt: "+dt,100,60+i*20);
		}
		
	}
}