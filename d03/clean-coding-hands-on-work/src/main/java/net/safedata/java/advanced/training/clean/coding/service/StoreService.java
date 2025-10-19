package net.safedata.java.advanced.training.clean.coding.service;

import net.safedata.java.advanced.training.training.model.Discount;
import net.safedata.java.advanced.training.training.model.Manager;
import net.safedata.java.advanced.training.training.model.Product;
import net.safedata.java.advanced.training.training.model.Section;
import net.safedata.java.advanced.training.training.model.Store;
import net.safedata.java.advanced.training.training.model.StoreSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class contains multiple clean coding issues that need to be fixed.
 * See EXERCISES.md for the list of exercises.
 */
public class StoreService {

    // Issue: Magic number
    public boolean isExpensiveProduct(Product product) {
        return product.getPrice() > 500;
    }

    // Issue: Poor method name - doesn't describe what it does
    public void doSomething(Store store) {
        System.out.println("Processing store: " + store.getName());
    }

    // Issue: Too many parameters
    public Store createStore(int id, String name, String location, Set<Section> sections,
                            Set<Manager> managers, boolean active, String category,
                            double rating, int employeeCount) {
        return new Store(id, name, location, sections, managers);
    }

    // Issue: Long method doing too many things
    public Map<String, Object> getStoreStatistics(Store store) {
        Map<String, Object> stats = new HashMap<>();

        // Calculate total products
        int totalProducts = 0;
        for (Section section : store.getStoreSections()) {
            if (section.getProducts().isPresent()) {
                totalProducts += section.getProducts().get().size();
            }
        }
        stats.put("totalProducts", totalProducts);

        // Calculate total price
        double totalPrice = 0.0;
        for (Section section : store.getStoreSections()) {
            if (section.getProducts().isPresent()) {
                for (Product product : section.getProducts().get()) {
                    totalPrice += product.getPrice();
                }
            }
        }
        stats.put("totalPrice", totalPrice);

        // Count products with discounts
        int discountedProducts = 0;
        for (Section section : store.getStoreSections()) {
            if (section.getProducts().isPresent()) {
                for (Product product : section.getProducts().get()) {
                    if (product.getDiscount().isPresent()) {
                        discountedProducts++;
                    }
                }
            }
        }
        stats.put("discountedProducts", discountedProducts);

        // Calculate average price
        double avgPrice = totalProducts > 0 ? totalPrice / totalProducts : 0.0;
        stats.put("averagePrice", avgPrice);

        return stats;
    }

    // Issue: Multiple nested loops
    public List<Product> findProductsInPriceRange(Store store, double minPrice, double maxPrice) {
        List<Product> result = new ArrayList<>();
        for (Section section : store.getStoreSections()) {
            if (section.getProducts().isPresent()) {
                for (Product product : section.getProducts().get()) {
                    if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
                        result.add(product);
                    }
                }
            }
        }
        return result;
    }

    // Issue: Deep nesting and complex conditions
    public double calculateTotalDiscountValue(Store store) {
        double total = 0.0;
        for (Section section : store.getStoreSections()) {
            if (section.getProducts().isPresent()) {
                for (Product product : section.getProducts().get()) {
                    if (product.getDiscount().isPresent()) {
                        Discount discount = product.getDiscount().get();
                        if (discount.getDiscountType() == Discount.Type.Percent) {
                            total += (product.getPrice() * discount.getValue() / 100);
                        } else {
                            if (discount.getValue() < product.getPrice()) {
                                total += discount.getValue();
                            } else {
                                total += product.getPrice();
                            }
                        }
                    }
                }
            }
        }
        return total;
    }

    // Issue: Duplicate code (similar to findProductsInPriceRange)
    public List<Product> findExpensiveProducts(Store store) {
        List<Product> result = new ArrayList<>();
        for (Section section : store.getStoreSections()) {
            if (section.getProducts().isPresent()) {
                for (Product product : section.getProducts().get()) {
                    if (product.getPrice() > 1000) {
                        result.add(product);
                    }
                }
            }
        }
        return result;
    }

    // Issue: Comments explaining what code does instead of self-explanatory code
    public List<Product> processProducts(Store store) {
        List<Product> result = new ArrayList<>();
        // Loop through all sections
        for (Section section : store.getStoreSections()) {
            // Check if section has products
            if (section.getProducts().isPresent()) {
                // Get the products list
                List<Product> products = section.getProducts().get();
                // Loop through products
                for (Product product : products) {
                    // Check if product has discount
                    if (product.getDiscount().isPresent()) {
                        // Check discount type
                        if (product.getDiscount().get().getDiscountType() == Discount.Type.Percent) {
                            // Check if discount is more than 20%
                            if (product.getDiscount().get().getValue() > 20) {
                                // Add product to result
                                result.add(product);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    // Issue: Long if-else chain
    public String getStoreCategoryDescription(Store store, String category) {
        if (category.equals("electronics")) {
            return "Electronics store selling devices and gadgets";
        } else if (category.equals("clothing")) {
            return "Clothing store with fashion items";
        } else if (category.equals("food")) {
            return "Food store with groceries";
        } else if (category.equals("furniture")) {
            return "Furniture store with home items";
        } else if (category.equals("books")) {
            return "Book store with various publications";
        } else if (category.equals("sports")) {
            return "Sports equipment store";
        } else {
            return "General store";
        }
    }

    // Issue: Method doing multiple things with mixed abstraction levels
    public void updateStoreInventory(Store store, int productId, int quantity) {
        // High-level business logic mixed with low-level operations
        System.out.println("Starting inventory update for store: " + store.getName());

        boolean found = false;
        for (Section section : store.getStoreSections()) {
            if (section.getProducts().isPresent()) {
                for (Product product : section.getProducts().get()) {
                    if (product.getId() == productId) {
                        found = true;
                        System.out.println("Found product: " + product.getName());
                        // Simulate quantity update (in reality, Product is immutable)
                        System.out.println("Updating quantity to: " + quantity);
                        // Log to database (simulation)
                        String logEntry = "UPDATE inventory SET quantity=" + quantity + " WHERE product_id=" + productId;
                        System.out.println("SQL: " + logEntry);
                        break;
                    }
                }
            }
            if (found) break;
        }

        if (!found) {
            System.err.println("Product not found: " + productId);
        }

        System.out.println("Inventory update completed");
    }

    // Issue: Boolean flag parameter
    public List<Product> getProducts(Store store, boolean includeExpensive) {
        List<Product> result = new ArrayList<>();
        for (Section section : store.getStoreSections()) {
            if (section.getProducts().isPresent()) {
                for (Product product : section.getProducts().get()) {
                    if (includeExpensive) {
                        result.add(product);
                    } else {
                        if (product.getPrice() <= 500) {
                            result.add(product);
                        }
                    }
                }
            }
        }
        return result;
    }

    // Issue: God method - does everything
    public Map<String, Object> generateCompleteStoreReport(Store store, boolean includeManagers,
                                                           boolean includeProducts,
                                                           boolean includeStatistics,
                                                           boolean includePricing) {
        Map<String, Object> report = new HashMap<>();

        report.put("storeId", store.getId());
        report.put("storeName", store.getName());
        report.put("location", store.getLocation());

        if (includeManagers) {
            List<String> managerNames = new ArrayList<>();
            for (Manager manager : store.getStoreManagers()) {
                managerNames.add(manager.getName());
            }
            report.put("managers", managerNames);
        }

        if (includeProducts) {
            int totalProducts = 0;
            for (Section section : store.getStoreSections()) {
                if (section.getProducts().isPresent()) {
                    totalProducts += section.getProducts().get().size();
                }
            }
            report.put("productCount", totalProducts);
        }

        if (includeStatistics) {
            Map<StoreSection, Integer> sectionStats = new HashMap<>();
            for (Section section : store.getStoreSections()) {
                if (section.getProducts().isPresent()) {
                    sectionStats.put(section.getName(), section.getProducts().get().size());
                }
            }
            report.put("sectionStatistics", sectionStats);
        }

        if (includePricing) {
            double minPrice = Double.MAX_VALUE;
            double maxPrice = Double.MIN_VALUE;
            double totalPrice = 0.0;
            int count = 0;

            for (Section section : store.getStoreSections()) {
                if (section.getProducts().isPresent()) {
                    for (Product product : section.getProducts().get()) {
                        double price = product.getPrice();
                        if (price < minPrice) minPrice = price;
                        if (price > maxPrice) maxPrice = price;
                        totalPrice += price;
                        count++;
                    }
                }
            }

            Map<String, Double> pricing = new HashMap<>();
            pricing.put("minPrice", minPrice);
            pricing.put("maxPrice", maxPrice);
            pricing.put("avgPrice", count > 0 ? totalPrice / count : 0.0);
            report.put("pricing", pricing);
        }

        return report;
    }
}
