package com.msu.algorithms;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.msu.knp.KnapsackProblem;
import com.msu.knp.model.Item;
import com.msu.knp.model.PackingList;
import com.msu.moo.model.Evaluator;
import com.msu.moo.util.Pair;
import com.msu.scenarios.AKNPScenario;
import com.msu.scenarios.knp.KNP_13_0020_1000_1;
import com.msu.scenarios.knp.KNP_13_0200_1000_1;
import com.msu.scenarios.knp.KNP_13_1000_1000_1;
import com.msu.scenarios.knp.KNP_13_2000_1000_1;

public class ComboTest {
	
	@Test
	public void testCorrectResult() {
		List<Item> items = new ArrayList<>();
		items.add(new Item(100, 3));
		items.add(new Item(200, 3));
		items.add(new Item(300, 3));
		items.add(new Item(400, 3));
		
		KnapsackProblem problem = new KnapsackProblem(10, items);
		
		PackingList<?> pl = Combo.getPackingList(new Evaluator<KnapsackProblem>(problem));
		assertEquals(Arrays.asList(false, true, true, true), pl.encode());
	}
	
	
	@Test
	public void testKNPScenarios() {
		
		for(AKNPScenario scenario : new AKNPScenario[] {new KNP_13_0200_1000_1(),new KNP_13_0020_1000_1(),new KNP_13_1000_1000_1(),new KNP_13_2000_1000_1(), }) {
			Pair<List<Item>, Integer> pair = scenario.getObject();
			KnapsackProblem problem = new KnapsackProblem(pair.second, pair.first);
			
			Evaluator<KnapsackProblem> eval = new Evaluator<KnapsackProblem>(problem);
			PackingList<?> pl = Combo.getPackingList(eval);
			
			Double value =  eval.evaluate(pl).getObjectives(0);
			assertEquals(eval.evaluate(scenario.getOptimal()).getObjectives(0),value);
			
		}
	}
	

}
