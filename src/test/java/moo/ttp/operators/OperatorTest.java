package moo.ttp.operators;

import static org.junit.Assert.assertArrayEquals;
import junit.framework.TestCase;
import moo.ttp.util.Pair;

import org.junit.Before;
import org.junit.Test;



public class OperatorTest extends TestCase {

	private Integer[] p1;
	private Integer[] p2;
	
	@Before
    public void setUp() {
		p1 = new Integer[] {1,2,3,4};
		p2 = new Integer[] {4,3,2,1};
    }
	
	@Test
	public void testOnePointCrossover() {
		Pair<Integer[],Integer[]> result = OnePointCrossover.crossover(p1, p2, 1);
		assertArrayEquals(new Integer[] {1,3,2,1}, result.first);
		assertArrayEquals(new Integer[] {4,2,3,4}, result.second);
	}
	
	
	@Test
	public void testOnePointCrossoverNoPointNoError() {
		OnePointCrossover.crossover(p1, p2);
	}
	
	@Test
	public void testSwapMutation() {
		SwapMutation.mutate(p1, 0, 3);
		assertArrayEquals(new Integer[] {4,2,3,1}, p1);
	}

	
	@Test
	public void testPMXCrossover() {
		p1 = new Integer[] {5,7,1,3,6,4,2};
		p2 = new Integer[] {4,6,2,7,3,1,5};
		Pair<Integer[],Integer[]> result = PMXCrossover.crossover(p1, p2, 3);
		assertArrayEquals(new Integer[] {4,6,2,3,7,5,1}, result.first);
		assertArrayEquals(new Integer[] {5,7,1,6,3,2,4}, result.second);
	}
	
	
	
	@Test
	public void testPMXCrossoverSimilarParentsNoErrir() {
		p1 = new Integer[] {1,2,3,4,5};
		p2 = new Integer[] {2,1,3,4,5};
		Pair<Integer[],Integer[]> result = PMXCrossover.crossover(p1, p2, 3);
	}

	
}
