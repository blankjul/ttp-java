package com.msu.problems;

import java.util.List;

import com.msu.moo.interfaces.IProblem;
import com.msu.thief.model.Item;

public interface IPackingProblem  extends IProblem {
	
	public int numOfItems();
	
	public List<Item> getItems();
	
	public int getMaxWeight();
	
	
	
	
}
