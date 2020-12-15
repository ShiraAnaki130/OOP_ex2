package gameClient;
import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import api.game_service;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class MyPanel extends JPanel {
    private Arena _ar;
    private gameClient.util.Range2Range _w2f;
    private static long start_time;
    private ImageIcon _board;
    private List<ImageIcon> Title;
    private List<ImageIcon> _pokemon;
    private List<ImageIcon> _pokeboll;


    public MyPanel() {
    	super();
    	this.Title = new LinkedList<>();
    	this._pokemon = new LinkedList<>();
    	this._pokeboll = new LinkedList<>();
    	try {
            this._board = new ImageIcon("pikachuP.png");
            ImageIcon pokeboll1 = new ImageIcon("pokeboll1.png");//title
            ImageIcon pokeboll2 = new ImageIcon("pokeboll2.png");//ID 0
            ImageIcon pokeboll3 = new ImageIcon("pokeboll3.png");//ID 1
            ImageIcon pokeboll4 = new ImageIcon("pokeboll4.png");//ID 2
            ImageIcon pokeboll5 = new ImageIcon("pokeboll5.png");//ID 3
            Title.add(pokeboll1);
            _pokeboll.add(pokeboll2);
            _pokeboll.add(pokeboll3);
            _pokeboll.add(pokeboll4);
            _pokeboll.add(pokeboll5);
            ImageIcon pokemon1 = new ImageIcon("pokemon1.png");//title
            ImageIcon pokemon2 = new ImageIcon("pokemon2.png");// -1 v<10
            ImageIcon pokemon3 = new ImageIcon("pokemon3.png");// 1 v<10
            ImageIcon pokemon4 = new ImageIcon("pokemon4.png");// -1 v>10
            ImageIcon pokemon5 = new ImageIcon("pokemon5.png");// 1<10
            Title.add(pokemon1);
            _pokemon.add(pokemon2);
            _pokemon.add(pokemon3);
            _pokemon.add(pokemon4);
            _pokemon.add(pokemon5);
        }
    	catch (Exception e){
    	    e.printStackTrace();
        }

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
        setSize(w, h);
        g.setColor(Color.ORANGE);
        g.fillRect(0,0,w,h);
        g.drawImage(Title.get(0).getImage(),450,120,this);
        g.drawImage(_board.getImage(),300,-30,this);
        paintComponent(g);
    }

    @Override
    public void paintComponent(Graphics g) {
        drowgraph(g);
        drawPokemon(g);
        drawAgants(g);
        drawInfo(g);
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
        //g.setColor(Color.pink);
        //g.fillRect(this.getWidth()-260,10,200,agent.size()*50);
        int r = 15;
        for (CL_Agent ag : agent) {
            Font font = g.getFont().deriveFont(20.0f);
            g.setFont(font);
            g.setColor(Color.red);
            String agent_info = "ID: "+ag.getID()+"    grade: "+ag.getValue();
            g.drawString(agent_info, this.getWidth()-250,40+agent.indexOf(ag)*50);
            g.drawImage(_pokeboll.get(ag.getID()).getImage(),this.getWidth()-300,20+agent.indexOf(ag)*50,this);
            geo_location c = ag.getLocation();
            if (c != null) {
                font = g.getFont().deriveFont(10.0f);
                g.setFont(font);
                geo_location fp = this._w2f.world2frame(c);
                g.drawImage(_pokeboll.get(ag.getID()).getImage(),(int) fp.x() -r,(int) fp.y()-r,this);
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
            int r = 15;
            if (c != null) {
                geo_location fp = this._w2f.world2frame(c);
                if(f.getType()<0&&f.getValue()<10){
                    g.drawImage(_pokemon.get(0).getImage(),(int)fp.x()-r,(int) fp.y()-r,this);
                }
                if(f.getType()>0&&f.getValue()<10){
                    g.drawImage(_pokemon.get(1).getImage(),(int)fp.x()-r,(int) fp.y()-r,this);
                }
                if(f.getType()<0&&f.getValue()>10){
                    g.drawImage(_pokemon.get(2).getImage(),(int)fp.x()-r,(int) fp.y()-r,this);
                }
                if(f.getType()>0&&f.getValue()>10){
                    g.drawImage(_pokemon.get(3).getImage(),(int)fp.x()-r,(int) fp.y()-r,this);
                }
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
        Font font =new Font("Forte", Font.BOLD, 55);
    }
        
    private void drawInfo(Graphics g){
        long current_time = this._ar.getGame().timeToEnd();
        String time = "Time to end: "+(current_time/1000);
        int game_scenario = this._ar.getScenario();
        String scenario = "game scenario: "+game_scenario;
        g.setColor(Color.darkGray);
        Font font = g.getFont().deriveFont(30.0f);
        g.setFont(font);
        g.drawString(scenario,40,30);
        font = g.getFont().deriveFont(20.0f);
        g.setFont(font);
        g.drawString(time,45,60);

    }
}


