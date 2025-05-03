package com.stockassistant.ai.client.tools;


import com.stockassistant.ai.client.api.InventoryApi;
import com.stockassistant.ai.client.model.Inventories;
import com.stockassistant.ai.client.model.InventoryItem;
import com.stockassistant.ai.client.model.UpdateInventoryItemRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryTools {

    private final InventoryApi inventoryApi;


    /**
     * Retrieves a paginated list of inventory items from the MCP server.
     * Supports optional filtering by warehouse or product ID.
     *
     * @param page        Page number to retrieve (defaults to 1 if null).
     * @param pageSize    Number of items per page (defaults to 20 if null).
     * @param warehouseId Optional UUID of the warehouse to filter by.
     * @param productId   Optional UUID of the product to filter by.
     * @return A PaginatedInventory containing the requested items.
     * @throws RestClientException if the API call fails.
     */
    @Tool(name = "GetInventoryLevels",
            description = "Fetches a page of inventory items. Parameters: page number, page size, warehouseId (optional), productId (optional).")
    public Inventories getInventory(Integer page,
                                    Integer pageSize,
                                    UUID warehouseId,
                                    UUID productId) throws RestClientException {
        log.info("Fetching inventory items - page: {}, pageSize: {}, warehouseId: {}, productId: {}",
                page, pageSize, warehouseId, productId);
        Inventories inventories = inventoryApi.getInventory(
                page,
                pageSize,
                warehouseId,
                productId);
        log.info("Found {} inventory items", inventories.getData().size());
        return inventories;
    }

    /**
     * Updates the stock quantity of a specific product in a specific warehouse.
     *
     * @param warehouseId UUID of the warehouse where stock should be updated.
     * @param productId   UUID of the product whose inventory will be updated.
     * @param request     Payload containing the new quantity and update details.
     * @return The InventoryItem reflecting the updated quantity.
     * @throws RestClientException if the API call fails or resource isn't found.
     */
    @Tool(name = "UpdateInventoryQuantity", description = "Updates a product's inventory in a given warehouse. Requires warehouseId, productId, and a request with the new quantity.")
    public InventoryItem updateInventoryItem(UUID warehouseId,
                                             UUID productId,
                                             UpdateInventoryItemRequest request) throws RestClientException {
        log.info("Updating inventory item - warehouseId: {}, productId: {}, new quantity: {}",
                warehouseId, productId, request.getQuantity());
        InventoryItem updatedItem = inventoryApi.updateInventoryItem(
                warehouseId,
                productId,
                request);
        log.info("Inventory item updated successfully");
        return updatedItem;
    }
}

