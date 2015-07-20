package moo.ttp.model;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;



public class MapTest extends TestCase {

	private Map m;
	
	@Before
    public void setUp() {
        m = new Map(10);
    }
	
	@Test
	public void testInitValuesAreZero() {
		assertEquals(0.0, m.get(5, 6));
	}

	@Test
	public void testSettingSymmetricValuesEqual() {
		m.set(5, 6, 8);
		assertEquals(8.0, m.get(5, 6) );
		assertEquals(8.0, m.get(6, 5));
	}
	
	
}
