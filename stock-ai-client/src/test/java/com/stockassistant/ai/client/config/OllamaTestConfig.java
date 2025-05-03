package com.stockassistant.ai.client.config;

import com.stockassistant.ai.client.tools.ProductTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.testcontainers.ollama.OllamaContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;

@Configuration
@Profile("test")
@Slf4j
public class OllamaTestConfig {

    private static final String MODEL_NAME = "llama3.2:1b";
    private static final OllamaContainer OLLAMA_CONTAINER =
            new OllamaContainer(DockerImageName.parse("ollama/ollama:0.1.34"));

    @Bean
    public OllamaApi ollamaApi() throws IOException, InterruptedException {
        OLLAMA_CONTAINER.start();
        log.info("Pulling '{}' model...", MODEL_NAME);
        OLLAMA_CONTAINER.execInContainer("ollama", "pull", MODEL_NAME);
        log.info("Model '{}' successfully pulled", MODEL_NAME);
        return new OllamaApi(getOllamaBaseUrl());
    }

    @Bean
    public OllamaChatModel ollamaChatModel(OllamaApi ollamaApi) {
        return OllamaChatModel.builder()
                .ollamaApi(ollamaApi)
                .defaultOptions(OllamaOptions.builder()
                                        .model(MODEL_NAME)
                                        .numCtx(4096)
                                        .temperature(0.1)
                                        .build())
                .build();
    }

    @Bean
    public ChatClient.Builder chatClientBuilder(OllamaChatModel ollamaChatModel) {
        return ChatClient.builder(ollamaChatModel);
    }


    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder,
                                 ProductTools productTools) {

        return chatClientBuilder
                .defaultTools(productTools)
                .build();
    }

    private static String getOllamaBaseUrl() {
        return String.format("http://%s:%d",
                             OLLAMA_CONTAINER.getHost(),
                             OLLAMA_CONTAINER.getMappedPort(11434));
    }
}