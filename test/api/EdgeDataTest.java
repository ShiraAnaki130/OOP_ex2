package api;

import static org.junit.jupiter.api.Assertions.*;
/**
 * This JUnit test case is for the  of class EdgeData on api's folder.
 */
import org.junit.jupiter.api.Test;

public class EdgeDataTest {
	private edge_data edge=new DWGraph_DS.EdgeData(2, 5, 1.88);

	@Test
	public void getSrc() {
		assertEquals(edge.getSrc(), 2);
	}
	@Test
	public void getDest() {
		assertEquals(edge.getDest(), 5);
	}
	@Test
	public void getWeight() {
		assertEquals(edge.getWeight(), 1.88);
	}
	@Test
	public void getInfo() {
		boolean bol= edge.getInfo().equals("f");
		assertTrue(bol);
	}
	@Test
	public void getTag() {
		assertEquals(edge.getTag(),0);
	}


}
