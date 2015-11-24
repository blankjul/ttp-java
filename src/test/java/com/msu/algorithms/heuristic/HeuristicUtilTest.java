package com.msu.algorithms.heuristic;

import java.util.List;

import org.junit.Test;

import com.msu.model.Evaluator;
import com.msu.thief.algorithms.heuristic.HeuristicUtil;
import com.msu.thief.io.thief.reader.JsonThiefProblemReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.variable.tour.StandardTour;
import com.msu.thief.variable.tour.Tour;

import junit.framework.TestCase;

public class HeuristicUtilTest extends TestCase {

	SingleObjectiveThiefProblem problem;
	Tour<?> tour;

	@Override
	protected void setUp() throws Exception {
		problem = (SingleObjectiveThiefProblem) new JsonThiefProblemReader()
				.read("resources/my_publication_coordinates.ttp");
		tour = new StandardTour("[0,2,1,3]");
	}

	@Test
	public void testDeltaObjective() {
		problem.setToMultiObjective(true);
		List<Double> deltaObjectives = HeuristicUtil.calcDeltaObjectives(problem, new Evaluator(Integer.MAX_VALUE),
				tour, 3);
		assertEquals(0.92, deltaObjectives.get(0), 0.01);
		assertEquals(-problem.getItems().get(3).getProfit(), deltaObjectives.get(1), 0.01);
	}

	@Test
	public void testDeltaSingleObjective() {
		List<Double> deltaObjectives = HeuristicUtil.calcDeltaObjectives(problem, new Evaluator(Integer.MAX_VALUE),
				tour, 3);
		assertEquals(-23.59, deltaObjectives.get(0), 0.01);
	}

	@Test
	public void testCalcDeltaVelocity() {
		assertEquals(0.23625, HeuristicUtil.calcDeltaVelocity(problem, 3), 0.01);
	}
	
	
	@Test
	public void testCalcDeltaTime() {
		problem.setToMultiObjective(true);
		List<Double> deltaObjectives = HeuristicUtil.calcDeltaObjectives(problem, new Evaluator(Integer.MAX_VALUE),
				tour, 3);
		double deltaTime = HeuristicUtil.calcDeltaTime(problem, tour, 3);
		assertEquals(deltaTime, deltaObjectives.get(0), 0.01);
	}
	

	@Test
	public void testCalcDeltaTimeWorstCase() {
		double deltaTime = HeuristicUtil.calcDeltaTime(problem, tour, 3, 0.3365);
		assertEquals(21, deltaTime, 0.01);
	}
	
	@Test
	public void testDeltaSingleObjectiveFast() {
		List<Double> deltaObjectives = HeuristicUtil.calcDeltaObjectives(problem, new Evaluator(Integer.MAX_VALUE),
				tour, 3);
		double deltaTime = HeuristicUtil.calcDeltaTime(problem, tour, 3);
		double deltaProfit = problem.getItems().get(3).getProfit();
		double delta = HeuristicUtil.calcDeltaSingleObjective(problem, deltaTime, deltaProfit);
		assertEquals(delta, deltaObjectives.get(0), 0.01);
	}
	
	

}
