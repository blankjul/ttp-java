package com.moo.ttp.json;

import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.moo.ttp.Study;
import com.moo.ttp.experiment.Experiment;
import com.moo.ttp.jmetal.jISolution;

public class JsonBuilder {

	public static JsonArray toJson(List<jISolution> l) {
		JsonArray res = new JsonArray();
		for (int i = 0; i < 2; i++) {
			JsonArray obj = new JsonArray();
			for (jISolution s : l) {
				obj.add(new JsonPrimitive(s.getObjective(i)));
			}
			res.add(obj);
		}
		return res;
	}
	
	public static <T> JsonArray createStringJson(List<T> l) {
		JsonArray a = new JsonArray();
		for(Object entry : l) a.add(new JsonPrimitive(entry.toString()));
		return a;
	}
	
	public static JsonObject toJson(Study s) {
		JsonObject res = new JsonObject();
		res.add("problems", createStringJson(s.problems));
		
		JsonArray runs = new JsonArray();
		for (int i = 0; i < s.NUM_OF_RUNS; i++) {
			runs.add(new JsonPrimitive(i));
		}
		res.add("runs", runs);
		res.add("date", new JsonPrimitive((System.currentTimeMillis() / 1000L)));
		res.add("algorithms", createStringJson(s.configureAlgorithmList(s.problems.get(0))));
		res.add("indicators", createStringJson(Arrays.asList(Experiment.INDICATORS)));
		return res;
	}

}
