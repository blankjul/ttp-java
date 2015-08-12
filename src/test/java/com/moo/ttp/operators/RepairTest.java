package com.moo.ttp.operators;

import org.junit.Before;
import org.junit.Test;

import com.moo.ttp.factory.ItemFactory;
import com.moo.ttp.factory.ThiefFactory;
import com.moo.ttp.problems.ttp.TravellingThiefProblem;

import junit.framework.TestCase;



public class RepairTest extends TestCase {

	private TravellingThiefProblem ttp;
	
	@Before
    public void setUp() {
		ttp = ThiefFactory.create(10, 1, ItemFactory.TYPE.WEAKLY_CORRELATED, 0.6);
		
    }
	
	@Test
	public void testRepairRemove() {
		Boolean[] b = new Boolean[] {true, true, true, true, true, true, true, true, true, true};
		RepairPickingPlan.repair(ttp, b);
		//assertTrue(ttp.getWeightWithoutDropping(b) <= ttp.getMaxWeight());
	}
	

}
