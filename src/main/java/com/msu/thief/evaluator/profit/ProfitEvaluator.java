package com.msu.thief.evaluator.profit;

import java.util.Map;

import com.msu.thief.evaluator.IEvaluator;
import com.msu.thief.model.Item;

/**
 * The ProfitCalculator provides an interface for calculating the profit. For
 * each item the time how long it was inside of the knapsack has to be provided.
 */
public abstract class ProfitEvaluator implements IEvaluator<Map<Item, Double>, Double> {
}
