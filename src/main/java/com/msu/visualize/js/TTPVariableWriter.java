package com.msu.visualize.js;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.msu.io.AWriter;
import com.msu.knp.model.Item;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Pair;
import com.msu.thief.ThiefProblem;
import com.msu.thief.model.CoordinateMap;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.variable.TTPVariable;

public class TTPVariableWriter extends AWriter<Pair<ThiefProblem, NonDominatedSolutionSet>> {

	public TTPVariableWriter() {
		super();
	}

	protected void add(ThiefProblem problem, TTPVariable var, JsonGenerator json, boolean addNodes) throws IOException {
		CoordinateMap map = (CoordinateMap) problem.getMap();

		int counter = 0;
		if (addNodes) {
			json.writeArrayFieldStart("nodes");
			for (Point2D p : map.getCities()) {
				json.writeStartObject();
				json.writeObjectField("id", String.format("n%s", counter));
				json.writeObjectField("label", String.format("%s", counter));
				json.writeObjectField("x", (int) p.getX());
				json.writeObjectField("y", (int) p.getY());
				json.writeObjectField("size", 2);
				json.writeEndObject();
				++counter;
			}
			json.writeEndArray();
		}

		if (var != null) {
			

			json.writeArrayFieldStart("edges");
			List<Point2D> cities = ((CoordinateMap) problem.getMap()).getCities();
			List<Integer> tour = var.getTour().encode();
			counter = 0;
			for (int i = 0; i < tour.size(); i++) {
				json.writeStartObject();
				json.writeObjectField("id", String.format("e%s", counter));
				json.writeObjectField("source", String.format("n%s", tour.get(i)));
				json.writeObjectField("target", String.format("n%s", tour.get((i + 1) % cities.size())));

				json.writeObjectField("type", "curve");
				json.writeObjectField("size", "5");

				json.writeEndObject();
				++counter;
			}
			json.writeEndArray();
			
			
			// check if item is picked at city or not
			json.writeArrayFieldStart("picks");
			ItemCollection<Item> collection = problem.getItemCollection();
			List<Boolean> packing = var.getPackingList().encode();
			
			for (int i = 0; i < problem.numOfCities(); i++) {
				Set<Integer> l = collection.getItemsFromCityByIndex(i);
				boolean noItem = true;
				for (Integer index : l) {
					if (packing.get(index) == true) {
						noItem = false;
						break;
					}
				}
				if (!noItem) {
					json.writeString("n" + i);
				}
				
			}
			json.writeEndArray();
			
			json.writeObjectField("variable", var.toString());
			
			
		}

	}

	@Override
	protected void write_(Pair<ThiefProblem, NonDominatedSolutionSet> obj, OutputStream os) throws IOException {

		ThiefProblem problem = obj.first;
		NonDominatedSolutionSet set = obj.second;
		JsonGenerator json = new JsonFactory().createGenerator(os, JsonEncoding.UTF8).useDefaultPrettyPrinter();

		json.writeStartObject();

		json.writeObjectFieldStart("graph");
		add(problem, null, json, true);
		json.writeEndObject();

		int counter = 0;
		for (Solution s : set.getSolutions()) {
			json.writeObjectFieldStart(String.valueOf(counter++));
			TTPVariable var = (TTPVariable) s.getVariable();
			add(problem, var, json, false);
			json.writeEndObject();
		}

		json.writeEndObject();

		json.close();

	}


}
