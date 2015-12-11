package com.msu.thief.evolving;

import java.io.ByteArrayOutputStream;

import com.msu.interfaces.IVariable;
import com.msu.model.variables.Variable;
import com.msu.thief.io.writer.JsonThiefProblemWriter;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.util.Util;

public class ThiefProblemVariable extends Variable<AbstractThiefProblem> {

	public ThiefProblemVariable(AbstractThiefProblem obj) {
		super(obj);
	}

	@Override
	public IVariable copy() {
		return new ThiefProblemVariable((AbstractThiefProblem) Util.cloneObject(obj));
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((obj == null) ? 0 : obj.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		ThiefProblemVariable o2 = (ThiefProblemVariable) obj;
		ByteArrayOutputStream bao1 = new ByteArrayOutputStream();
		new JsonThiefProblemWriter().write(this.obj, bao1);
		
		ByteArrayOutputStream bao2 = new ByteArrayOutputStream();
		new JsonThiefProblemWriter().write(o2.obj, bao2);
		
		return bao1.toString().equals(bao2.toString());
	}

	


}
