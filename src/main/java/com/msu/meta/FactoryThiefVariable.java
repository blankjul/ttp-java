package com.msu.meta;

import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.AVariable;
import com.msu.thief.ThiefProblem;
import com.msu.util.ThiefUtil;

public class FactoryThiefVariable extends AVariable<ThiefProblem> {

	public FactoryThiefVariable(ThiefProblem obj) {
		super(obj);
	}

	@Override
	public IVariable copy() {
		return new FactoryThiefVariable((ThiefProblem) ThiefUtil.cloneObject(obj));
	}
	


}
