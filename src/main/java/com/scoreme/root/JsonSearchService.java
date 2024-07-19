package com.scoreme.root;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;

@Service
public class JsonSearchService {

    public String findFieldPath(JsonNode rootNode, String fieldPath) {
        StringBuilder path = new StringBuilder();
        String[] pathSegments = fieldPath.split("\\.");

        if (pathSegments.length == 0) {
            return "";
        }

        // Start searching from the root node
        JsonNode currentNode = rootNode;

        for (String segment : pathSegments) {
            if (currentNode == null) {
                break;
            }

            if (currentNode.isObject()) {
                if (currentNode.has(segment)) {
                    path.append(segment).append(":").append(currentNode.get(segment).getNodeType()).append("/");
                    currentNode = currentNode.get(segment);
                } else {
                    return ""; // Field not found
                }
            } else if (currentNode.isArray()) {
                try {
                    int index = Integer.parseInt(segment);
                    JsonNode arrayNode = currentNode.get(index);
                    path.append(segment).append(":").append(arrayNode.getNodeType()).append("/");
                    currentNode = arrayNode;
                } catch (NumberFormatException | NullPointerException | IndexOutOfBoundsException e) {
                    return ""; // Invalid index or node type
                }
            }
        }

        return path.toString();
    }
}
