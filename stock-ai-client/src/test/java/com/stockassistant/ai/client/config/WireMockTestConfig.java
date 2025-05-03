package com.stockassistant.ai.client.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.stockassistant.ai.client.api.ProductsApi;
import com.stockassistant.ai.client.invoker.ApiClient;
import com.stockassistant.ai.client.tools.ProductTools;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
@Profile("test")
public class WireMockTestConfig {

    private static final int WIREMOCK_PORT = 8069;
    private final WireMockServer wireMockServer = new WireMockServer(WIREMOCK_PORT);

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(wireMockServerUrl());
        uriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        restTemplate.setUriTemplateHandler(uriBuilderFactory);
        return restTemplate;
    }

    @Bean
    public ApiClient apiClient(RestTemplate restTemplate) {
        ApiClient apiClient = new ApiClient(restTemplate);
        apiClient.setBasePath(wireMockServerUrl());
        return apiClient;
    }

    @Bean
    public ProductsApi productsApi(ApiClient apiClient) {
        return new ProductsApi(apiClient);
    }

    @Bean
    public ProductTools productTools(ProductsApi productsApi) {
        return new ProductTools(productsApi);
    }

    @PostConstruct
    void startServer() {
        wireMockServer.start();
        WireMock.configureFor("localhost", WIREMOCK_PORT);
    }

    private String wireMockServerUrl() {
        return "http://localhost:" + WIREMOCK_PORT;
    }
}