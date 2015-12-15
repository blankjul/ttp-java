package com.msu.thief.variable.pack.factory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.msu.interfaces.IProblem;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;
import com.msu.util.Util;

public class EndOfTourPackingListFactory extends APackingListFactory {

	@Override
	public PackingList<?> next(IProblem p, MyRandom rand) {

		ThiefProblemWithFixedTour fixed = (ThiefProblemWithFixedTour) p;
		Tour<?> tour = fixed.getTour();

		Set<Integer> items = new HashSet<>();

		// search for a city in the end with items.
		while (items.isEmpty()) {

			double x = rand.nextDouble();
			double exp = 1 - Math.exp(-8 * x);
			int cityIdx = (int) (fixed.numOfCities() * exp);
			int city = tour.encode().get(cityIdx);

			ItemCollection<Item> c = fixed.getProblem().getItemCollection();
			items = c.getItemsFromCityByIndex(city);

		}

		int itemIdx = rand.select(new ArrayList<>(items));

		List<Boolean> l = Util.createListWithDefault(fixed.numOfItems(), false);
		l.set(itemIdx, true);

		return new BooleanPackingList(l);
	}

}
