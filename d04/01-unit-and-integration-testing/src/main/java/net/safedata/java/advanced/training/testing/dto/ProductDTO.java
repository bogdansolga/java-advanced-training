package net.safedata.java.advanced.training.testing.dto;

import net.safedata.java.advanced.training.jpa.model.Product;

import java.io.Serializable;

/**
 * A DTO (Data Transfer Object) used to serialize / deserialize {@link Product} objects
 *
 * @author bogdan.solga
 */
public record ProductDTO(int id, String productName) implements Serializable {}
