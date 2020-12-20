package gameClient;
import api.*;
import gameClient.util.Point3D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CL_AgentTest {
    private directed_weighted_graph _g;
    @Test
    public void sdt(){
        _g = new DWGraph_DS();
        node_data n1 = new DWGraph_DS.NodeData();
        node_data n2 = new DWGraph_DS.NodeData();
        node_data n3 = new DWGraph_DS.NodeData();
        geo_location g1 = new DWGraph_DS.GeoLocation(3,5,0);
        geo_location g2 = new DWGraph_DS.GeoLocation(1,2,0);
        geo_location g3 = new DWGraph_DS.GeoLocation(6,2,0);
        n1.setLocation(g1);
        n2.setLocation(g2);
        n3.setLocation(g3);
        _g.addNode(n1);
        _g.addNode(n2);
        _g.addNode(n3);
        _g.connect(n1.getKey(),n2.getKey(),2.3);
        _g.connect(n2.getKey(),n3.getKey(),6.2);
        _g.connect(n3.getKey(),n1.getKey(),4.3);
        CL_Agent a = new CL_Agent(_g, n1.getKey());
        Point3D point = new Point3D(4,2,0);
        CL_Pokemon p1 = new CL_Pokemon(point,1,5);
        p1.set_edge(_g.getEdge(1,2));
        a.set_curr_edge(_g.getEdge(n1.getKey(),n2.getKey()));
        a.set_curr_fruit(p1);
        a.setSpeed(1);
        a.set_SDT(10);
        double de = g1.distance(g2);
        double dist = point.distance(a.getLocation());
        double norm = (dist/de);
        double dt = (norm*2.3)/a.getSpeed();
        long ans = (long) (10*dt);
        Assertions.assertEquals(ans,a.get_sg_dt());
        a.setSpeed(20);
        a.set_SDT(10);
        ans = a.get_sg_dt();
        Assertions.assertEquals(true,ans==1);
        a.setSpeed(30);
        a.set_SDT(10);
        ans = a.get_sg_dt();
        Assertions.assertEquals(true,ans==0);
    }
}