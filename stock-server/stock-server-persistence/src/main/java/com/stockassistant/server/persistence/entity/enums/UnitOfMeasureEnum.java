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

package com.stockassistant.server.persistence.entity.enums;

/**
 * Enumeration representing the different units of measure for products in the system.
 * This enum is used to specify how products are measured and tracked in inventory.
 */
public enum UnitOfMeasureEnum {
    /** Individual items or pieces */
    UNIT,

    /** Weight measurement in kilograms */
    KILOGRAM,

    /** Volume measurement in liters */
    LITER,

    /** Length measurement in meters */
    METER
}
