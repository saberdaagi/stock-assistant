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

package com.stockassistant.server.domain.model.excpetion;

/**
 * Exception thrown when a requested object cannot be found in the system.
 * This exception is typically thrown when attempting to retrieve, update, or delete
 * an object that does not exist in the database.
 */
public class ObjectNotFoundException extends RuntimeException {
    /**
     * Constructs a new ObjectNotFoundException with no detail message.
     */
    public ObjectNotFoundException() {
        super();
    }
}
