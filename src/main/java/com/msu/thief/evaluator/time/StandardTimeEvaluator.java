package com.msu.thief.evaluator.time;

import java.util.ArrayList;
import java.util.List;

import com.msu.thief.evaluator.TourInformation;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;

/**
 * This implementation provides a standard time calculator for the traveling
 * thief problem.
 * It is implemented according the definition of Bonyadi.
 *
 */
public class StandardTimeEvaluator extends TimeEvaluator {

	@Override
	public TourInformation evaluate_(AbstractThiefProblem problem, Tour tour, Pack pack) {

		List<Double> speedAtCities = new ArrayList<>();
		List<Double> timeAtCities = new ArrayList<>();
		List<Double> weightAtCities = new ArrayList<>();
		
		
		double time = 0;
		double weight = 0;

		ItemCollection<Item> items = problem.getItemCollection();
		List<Integer> pi = tour.encode();
		double speed = problem.getMaxSpeed();

		// iterate over all possible cities
		for (int i = 0; i < pi.size(); i++) {

			// for each item index this city
			for (Integer index : items.getItemsFromCityByIndex(pi.get(i))) {

				// if we pick that item
				if (pack.isPicked(index)) {

					Item item = items.get(index);

					// update the current weight
					weight += item.getWeight();

					double speedDiff = problem.getMaxSpeed() - problem.getMinSpeed();
					speed = problem.getMaxSpeed() - weight * speedDiff / problem.getMaxWeight();

					// if we are lower than minimum it is just the minimum
					// if this is the case the weight is larger than the
					// maxWeight!
					speed = Math.max(speed, problem.getMinSpeed());

				}

			}

			// add all the properties for the analysis
			speedAtCities.add(speed);
			weightAtCities.add(weight);
			timeAtCities.add(time);

			// increase time - do not forget the way from the last city to the
			// first!
			time += (problem.getMap().get(pi.get(i), pi.get((i + 1) % pi.size())) / speed);

		}

		return new TourInformation(problem, new TTPVariable(tour, pack), speedAtCities, timeAtCities, weightAtCities,
				time);
	}

}
