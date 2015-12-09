package com.msu.thief.algorithms.fixed.topdown;

public class HeuristicTree {

	// ! root of the tree
	protected HeuristicNode root = null;

	
	public HeuristicTree(HeuristicNode root) {
		this.root = root;
	}


	public HeuristicNode getRoot() {
		return root;
	}

	public void setRoot(HeuristicNode root) {
		this.root = root;
	}
	
	

}
