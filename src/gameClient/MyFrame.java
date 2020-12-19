package gameClient;

import api.directed_weighted_graph;
import gameClient.util.Range;
import gameClient.util.Range2D;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class represents a GUI class for presenting a game on a graph.
 * This class uses 'MyPanel' class where the game is actually drawning.
 *  @author Lea.Shira;
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
	/** 
	 * This function updates arena's field.
	 * @param ar- the arena of the game.
	 */
	public void update(Arena ar) {
		this._ar = ar;
		panel = new MyPanel();
		this.add(panel);
		updateFrame();
	}
	/**
	 * This function updates the frame, and paint the pokemon game(from MyPanel class).
	 */
	private void updateFrame() {
		Range rx = new Range(20, this.getWidth() - 20);
		Range ry = new Range(this.getHeight() - 10, 150);
		Range2D frame = new Range2D(rx, ry);
		directed_weighted_graph g = _ar.getGraph();
		_w2f = Arena.w2f(g, frame);
		panel.update(_ar);
		panel.repaint();
	}
	/**
	 * This function uses the updateFrame function for painting the pokemon game.
	 */
	public void paint(Graphics g) {
		updateFrame();
	}

}