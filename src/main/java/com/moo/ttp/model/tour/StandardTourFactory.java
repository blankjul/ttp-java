package com.moo.ttp.model.tour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import com.msu.moo.model.interfaces.IFactory;

public class StandardTourFactory implements IFactory<Tour<?>> {

	//! length of the tour
	protected int length;
	
	
	
	public StandardTourFactory(int length) {
		super();
		this.length = length;
	}


	@Override
	public Tour<?> create() {
		LinkedList<Integer> indices = new LinkedList<Integer>();
		for (int i = 1; i < length; i++) {
			indices.add(i);
		}
		Collections.shuffle(indices);
		indices.addFirst(0);
		return new StandardTour(new ArrayList<>(indices));
	}

}
