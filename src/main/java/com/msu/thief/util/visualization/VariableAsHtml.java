package com.msu.thief.util.visualization;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.model.CoordinateMap;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;

public class VariableAsHtml {

	public static void write(AbstractThiefProblem problem, Solution<TTPVariable> s, String pathToHtml) {

		StringWriter sw = new StringWriter();

		JsonGenerator json;
		try {
			json = new JsonFactory().createGenerator(sw).useDefaultPrettyPrinter();

			json.writeStartObject();

			// write the nodes
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


			if (s != null) {

				// write the edges
				json.writeArrayFieldStart("edges");
				TTPVariable var = s.getVariable();

				if (problem.getMap() instanceof CoordinateMap) {
					List<Point2D> cities = ((CoordinateMap) problem.getMap()).getCities();
					List<Integer> tour = var.getTour().decode();
					for (int i = 0; i < tour.size(); i++) {
						json.writeStartObject();
						json.writeObjectField("id", String.format("e%s", i));
						json.writeObjectField("source", String.format("n%s", tour.get(i)));
						json.writeObjectField("target", String.format("n%s", tour.get((i + 1) % cities.size())));
						json.writeObjectField("type", "arrow");
						json.writeObjectField("size", 2);

						json.writeEndObject();
					}
					json.writeEndArray();

					// check if item is picked at city or not
					json.writeArrayFieldStart("picks");
					ItemCollection<Item> collection = problem.getItemCollection();

					Pack packing = var.getPack();

					for (int i = 0; i < problem.numOfCities(); i++) {
						boolean pickItem = false;
						for (Integer index : collection.getItemsFromCityByIndex(i)) {
							if (packing.isPicked(index)) {
								pickItem = true;
								break;
							}
						}
						json.writeObject(pickItem);

					}
				}

				json.writeEndArray();
				json.writeObjectField("variable", s.toString());
			} else {
				json.writeArrayFieldStart("edges");
				json.writeEndArray();
				json.writeArrayFieldStart("picks");
				json.writeEndArray();
				json.writeObjectField("variable", "");
				
			}

			json.writeEndObject();
			json.close();

			PrintWriter writer = new PrintWriter(pathToHtml, "UTF-8");

			HashMap<String, Object> scopes = new HashMap<String, Object>();
			scopes.put("data", sw.toString());

			MustacheFactory mf = new DefaultMustacheFactory();
			Mustache mustache = mf.compile("resources/graph.html");
			mustache.execute(writer, scopes);
			writer.flush();
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
