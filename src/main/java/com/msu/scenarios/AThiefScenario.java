package com.msu.scenarios;

public abstract class AThiefScenario<T, V> {

	public abstract T getObject();

	public V getOptimal() {
		return null;
	}
}
