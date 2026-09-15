package com.llmrpg.app.ai;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OllamaLLMClientTest {

    @Test
    void shouldGenerateResponse() {
        LLMClient client = new OllamaLLMClient();

        String response = client.generate(
                "Responda somente em português: A integração Java e Ollama funciona."
        );

        assertNotNull(response);
        assertFalse(response.isBlank());
    }
}