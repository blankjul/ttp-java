package com.msu.io.writer;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import com.msu.io.AProblemWriter;
import com.msu.io.pojo.PlainObjectThiefProblem;
import com.msu.thief.ThiefProblem;

public class JsonThiefProblemWriter extends AProblemWriter<ThiefProblem>{


	@Override
	public void write(ThiefProblem obj, String path) {
		PlainObjectThiefProblem problem = new PlainObjectThiefProblem(obj);
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), problem);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
