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
 * Enumeration representing the different categories of products in the system.
 * This enum is used to classify products into broad categories for better organization
 * and filtering.
 */
public enum ProductCategoryEnum {
    /** Products related to electronic devices and components */
    ELECTRONICS,

    /** Products related to tools, equipment, and building materials */
    HARDWARE,

    /** Products that are regularly used and need to be replenished */
    CONSUMABLES
}
