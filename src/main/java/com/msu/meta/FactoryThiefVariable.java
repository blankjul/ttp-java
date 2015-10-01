package com.msu.meta;

import com.msu.io.pojo.PlainObjectThiefProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.AVariable;
import com.msu.util.ThiefUtil;

public class FactoryThiefVariable extends AVariable<PlainObjectThiefProblem> {

	public FactoryThiefVariable(PlainObjectThiefProblem obj) {
		super(obj);
	}

	@Override
	public IVariable copy() {
		return new FactoryThiefVariable((PlainObjectThiefProblem) ThiefUtil.cloneObject(obj));
	}
	


}
