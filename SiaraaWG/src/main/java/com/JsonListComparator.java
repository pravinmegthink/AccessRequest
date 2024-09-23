package com;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;

public class JsonListComparator {

	public static void main(String[] args) throws Exception {
		// File paths
		String filePath1 = "C:\\Users\\Pravin\\Desktop\\postman-json.json";
		String filePath2 = "C:\\Users\\Pravin\\Desktop\\rest-json.json";
		String matchFilePath = "C:\\Users\\Pravin\\Desktop\\match.json";
		String mismatchFilePath = "C:\\Users\\Pravin\\Desktop\\mismatch.json";

		// Create ObjectMapper instance
		ObjectMapper mapper = new ObjectMapper();

		// Read JSON from files
		JsonNode json1 = readJsonFromFile(filePath1, mapper);
		JsonNode json2 = readJsonFromFile(filePath2, mapper);

		// Compare JSON key-value pairs
		boolean isMatch = compareJsonKeyValuePairs(json1, json2);

		// Write results to files
		if (isMatch) {
			writeJsonToFile(json1, matchFilePath, mapper);
			System.out.println("JSON key-value pairs match. Written to: " + matchFilePath);
		} else {
			writeJsonToFile(json1, mismatchFilePath, mapper);
			System.out.println("JSON key-value pairs do not match. Written to: " + mismatchFilePath);
		}
	}

	public static JsonNode readJsonFromFile(String filePath, ObjectMapper mapper) throws IOException {
		// Read file content
		String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
		// Parse JSON content to JsonNode
		return mapper.readTree(jsonContent);
	}

	public static boolean compareJsonKeyValuePairs(JsonNode json1, JsonNode json2) {
		// Iterate through fields of the first JSON
		Iterator<Map.Entry<String, JsonNode>> fields1 = json1.fields();
		while (fields1.hasNext()) {
			Map.Entry<String, JsonNode> field1 = fields1.next();
			String key = field1.getKey();
			JsonNode value1 = field1.getValue();

			// Check if the key exists in the second JSON and compare values
			if (json2.has(key)) {
				JsonNode value2 = json2.get(key);
				if (!value1.equals(value2)) {
					return false; // Values do not match
				}
			} else {
				return false; // Key does not exist in second JSON
			}
		}
		return true; // All key-value pairs match
	}

	public static void writeJsonToFile(JsonNode jsonNode, String fileName, ObjectMapper mapper) throws IOException {
		// Write JsonNode to file
		mapper.writeValue(new File(fileName), jsonNode);
	}
}
