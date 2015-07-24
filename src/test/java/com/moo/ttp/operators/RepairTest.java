package com.moo.ttp.operators;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.moo.ttp.factory.ThiefFactory;
import com.moo.ttp.problems.TravellingThiefProblem;



public class RepairTest extends TestCase {

	private TravellingThiefProblem ttp;
	
	@Before
    public void setUp() {
		ttp = ThiefFactory.create(10, 1);
		
    }
	
	@Test
	public void testRepairRemove() {
		Boolean[] b = new Boolean[] {true, true, true, true, true, true, true, true, true, true};
		RepairPickingPlan.repair(ttp, b);
		//assertTrue(ttp.getWeightWithoutDropping(b) <= ttp.getMaxWeight());
	}
	

}
