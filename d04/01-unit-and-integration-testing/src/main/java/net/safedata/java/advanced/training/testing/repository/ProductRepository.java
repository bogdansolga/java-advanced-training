package net.safedata.java.advanced.training.testing.repository;

import net.safedata.java.advanced.training.jpa.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
