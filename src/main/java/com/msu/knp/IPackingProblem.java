package com.msu.knp;

import java.util.List;

import com.msu.knp.model.Item;
import com.msu.moo.interfaces.IProblem;

public interface IPackingProblem  extends IProblem {
	
	public int numOfItems();
	
	public List<Item> getItems();
	
	public int getMaxWeight();
	
	
	
	
}
