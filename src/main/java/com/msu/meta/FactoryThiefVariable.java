package com.msu.meta;

import java.io.ByteArrayOutputStream;

import com.msu.io.writer.JsonThiefProblemWriter;
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

	@Override
	public boolean isEqual(ThiefProblem o1, ThiefProblem o2) {
		ByteArrayOutputStream bao1 = new ByteArrayOutputStream();
		new JsonThiefProblemWriter().write(o1, bao1);
		
		ByteArrayOutputStream bao2 = new ByteArrayOutputStream();
		new JsonThiefProblemWriter().write(o2, bao2);
		
		return bao1.toString().equals(bao2.toString());
	}
	

	
	


}
