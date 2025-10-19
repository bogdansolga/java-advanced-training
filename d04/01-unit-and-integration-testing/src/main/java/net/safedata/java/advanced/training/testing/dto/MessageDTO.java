package net.safedata.java.advanced.training.testing.dto;

import java.io.Serializable;

/**
 * A simple DTO used to carry a message to the consumer
 *
 * @author bogdan.solga
 */
public record MessageDTO(String message) implements Serializable {}
