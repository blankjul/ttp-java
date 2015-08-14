package com.moo.operators.crossover;

/**
 * Abstract class for having a specific point where the crossover is executed.
 */
public abstract class PointCrossover<T> extends AbstractCrossover<T> {

	// ! point where the crossover is executed
	protected Integer point = null;

	
	public PointCrossover() {
		super();
	}

	public PointCrossover(Integer point) {
		super();
		this.point = point;
	}

	/**
	 * @return crossover point. If null it is defined random!
	 */
	public Integer getPoint() {
		return point;
	}

	/**
	 * Set any point that is larger than zero! If the point should
	 * be to large it is set randomly.
	 * 
	 * @param point integer value
	 */
	public void setPoint(Integer point) {
		if (point > 0 ) this.point = point;
	}
	
	

}
