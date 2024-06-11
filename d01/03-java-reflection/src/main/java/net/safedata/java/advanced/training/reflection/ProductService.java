package net.safedata.java.advanced.training.reflection;

public class ProductService {
    public static void main(String[] args) {
        System.out.println(new Product(10, "Tablet"));
    }

    public Product buildProduct(int id, String name) {
        return new Product(id, name);
    }
}
