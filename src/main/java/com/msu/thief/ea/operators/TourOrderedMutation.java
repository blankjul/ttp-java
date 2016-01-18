package com.msu.thief.ea.operators;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.interfaces.IMutation;
import com.msu.moo.util.MyRandom;
import com.msu.moo.util.Pair;
import com.msu.thief.ea.RecombinationUtil;
import com.msu.thief.problems.variable.Tour;

public class TourOrderedMutation implements IMutation<Tour> {

	@Override
	public void mutate(Tour a, MyRandom rand) {

		Pair<Integer, Integer> bounds = RecombinationUtil.calcBounds(rand, 1, a.numOfCities());

		final int span = bounds.second - bounds.first;
		final int position = rand.nextInt(1, a.numOfCities() - span);

		swap(a, bounds.first, bounds.second, position);

	}

	public void swap(Tour t, int begin, int end, int position) {
		List<Integer> l = t.decode();
		List<Integer> section = new ArrayList<>(l.subList(begin, end));
		l.removeAll(section);
		l.addAll(position, section);
	}

}
