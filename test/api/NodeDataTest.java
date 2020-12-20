package api;
import api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This JUnit test case is for the  of class NodeData on api's folder.
 */
import org.junit.jupiter.api.Test;

public class NodeDataTest {
	private node_data node=new DWGraph_DS.NodeData(1,2,3,4);
	@Test
	public void getKey() {
		assertEquals(node.getKey(), 1);
	}
	@Test
	public void getLocation() {
		geo_location pos=new DWGraph_DS.GeoLocation(2,3,4);
		assertEquals(node.getLocation(), pos);
	}
	@Test
	public void getWeight() {
		assertEquals(node.getWeight(), 0.0);
	}
	@Test
	public void getInfo() {
		boolean bol= node.getInfo().equals("f");
		assertTrue(bol);
	}
	@Test
	public void getTag() {
		assertEquals(node.getTag(),Integer.MAX_VALUE);
	}


}
