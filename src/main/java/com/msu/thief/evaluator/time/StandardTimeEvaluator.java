package com.msu.thief.evaluator.time;

import java.util.List;

import com.msu.problems.SalesmanProblem;
import com.msu.problems.ThiefProblem;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.Pair;

public class StandardTimeEvaluator extends TimeEvaluator {

	
	
	public StandardTimeEvaluator(ThiefProblem problem) {
		super(problem);
	}


	@Override
	public Double evaluate_(Pair<Tour<?>, PackingList<?>> input) {
		
		ItemCollection<Item> items = problem.getItemCollection();
		
		List<Integer> pi = input.first.encode();
		List<Boolean> b = input.second.encode();
		
		// if no item is picked the tsp tour calculator could be used!
		if (!b.contains(true)) {
			return new SalesmanProblem(problem.getMap()).evaluate(pi) * problem.getMaxSpeed();
		} 
		
		
		

		double speed = problem.getMaxSpeed();
		// iterate over all possible cities
		for (int i = 0; i < pi.size(); i++) {

			// for each item index this city
			for (Integer index : items.getItemsFromCityByIndex(pi.get(i))) {

				// if we pick that item
				if (b.get(index)) {

					Item item = items.get(index);
					
					// update the current weight
					weight += item.getWeight();

					double speedDiff = problem.getMaxSpeed() - problem.getMinSpeed();
					speed = problem.getMaxSpeed() - weight * speedDiff / problem.getMaxWeight();

					// if we are lower than minimum it is just the minimum
					// if this is the case the weight is larger than the
					// maxWeight!
					speed = Math.max(speed, problem.getMinSpeed());
	
					// save the picking time!
					mItem.put(item, time);

				}
			}

			// do not forget the way from the last city to the first!
			time += (problem.getMap().get(pi.get(i), pi.get((i + 1) % pi.size()) ) / speed);

		}
		
		// calculate the time of each item on the tour!
		for (Item i : mItem.keySet()) {
			double pointOfTime = mItem.get(i);
			mItem.put(i, time - pointOfTime);
		}
		
		return time;
	}

}
