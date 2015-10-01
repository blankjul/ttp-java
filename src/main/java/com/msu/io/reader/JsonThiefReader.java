package com.msu.io.reader;

import java.io.BufferedReader;
import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import com.msu.io.AProblemReader;
import com.msu.io.pojo.PlainObjectThiefProblem;
import com.msu.thief.ThiefProblem;

public class JsonThiefReader extends AProblemReader<ThiefProblem> {

	@Override
	protected ThiefProblem read_(BufferedReader br) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		PlainObjectThiefProblem problem = mapper.readValue(br, PlainObjectThiefProblem.class);
		return problem.create();

	}

}
