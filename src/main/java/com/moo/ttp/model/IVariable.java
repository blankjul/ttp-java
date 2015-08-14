package com.moo.ttp.model;

public interface IVariable<C, E> extends ICopyable<C> {

	public E encode();

	public C random(int length);

	public void set(Object obj);

	public Object get();

}