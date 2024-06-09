package net.safedata.java.advanced.training.testing.dto;

import net.safedata.java.advanced.training.jpa.model.Product;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO (Data Transfer Object) used to serialize / deserialize {@link Product} objects
 *
 * @author bogdan.solga
 */
public class ProductDTO implements Serializable {
	
    private int id;

    private String productName;

    public ProductDTO(final int id, final String productName) {
        this.id = id; this.productName = productName;
    }

    public ProductDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDTO)) return false;
        ProductDTO that = (ProductDTO) o;
        return id == that.id &&
                Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName);
    }
}
