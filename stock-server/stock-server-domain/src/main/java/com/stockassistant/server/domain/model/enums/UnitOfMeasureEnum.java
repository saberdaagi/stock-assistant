/**
 * Copyright (c) 2024 Stock Assistant. All rights reserved.
 *
 * This software is the confidential and proprietary information of the creator.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with
 * Stock Assistant.
 *
 * @author Daagi Saber
 * @version 1.0
 */

package com.stockassistant.server.domain.model.enums;

/**
 * Enumeration representing different units of measure for products in the inventory system.
 * This enum defines the standard units that can be used to measure product quantities.
 */
public enum UnitOfMeasureEnum {
    /** Individual units or pieces */
    UNIT,
    
    /** Weight measurement in kilograms */
    KILOGRAM,
    
    /** Volume measurement in liters */
    LITER,
    
    /** Length measurement in meters */
    METER
}
