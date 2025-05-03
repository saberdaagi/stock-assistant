package com.stockassistant.ai.client.tools;

import com.stockassistant.ai.client.api.WarehousesApi;
import com.stockassistant.ai.client.model.Warehouse;
import com.stockassistant.ai.client.model.WarehouseRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class WarehousesTools {

    private final WarehousesApi warehousesApi;


    /**
     * Adds a new warehouse to the system.
     *
     * @param request The details of the warehouse to create.
     * @return The created Warehouse object.
     * @throws RestClientException if the API call fails.
     */
    @Tool(
            name = "CreateWarehouse",
            description = "Creates a new warehouse. Requires a WarehouseRequest payload with name, location, and other details."
    )
    public Warehouse createWarehouse(WarehouseRequest request) throws RestClientException {
        log.info("Creating new warehouse - name: {}, location: {}, capacity: {}",
                request.getName(), request.getLocation(), request.getCapacity());
        Warehouse warehouse = warehousesApi.createWarehouse(request);
        log.info("Warehouse created successfully with UUID: {}", warehouse.getUuid());
        return warehouse;
    }



    /**
     * Updates an existing warehouse's details.
     *
     * @param warehouseId UUID of the warehouse to update.
     * @param request     Updated warehouse information.
     * @return The updated Warehouse object.
     * @throws RestClientException if the API call fails or the warehouse is not found.
     */
    @Tool(
            name = "UpdateWarehouse",
            description = "Modifies an existing warehouse by UUID. Requires warehouseId and a WarehouseRequest with updated fields."
    )
    public Warehouse updateWarehouse(UUID warehouseId, WarehouseRequest request) throws RestClientException {
        log.info("Updating warehouse with UUID: {} - name: {}, location: {}, capacity: {}",
                warehouseId, request.getName(), request.getLocation(), request.getCapacity());
        Warehouse warehouse = warehousesApi.updateWarehouse(warehouseId, request);
        log.info("Warehouse updated successfully");
        return warehouse;
    }
}
