import org.junit.jupiter.api.Assertions;
import ex2.*;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Vector;

public class DWGraph_DSTest  {
    directed_weighted_graph g_test;

    @Test
    void getNode(){
        g_test = new DWGraph_DS();
        node_data n1 = new DWGraph_DS.NodeData() ;
        node_data n2 = new DWGraph_DS.NodeData() ;
        node_data n3 = new DWGraph_DS.NodeData() ;
        node_data n4 = new DWGraph_DS.NodeData() ;
        g_test.addNode(n1);
        g_test.addNode(n2);
        g_test.addNode(n3);
        Assertions.assertEquals(n1,g_test.getNode(n1.getKey()));
        Assertions.assertEquals(n2,g_test.getNode(n2.getKey()));
        Assertions.assertEquals(n3,g_test.getNode(n3.getKey()));
        Assertions.assertEquals(null,g_test.getNode(n4.getKey()));
    }
    @Test
    void getEdge() {
        g_test = new DWGraph_DS();
        node_data n1 = new DWGraph_DS.NodeData() ;
        node_data n2 = new DWGraph_DS.NodeData() ;
        int key1 = n1.getKey();
        int key2 = n2.getKey();
        g_test.addNode(n1);
        g_test.addNode(n2);
        Assertions.assertEquals(true,g_test.getEdge(key1,key2)==null);
        g_test.connect(key1,key2,3.2);
        Assertions.assertEquals(true,g_test.getEdge(key1,key2)!=null);
    }

    @Test
    void addNode() {
        g_test = new DWGraph_DS();
        for(int i =0;i<10;i++){
            node_data n = new DWGraph_DS.NodeData() ;
            g_test.addNode(n);
            Assertions.assertEquals(true,g_test.getV().contains(n));
        }
        node_data nn = new DWGraph_DS.NodeData();
        Assertions.assertEquals(false,g_test.getV().contains(nn.getKey()));
    }

    @Test
    void connect() {
        g_test = new DWGraph_DS();
        int[] keys = new int[10];
        for(int i =0;i<10;i++){
            node_data n = new DWGraph_DS.NodeData() ;
            keys[i] = n.getKey();
            g_test.addNode(n);
        }
        g_test.connect(keys[0],keys[5],1.6);
        Assertions.assertEquals(true,g_test.getEdge(keys[0],keys[5])!=null);
        Assertions.assertEquals(false,g_test.getEdge(keys[5],keys[0])!=null);
        g_test.connect(keys[1],keys[6],3.1);
        Assertions.assertEquals(true,g_test.getEdge(keys[1],keys[6])!=null);
        Assertions.assertEquals(false,g_test.getEdge(keys[6],keys[1])!=null);
        g_test.connect(keys[2],keys[7],5.2);
        Assertions.assertEquals(true,g_test.getEdge(keys[2],keys[7])!=null);
        Assertions.assertEquals(false,g_test.getEdge(keys[7],keys[2])!=null);
        g_test.connect(keys[3],keys[8],4.6);
        Assertions.assertEquals(true,g_test.getEdge(keys[3],keys[8])!=null);
        Assertions.assertEquals(false,g_test.getEdge(keys[8],keys[3])!=null);
    }

    @Test
    void getV() {
        g_test = new DWGraph_DS();
        Collection<node_data> keys = new Vector<>();
        for(int i =0;i<10;i++){
            node_data n = new DWGraph_DS.NodeData() ;
            keys.add(n);
            g_test.addNode(n);
        }
        Collection<node_data> vertex = g_test.getV();
        for(node_data node: vertex){
            Assertions.assertEquals(true,keys.contains(node));;
        }
    }

    @Test
    void getE() {
        g_test = new DWGraph_DS();
        int[] keys = new int[5];
        for(int i =0;i<5;i++){
            node_data n = new DWGraph_DS.NodeData() ;
            keys[i] = n.getKey();
            g_test.addNode(n);
        }
        g_test.connect(keys[0],keys[1],1.2);
        g_test.connect(keys[0],keys[2],3.1);
        g_test.connect(keys[0],keys[3],3.5);
        g_test.connect(keys[4],keys[0],4.5);
        Collection<edge_data> edges_0= g_test.getE(keys[0]);
        for(int i =1 ;i<=3;i++){
            Assertions.assertEquals(true,edges_0.contains(g_test.getEdge(keys[0],keys[i])));
        }
        Assertions.assertEquals(false,edges_0.contains(g_test.getEdge(keys[4],keys[0])));
    }

    @Test
     void removeNode() {
        g_test = new DWGraph_DS();
        int[] keys = new int[5];
        for(int i =0;i<5;i++){
            node_data n = new DWGraph_DS.NodeData() ;
            keys[i] = n.getKey();
            g_test.addNode(n);
        }
        g_test.connect(keys[0],keys[1],1.2);
        g_test.connect(keys[0],keys[2],3.1);
        g_test.connect(keys[0],keys[3],3.5);
        g_test.connect(keys[4],keys[0],4.5);
        g_test.connect(keys[4],keys[1],3.5);
        g_test.connect(keys[2],keys[4],3.4);
        g_test.connect(keys[2],keys[1],1.4);
        g_test.removeNode(keys[0]);
        Assertions.assertEquals(true,g_test.getNode(keys[0])==null);
        Assertions.assertEquals(true,g_test.getE(keys[0])==null);
        Assertions.assertEquals(true,g_test.getE(keys[4]).size()==1);
        g_test.removeNode(keys[4]);
        Assertions.assertEquals(true,g_test.getNode(keys[4])==null);
        Assertions.assertEquals(true,g_test.getE(keys[4])==null);
        Assertions.assertEquals(true,g_test.getE(keys[2]).size()==1);
    }

    @Test
   void removeEdge() {
        g_test = new DWGraph_DS();
        int[] keys = new int[5];
        for(int i =0;i<5;i++){
            node_data n = new DWGraph_DS.NodeData() ;
            keys[i] = n.getKey();
            g_test.addNode(n);
        }
        g_test.connect(keys[0],keys[1],1.2);
        g_test.connect(keys[0],keys[2],3.1);
        g_test.connect(keys[0],keys[3],3.5);
        g_test.connect(keys[4],keys[0],4.5);
        g_test.connect(keys[4],keys[1],3.5);
        g_test.connect(keys[2],keys[4],3.4);
        g_test.connect(keys[2],keys[1],1.4);
        g_test.removeEdge(keys[0],keys[1]);
        Assertions.assertEquals(null,g_test.getEdge(keys[0],keys[1]));
        g_test.removeEdge(keys[0],keys[2]);
        Assertions.assertEquals(null,g_test.getEdge(keys[0],keys[2]));
    }

    @Test
    void nodeSize() {
        g_test = new DWGraph_DS();
        int[] keys = new int[10];
        for(int i =0;i<10;i++){
            node_data n = new DWGraph_DS.NodeData() ;
            keys[i] = n.getKey();
            g_test.addNode(n);
        }
        Assertions.assertEquals(10,g_test.nodeSize());
        g_test.removeNode(keys[0]);
        g_test.removeNode(keys[1]);
        Assertions.assertEquals(8,g_test.nodeSize());
        node_data n1 = new DWGraph_DS.NodeData();
        g_test.addNode(n1);
        Assertions.assertEquals(9,g_test.nodeSize());
        g_test.addNode(n1);
        Assertions.assertEquals(9,g_test.nodeSize());
    }

    @Test
    void edgeSize() {
        g_test = new DWGraph_DS();
        int[] keys = new int[10];
        for(int i =0;i<10;i++){
            node_data n = new DWGraph_DS.NodeData() ;
            keys[i] = n.getKey();
            g_test.addNode(n);
        }
        g_test.connect(keys[0],keys[1],1.2);
        g_test.connect(keys[0],keys[2],3.1);
        g_test.connect(keys[0],keys[3],3.5);
        g_test.connect(keys[4],keys[0],4.5);
        g_test.connect(keys[4],keys[1],3.5);
        g_test.connect(keys[2],keys[4],3.4);
        g_test.connect(keys[2],keys[1],1.4);
        Assertions.assertEquals(7,g_test.edgeSize());
        g_test.connect(keys[0],keys[2],3.1);
        Assertions.assertEquals(7,g_test.edgeSize());
        g_test.removeNode(keys[0]);
        Assertions.assertEquals(3,g_test.edgeSize());
        g_test.removeNode(keys[4]);
        Assertions.assertEquals(1,g_test.edgeSize());
    }

}
