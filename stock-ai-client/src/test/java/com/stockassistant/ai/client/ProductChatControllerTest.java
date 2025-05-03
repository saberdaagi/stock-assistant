package com.stockassistant.ai.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.stockassistant.ai.client.config.OllamaTestConfig;
import com.stockassistant.ai.client.config.WireMockTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(classes = {OllamaTestConfig.class, WireMockTestConfig.class})
@ActiveProfiles("test")
@Slf4j
class ProductChatControllerTest {

    @Autowired
    private ChatClient chatClient;

    @Test
    void getProductDetails_ValidName_ReturnsDetails() {
        String productName = "AlphaPhone";
        String expectedPrice = "444.14";
        String expectedSku = "SKU001";

        WireMock.stubFor(WireMock.get(urlPathEqualTo("/products"))
                                 .withQueryParam("name", WireMock.equalTo(productName))
                                 .willReturn(aResponse().withStatus(OK.value())
                                                     .withBody("""
                                                                       {
                                                                           "data": [{
                                                                               "uuid": "d71d1965-ea47-44d7-a31d-76b1bc7d180d",
                                                                               "sku": "%s",
                                                                               "name": "%s",
                                                                               "description": "Smartphone with AMOLED screen",
                                                                               "category": "ELECTRONICS",
                                                                               "unitOfMeasure": "UNIT",
                                                                               "price": %s
                                                                           }],
                                                                           "total": 1,
                                                                           "page": 1,
                                                                           "pageSize": 20
                                                                       }
                                                                       """
                                                                       .formatted(expectedSku, productName,
                                                                                  expectedPrice))
                                                     .withHeader("Content-Type", APPLICATION_JSON_VALUE)));

        String response = chatClient.prompt(
                "Give me the detail of the product '%s'".formatted(productName)).call().content();

        WireMock.verify(WireMock.getRequestedFor(urlPathEqualTo("/products")).withQueryParam("name", WireMock.equalTo(
                productName)));

        assertNotNull(response, "Response should not be null");
        assertFalse(response.isBlank(), "Response should not be empty");

        assertTrue(response.contains(productName), "Response should contain product name: " + response);
        assertTrue(response.contains(expectedSku), "Response should contain SKU: " + response);
        assertTrue(response.contains(expectedPrice), "Response should contain price: " + response);

        assertTrue(response.contains("ELECTRONICS"), "Response should contain category: " + response);
        assertTrue(response.contains("AMOLED screen"), "Response should contain description: " + response);
    }
}