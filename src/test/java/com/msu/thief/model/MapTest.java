package com.msu.thief.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.msu.thief.model.Map;



public class MapTest {

	private Map m;
	
	@Before
    public void setUp() {
        m = new Map(10);
    }
	
	@Test
	public void testInitValuesAreZero() {
		assertEquals(0.0, m.get(5, 6), 0.01);
	}

	@Test
	public void testSettingSymmetricValuesEqual() {
		m.set(5, 6, 8);
		assertEquals(8.0, m.get(5, 6) , 0.01);
		assertEquals(8.0, m.get(6, 5), 0.01);
	}
	
	
}
