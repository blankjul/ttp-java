package com.msu.thief.io.writer;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.msu.moo.util.io.AWriter;
import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;
import com.msu.thief.evaluator.profit.NoDroppingEvaluator;
import com.msu.thief.evaluator.time.StandardTimeEvaluator;
import com.msu.thief.model.CoordinateMap;
import com.msu.thief.model.Item;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.MultiObjectiveThiefProblem;
import com.msu.thief.problems.ProfitConstraintThiefProblem;
import com.msu.thief.problems.SingleObjectiveThiefProblem;

public class JsonThiefProblemWriter extends AWriter<AbstractThiefProblem> {

	
	@Override
	protected void write_(AbstractThiefProblem p, OutputStream os) throws IOException {
		JsonGenerator json = new JsonFactory().createGenerator(os, JsonEncoding.UTF8);
		
		DefaultPrettyPrinter pp = new DefaultPrettyPrinter();
		pp.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
		json.setPrettyPrinter(pp);
		
		json.writeStartObject();
		json.writeObjectField("name", p.getName());
		
		int model = -1;
		if (p instanceof SingleObjectiveThiefProblem) {
			model = 1;
			json.writeObjectField("problemType", "SingleObjective");
		} else if (p instanceof MultiObjectiveThiefProblem) {
			model = 2;
			json.writeObjectField("problemType", "MultiObjective");
		} else if (p instanceof ProfitConstraintThiefProblem) {
			model = 3;
			json.writeObjectField("problemType", "ProfitConstraint");
		}  else {
			throw new RuntimeException("Thief Problem not known!");
		}
		
		//json.writeObjectField("numOfCities", p.numOfCities());
		//json.writeObjectField("numOfItems", p.numOfItems());
		json.writeObjectField("maxWeight", p.getMaxWeight());
		if (model == 1) json.writeObjectField("R", ((SingleObjectiveThiefProblem)p).getR());
		else if (model == 3) json.writeObjectField("minProfitConstraint", ((ProfitConstraintThiefProblem)p).getMinProfitConstraint());
		json.writeObjectField("minSpeed", p.getMinSpeed());
		json.writeObjectField("maxSpeed", p.getMaxSpeed());
		
		json.writeObjectFieldStart("profitEvaluator");
		if (p.getProfitEvaluator() instanceof NoDroppingEvaluator) {
			json.writeObjectField("type", "NO_DROPPING");
		} else if (p.getProfitEvaluator() instanceof ExponentialProfitEvaluator) {
			json.writeObjectField("type", "EXPONTENTIAL");
			ExponentialProfitEvaluator epe = (ExponentialProfitEvaluator) p.getProfitEvaluator();
			json.writeObjectField("droppingConstant", epe.getDroppingConstant());
			json.writeObjectField("droppingRate", epe.getDroppingRate());
		} else {
			throw new RuntimeException("Evalator is not implemented to be written to a file!");
		}
		json.writeEndObject();
		
		json.writeObjectFieldStart("timeEvaluator");
		if (p.getTimeEvaluator() instanceof StandardTimeEvaluator) {
			json.writeObjectField("type", "STANDARD");
		} else {
			throw new RuntimeException("Evalator is not implemented to be written to a file!");
		}
		json.writeEndObject();
		
		
		SymmetricMap map = p.getMap();
		boolean isCoordinateMap = map instanceof CoordinateMap;
		String cityType = (isCoordinateMap) ? "XY_COORDINATES" : "FULL_MATRIX";
		json.writeObjectField("cityType",cityType);
		
		json.writeFieldName("cities");
		if (isCoordinateMap) {
			json.writeStartArray();
			List<Point2D> cityPoints = ((CoordinateMap) map).getCities();
			for(Point2D point : cityPoints) {
				json.writeStartArray();
				json.writeNumber(point.getX());
				json.writeNumber(point.getY());
				json.writeEndArray();
			}
			json.writeEndArray();
		} else {
			json.writeStartArray();
			for (int i = 0; i < map.getSize(); i++) {
				json.writeStartArray();;
				for (int j = 0; j < map.getSize(); j++) {
					json.writeNumber(map.get(i, j));
				}
				json.writeEndArray();
			}
			json.writeEndArray();
		}
		

		json.writeFieldName("items");
		json.writeStartArray();
		for (int i = 0; i < p.numOfCities(); i++) {
			for (Item item : p.getItemCollection().getItemsFromCity(i)) {
				json.writeStartObject();
				json.writeObjectField("city", i);
				json.writeObjectField("weight", item.getWeight());
				json.writeObjectField("value", item.getProfit());
				if (item.getDropping() != null) json.writeObjectField("dropping", item.getDropping());
				json.writeEndObject();
			}
		}
		json.writeEndArray();
		
		json.writeEndObject();
		json.close();
		
		os.close();
	}
	

}
