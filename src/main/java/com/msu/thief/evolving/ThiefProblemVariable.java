package com.msu.thief.evolving;

import java.io.ByteArrayOutputStream;

import com.msu.interfaces.IVariable;
import com.msu.model.variables.Variable;
import com.msu.thief.io.writer.JsonThiefProblemWriter;
import com.msu.thief.problems.ThiefProblem;
import com.msu.util.Util;

public class ThiefProblemVariable extends Variable<ThiefProblem> {

	public ThiefProblemVariable(ThiefProblem obj) {
		super(obj);
	}

	@Override
	public IVariable copy() {
		return new ThiefProblemVariable((ThiefProblem) Util.cloneObject(obj));
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
