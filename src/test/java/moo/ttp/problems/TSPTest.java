package moo.ttp.problems;

import junit.framework.TestCase;
import moo.ttp.model.Map;

import org.junit.Test;


public class TSPTest extends TestCase {

	@Test
	public void testEvaluateFunction() {
		Map m = new Map(3);
		m.set(0, 1, 1);
		m.set(1, 2, 2);
		m.set(2, 0, 3);
		TravellingSalesmanProblem tsp = new TravellingSalesmanProblem(m);
		assertEquals(6, tsp.evaluate(new Integer[]{0,1,2}));
	}
	

}
