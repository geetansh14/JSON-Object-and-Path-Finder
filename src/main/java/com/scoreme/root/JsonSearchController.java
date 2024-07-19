package com.scoreme.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class JsonSearchController {

    private final JsonSearchService jsonSearchService;
    private static final Logger logger = LoggerFactory.getLogger(JsonSearchController.class);
    private final ObjectMapper objectMapper;

    @Autowired
    public JsonSearchController(JsonSearchService jsonSearchService, ObjectMapper objectMapper) {
        this.jsonSearchService = jsonSearchService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/api/json/search")
    public ResponseEntity<JsonResponse> findFieldPath(@RequestBody JsonRequest request) {
        logger.info("Received request: {}", request);
        try {
            String jsonContent = request.getJson();
            if (jsonContent == null || jsonContent.isEmpty()) {
                logger.error("Argument 'json' is null or empty");
                throw new IllegalArgumentException("Argument 'json' is null or empty");
            }

            JsonNode rootNode = objectMapper.readTree(jsonContent);
            String path = jsonSearchService.findFieldPath(rootNode, request.getFieldName());
            if (path.isEmpty()) {
                logger.info("Field path not found");
                return ResponseEntity.notFound().build();
            }

            logger.info("Field path found: {}", path);

            JsonResponse jsonResponse = new JsonResponse();
            jsonResponse.setPath(path); // Set the path in JsonResponse

            return ResponseEntity.ok(jsonResponse);
        } catch (IOException e) {
            logger.error("Invalid JSON", e);
            return ResponseEntity.badRequest().body(new JsonResponse("Invalid JSON"));
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(new JsonResponse(e.getMessage()));
        }
    }
}
