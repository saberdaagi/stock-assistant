package com.stockassistant.ai.client.config;

import com.stockassistant.ai.client.tools.InventoryTools;
import com.stockassistant.ai.client.tools.ProductTools;
import com.stockassistant.ai.client.tools.WarehousesTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {
    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder,
                                 InventoryTools inventoryTools,
                                 ProductTools productsServiceClient,
                                 WarehousesTools warehousesTools) {

        return chatClientBuilder
                .defaultTools(inventoryTools, productsServiceClient, warehousesTools)
                .build();
    }
}
