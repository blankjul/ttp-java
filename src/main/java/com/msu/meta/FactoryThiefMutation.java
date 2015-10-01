package com.msu.meta;

import java.util.Arrays;

import com.msu.io.pojo.PlainObjectItem;
import com.msu.io.pojo.PlainObjectThiefProblem;
import com.msu.moo.operators.AbstractMutation;
import com.msu.moo.util.Random;

public class FactoryThiefMutation extends AbstractMutation<PlainObjectThiefProblem> {

	@Override
	protected void mutate_(PlainObjectThiefProblem a) {
		Random rnd = Random.getInstance();

		for (int i = 0; i < a.cities.size(); i++) {
			if (rnd.nextDouble() < 0.1) {
				a.cities.set(i, Arrays.asList(rnd.nextDouble(0, 1000), rnd.nextDouble(0, 1000)));
			}
		}
		a.maxWeight *= rnd.nextDouble(0.1, 5);

		for (int i = 0; i < a.items.size(); i++) {
			if (rnd.nextDouble() < 0.05) {
				a.items.set(i, new PlainObjectItem(i, rnd.nextInt(1, 1000), rnd.nextInt(1, 1000)));
			}

		}
	}

}
