package com.msu.algorithms;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.msu.knp.KnapsackProblem;
import com.msu.knp.model.Item;
import com.msu.knp.model.PackingList;
import com.msu.moo.model.Evaluator;

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

}
