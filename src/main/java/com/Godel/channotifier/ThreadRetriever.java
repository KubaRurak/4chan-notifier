package com.Godel.channotifier;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ThreadRetriever {

	public List<Integer> retrieveThreadNumbers(String jsonStr) throws Exception {
		List<Integer> threadNumbers = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(jsonStr);

		for (JsonNode pageNode : jsonNode) {
			JsonNode threadsNode = pageNode.get("threads");
			for (JsonNode threadNode : threadsNode) {
				int threadNumber = threadNode.get("no").asInt();
				threadNumbers.add(threadNumber);
			}
		}
		return threadNumbers;
	}
}
