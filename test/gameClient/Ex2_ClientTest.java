package gameClient;
import api.*;
import gameClient.*;
import gameClient.util.Point3D;
import static org.junit.jupiter.api.Assertions.*;
import java.util.LinkedList;
import java.util.List;
/**
 * This JUnit test case is for the of class Ex2 on gameClient's folder.
 */

import org.junit.jupiter.api.Test;

class Ex2_ClientTest {
	private directed_weighted_graph _g;
	@Test
    public void nextNodeTest(){
        _g = new DWGraph_DS();
        node_data n1 = new DWGraph_DS.NodeData();
        node_data n2 = new DWGraph_DS.NodeData();
        node_data n3 = new DWGraph_DS.NodeData();
        node_data n4 = new DWGraph_DS.NodeData();
        node_data n5 = new DWGraph_DS.NodeData();
        node_data n6 = new DWGraph_DS.NodeData();
        geo_location g1 = new DWGraph_DS.GeoLocation(3,5,0);
        geo_location g2 = new DWGraph_DS.GeoLocation(1,2,0);
        geo_location g3 = new DWGraph_DS.GeoLocation(6,2,0);
        geo_location g4 = new DWGraph_DS.GeoLocation(4,7,0);
        geo_location g5 = new DWGraph_DS.GeoLocation(6,7,0);
        geo_location g6 = new DWGraph_DS.GeoLocation(3,3,0);
        n1.setLocation(g1);
        n2.setLocation(g2);
        n3.setLocation(g3);
        n4.setLocation(g4);
        n5.setLocation(g5);
        n6.setLocation(g6);
        _g.addNode(n1);
        _g.addNode(n2);
        _g.addNode(n3);
        _g.addNode(n4);
        _g.addNode(n5);
        _g.addNode(n6);
        CL_Agent a1 = new CL_Agent(_g, n1.getKey());
        CL_Agent a2 = new CL_Agent(_g, n2.getKey());
        CL_Agent a3 = new CL_Agent(_g,n3.getKey());
        _g.connect(n1.getKey(),n2.getKey(),2.3);
        _g.connect(n2.getKey(),n3.getKey(),6.2);
        _g.connect(n3.getKey(),n4.getKey(),4.3);
        _g.connect(n4.getKey(),n5.getKey(),2.3);
        _g.connect(n5.getKey(),n6.getKey(),1.2);
        _g.connect(n6.getKey(),n1.getKey(),5.3);
        _g.connect(n1.getKey(),n5.getKey(),7.3);
        _g.connect(n4.getKey(),n2.getKey(),1.2);
        _g.connect(n3.getKey(),n1.getKey(),5.7);
        Point3D point1 = new Point3D(2,3.5,0);
        CL_Pokemon p1 = new CL_Pokemon(point1,1,7);
        p1.set_edge(_g.getEdge(n1.getKey(),n2.getKey()));
        Point3D point2 = new Point3D(2,3.5,0);
        CL_Pokemon p2 = new CL_Pokemon(point2,1,5);
        p2.set_edge(_g.getEdge(n2.getKey(), n3.getKey()));
        Point3D point3 = new Point3D(4,13/3,0);
        CL_Pokemon p3 = new CL_Pokemon(point3,1,3);
        p3.set_edge(_g.getEdge(n5.getKey(),n6.getKey()));
        List<CL_Pokemon> po = new LinkedList<>();
        po.add(p1);
        po.add(p2);
        po.add(p3);
        int dest = Ex2_Client.nextNode(_g, a1.getSrcNode(),po);
        assertEquals(n2.getKey(),dest);
        po.remove(p1);
        p1.setInfo("t");
        po.add(p1);
        dest = Ex2_Client.nextNode(_g, a2.getSrcNode(),po);
        assertEquals(n3.getKey(),dest);
        po.remove(p2);
        p2.setInfo("t");
        po.add(p2);
        dest = Ex2_Client.nextNode(_g , a3.getSrcNode(),po);
        assertEquals(n6.getKey(),dest);
    }
}
