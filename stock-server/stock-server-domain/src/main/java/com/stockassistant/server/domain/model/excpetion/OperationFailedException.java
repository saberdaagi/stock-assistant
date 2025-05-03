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
 * Exception thrown when an operation fails to complete successfully.
 * This exception is typically thrown when a business operation cannot be completed
 * due to various reasons such as validation failures, business rule violations,
 * or system errors.
 */
public class OperationFailedException extends RuntimeException {
    /**
     * Constructs a new OperationFailedException with no detail message.
     */
    public OperationFailedException() {
        super();
    }
}
