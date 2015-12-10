package com.msu.thief.evaluator;

import com.msu.thief.problems.ThiefProblem;

public class ItemInformation {
	
		protected double pickupTime;
		protected double timeInKnapsack;
		protected double intialValue;
		protected double finalValue;
		
		
		public ItemInformation(ThiefProblem problem, TourInformation information, int idx, double intialValue, double finalValue) {
			this.pickupTime = getPickupTimeOfItem(problem, information, idx);
			this.timeInKnapsack = calcTimeOfItemInKnapsack(problem, information, idx);
			this.intialValue = intialValue;
			this.finalValue = finalValue;
		}
		
		
		public ItemInformation(double pickupTime, double timeInKnapsack, double intialValue, double finalValue) {
			this.pickupTime = pickupTime;
			this.timeInKnapsack = timeInKnapsack;
			this.intialValue = intialValue;
			this.finalValue = finalValue;
		}
		
		public double getPickupTime() {
			return pickupTime;
		}
		
		public double getTimeInKnapsack() {
			return timeInKnapsack;
		}
		
		public double getIntialValue() {
			return intialValue;
		}
		
		public double getFinalValue() {
			return finalValue;
		}
	
		
		public static double getPickupTimeOfItem(ThiefProblem problem, TourInformation information, int idx) {
			int cityOfItem = problem.getItemCollection().getCityOfItem(idx);
			return information.getTimeAtCity(cityOfItem);
		}

		public static double calcTimeOfItemInKnapsack(ThiefProblem problem, TourInformation information, int idx) {
			return information.getTime() - getPickupTimeOfItem(problem, information, idx);
		}
		
		
		
}
