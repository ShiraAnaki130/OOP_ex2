package gameClient;
import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import api.game_service;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import javax.swing.*;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;


public class MyPanel extends JPanel {
    private Arena _ar;
    private gameClient.util.Range2Range _w2f;
    private static long start_time;

    public MyPanel() {
    	super();
        start_time = new Date().getTime();
    }

    public void update(Arena ar) {
        this._ar = ar;
        updatePanel();
    }

    private void updatePanel() {
        Range rx = new Range(20, this.getWidth() - 20);
        Range ry = new Range(this.getHeight() - 30, 150);
        Range2D frame = new Range2D(rx, ry);
        directed_weighted_graph g = _ar.getGraph();
        _w2f = Arena.w2f(g, frame);
    }

    public void paint(Graphics g) {
        int w = this.getWidth();
        int h = this.getHeight();
        g.clearRect(0, 0, w, h);
        setSize(w,h);
        this.setBackground(Color.white);
        paintComponent(g);
    }

    @Override
    public void paintComponent(Graphics g) {
        drowgraph(g);
        drawPokemon(g);
        drawAgants(g);
        drawTime(g);
    }

    private void drowgraph(Graphics g) {
        directed_weighted_graph gg = _ar.getGraph();
        for (node_data node : gg.getV()) {
            g.setColor(Color.black);
            drawNode(node, 5, g);
            for (edge_data edge : gg.getE(node.getKey())) {
                g.setColor(Color.gray);
                drawEdge(edge, g);
            }
        };
    }

    private void drawAgants(Graphics g) {
        List<CL_Agent> agent = _ar.getAgents();
        g.setColor(Color.pink);
        g.fillRect(this.getWidth()-260,10,200,agent.size()*50);
        int r = 8;
        for (CL_Agent ag : agent) {
            Font font = g.getFont().deriveFont(20.0f);
            g.setFont(font);
            g.setColor(Color.red);
            String agent_info = "ID: "+ag.getID()+"    grade: "+ag.getValue();
            g.drawString(agent_info, this.getWidth()-250,40+agent.indexOf(ag)*50);
            geo_location c = ag.getLocation();
            if (c != null) {
                font = g.getFont().deriveFont(10.0f);
                g.setFont(font);
                geo_location fp = this._w2f.world2frame(c);
                g.fillOval((int) fp.x() - r, (int) fp.y() - r, 2 * r, 2 * r);
                String agents_s = "ID: "+ag.getID();
                g.drawString(agents_s, (int)fp.x()+r,(int) fp.y()-r);
            }
        }
    }

    private void drawNode(node_data n, int r, Graphics g) {
        geo_location pos = n.getLocation();
        geo_location fp = this._w2f.world2frame(pos);
        g.fillOval((int) fp.x() - r, (int) fp.y() - r, 2 * r, 2 * r);
        g.drawString("" + n.getKey(), (int) fp.x(), (int) fp.y() - 4 * r);
    }

    private void drawEdge(edge_data e, Graphics g) {
        directed_weighted_graph gg = _ar.getGraph();
        geo_location s = gg.getNode(e.getSrc()).getLocation();
        geo_location d = gg.getNode(e.getDest()).getLocation();
        geo_location s0 = this._w2f.world2frame(s);
        geo_location d0 = this._w2f.world2frame(d);
        g.drawLine((int) s0.x(), (int) s0.y(), (int) d0.x(), (int) d0.y());
    }

    private void drawPokemon(Graphics g) {
        List<CL_Pokemon> fs = _ar.getPokemons();
        for (CL_Pokemon f : fs) {
            Point3D c = f.getLocation();
            int r = 10;
            g.setColor(Color.green);
            if (f.getType() < 0) {
                g.setColor(Color.orange);
            }
            if (c != null) {
                geo_location fp = this._w2f.world2frame(c);
                g.fillOval((int) fp.x() - r, (int) fp.y() - r, 2 * r, 2 * r);
                String pokemon_s = "V:"+f.getValue();
                Font font = g.getFont().deriveFont(10.0f);
                g.setFont(font);
                g.drawString(pokemon_s, (int)fp.x()+r,(int) fp.y()-r);
            }
        }
    }
    private void drawTime(Graphics g){
        long current_time = new Date().getTime();
        int show_time = (int)(current_time - start_time)/1000 ;
        String time = "Time to end: "+(60-show_time)+" seconds";
        g.setColor(Color.BLUE);
        Font font = g.getFont().deriveFont(40.0f);
        g.setFont(font);
        g.drawString(time,10,60);
    }
}


