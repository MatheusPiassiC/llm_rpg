package com.llmrpg.app.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OllamaLLMClient implements LLMClient {

    private static final URI API_URI =
            URI.create("http://localhost:11435/api/chat");

    private static final String MODEL = "qwen2.5:3b-instruct";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public OllamaLLMClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String generate(String prompt) {
        try {
            ObjectNode requestBody = objectMapper.createObjectNode();
            requestBody.put("model", MODEL);
            requestBody.put("stream", false);

            ArrayNode messages = requestBody.putArray("messages");
            messages.addObject()
                    .put("role", "user")
                    .put("content", prompt);

            HttpRequest request = HttpRequest.newBuilder(API_URI)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            objectMapper.writeValueAsString(requestBody)))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() != 200) {
                throw new RuntimeException(
                        "Ollama retornou HTTP " + response.statusCode()
                                + ": " + response.body()
                );
            }

            JsonNode responseBody = objectMapper.readTree(response.body());

            return responseBody.path("message")
                    .path("content")
                    .asText();

        } catch (IOException e) {
            throw new RuntimeException("Falha ao comunicar com o Ollama.", e);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("A requisição ao Ollama foi interrompida.", e);
        }
    }
}
