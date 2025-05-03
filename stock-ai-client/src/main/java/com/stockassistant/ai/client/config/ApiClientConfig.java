package com.stockassistant.ai.client.config;

import com.stockassistant.ai.client.api.InventoryApi;
import com.stockassistant.ai.client.api.ProductsApi;
import com.stockassistant.ai.client.api.WarehousesApi;
import com.stockassistant.ai.client.invoker.ApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
@RequiredArgsConstructor
public class ApiClientConfig {

    @Value("${stock.api.base-url}")
    private String baseUrl;

    /**
     * Configures and provides a RestTemplate bean.
     * Ensures the RestTemplate uses the correct base URI and encoding mode.
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(baseUrl);
        uriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        restTemplate.setUriTemplateHandler(uriBuilderFactory);
        return restTemplate;
    }

    /**
     * Configures and provides an ApiClient bean.
     * Sets the base path and initializes the ApiClient with the configured RestTemplate.
     */
    @Bean
    public ApiClient apiClient(RestTemplate restTemplate) {
        ApiClient apiClient = new ApiClient(restTemplate);
        apiClient.setBasePath(baseUrl);
        return apiClient;
    }

    @Bean
    public InventoryApi inventoryApi(ApiClient apiClient) {
        return new InventoryApi(apiClient);
    }

    @Bean
    public ProductsApi productsApi(ApiClient apiClient) {
        return new ProductsApi(apiClient);
    }

    @Bean
    public WarehousesApi warehousesApi(ApiClient apiClient) {
        return new WarehousesApi(apiClient);
    }


}