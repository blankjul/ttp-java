package com.msu.visualize.js;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.msu.knp.model.Item;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.ThiefProblem;
import com.msu.thief.model.CoordinateMap;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.variable.TTPVariable;

public class TTPVariableToJson {

	// ! string writer for getting the result
	protected StringWriter sw = null;

	// ! json generator object
	protected JsonGenerator json = null;;

	// ! problem instance
	protected ThiefProblem problem = null;

	public TTPVariableToJson(ThiefProblem problem) throws IOException {
		sw = new StringWriter();
		json = new JsonFactory().createGenerator(sw).useDefaultPrettyPrinter();
		this.problem = problem;
		
		json.writeStartObject();
		writeGraph(problem);
	}

	protected void writeGraph(ThiefProblem problem) throws IOException {
		CoordinateMap map = (CoordinateMap) problem.getMap();
		json.writeArrayFieldStart("nodes");
		int counter = 0;
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

	public void add(Solution s, String name) throws IOException {

		json.writeObjectFieldStart(name);
		
		TTPVariable var = (TTPVariable) s.getVariable();
		

		json.writeArrayFieldStart("edges");
		List<Point2D> cities = ((CoordinateMap) problem.getMap()).getCities();
		List<Integer> tour = var.getTour().encode();
		for (int i = 0; i < tour.size(); i++) {
			json.writeStartObject();
			json.writeObjectField("id", String.format("e%s", i));
			json.writeObjectField("source", String.format("n%s", tour.get(i)));
			json.writeObjectField("target", String.format("n%s", tour.get((i + 1) % cities.size())));

			json.writeObjectField("type", "curve");
			json.writeObjectField("size", "5");

			json.writeEndObject();
		}
		json.writeEndArray();

		// check if item is picked at city or not
		json.writeArrayFieldStart("picks");
		ItemCollection<Item> collection = problem.getItemCollection();
		List<Boolean> packing = var.getPackingList().encode();

		
		for (int i = 0; i < problem.numOfCities(); i++) {
			Set<Integer> l = collection.getItemsFromCityByIndex(i);
			boolean pickItem = false;
			for (Integer index : l) {
				if (packing.get(index) == true) {
					pickItem = true;
					break;
				}
			}
			json.writeObject(pickItem);

		}
		json.writeEndArray();

		json.writeObjectField("variable", var.toString());
		
		json.writeEndObject();

	}
	
	public String getResult() throws IOException {
		json.writeEndObject();
		json.close();
		return sw.toString();
	}


}
