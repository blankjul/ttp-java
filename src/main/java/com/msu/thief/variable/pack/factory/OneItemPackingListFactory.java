package com.msu.thief.variable.pack.factory;

import com.msu.interfaces.IProblem;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.thief.variable.pack.PackingList;
import com.msu.util.MyRandom;

public class OneItemPackingListFactory extends APackingPlanFactory {


	@Override
	public PackingList<?> next(IProblem p, MyRandom rand) {
		PackingList<?> pack = new EmptyPackingListFactory().next(p, rand);
		return (PackingList<?>) new BitFlipMutation().mutate(pack, p, rand);
	}



}
