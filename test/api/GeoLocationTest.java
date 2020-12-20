package api;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GeoLocationTest {
	private geo_location pos= new DWGraph_DS.GeoLocation(2, 5, 0);

	@Test
	public void getX() {
		assertEquals(pos.x(), 2);
	}
	@Test
	public void getY() {
		assertEquals(pos.y(), 5);
	}
	@Test
	public void getZ() {
		assertEquals(pos.z(), 0);
	}
	@Test
	public void distance() {
		geo_location pos2= new DWGraph_DS.GeoLocation(3, 6, 1);
		double num=Math.sqrt(3);
		assertEquals(pos.distance(pos2), num);
	}


}
