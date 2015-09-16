package com.msu.thief.scenarios;

public abstract class AScenario<T, V> {

	public abstract T getObject();

	public V getOptimal() {
		return null;
	}
}
