# Functional Programming Hands-On Exercises

## Part 1: Functional Interfaces

1. Create a `Predicate<Product>` that checks if a product's price is greater than 100.
2. Create a `Consumer<Product>` that prints the product name and price in the format: "Product: [name] - $[price]".
3. Create a `Function<Product, String>` that converts a Product to a formatted string with discount information (if present).
4. Create a `Supplier<Product>` that generates a new Product with random values.
5. Create a `BiPredicate<Product, Double>` that checks if a product's price is within a given threshold.
6. Create a `BiFunction<Product, Discount, Product>` that applies a discount to a product and returns a new Product with updated price.
7. Create a `UnaryOperator<Product>` that applies a 10% price increase to a product.
8. Chain multiple Predicates: Create a combined predicate that checks if a product has a price > 50 AND has a discount.
9. Create a `BiConsumer<Store, Manager>` that adds a manager to a store's manager set and prints a confirmation message.
10. Create a custom functional interface `ProductValidator` with method `ValidationResult validate(Product product, List<String> requiredTags)` that returns a ValidationResult (create this class) indicating if the product has all required tags.

## Part 2: Optional Class

1. Given a Product, safely get the discount value or return 0 if no discount exists.
2. Given a Product with Optional discount, check if discount type is Percent using `filter()`.
3. Given a Section, get the first product from its products list, or throw a custom exception if no products exist.
4. Given a Product, get its tag list, flatten it, and return the first tag or "untagged" as default.
5. Given a Store, find a section by name (StoreSection enum) and return its id, or return -1 if not found.
6. Chain Optional operations: Given a Section, get its products, find a product by name, get its discount, and return the discount value.
7. Given a Product with Optional discount, transform the discount value based on its type (multiply by price for Percent, return as-is for Value).
8. Given a List of Sections, safely get the products from the first section and return the count, or 0 if no sections or no products.
9. Use `Optional.or()` to provide an alternative Product when the discount is missing from a given Product.
10. Given a Store, find a manager by name using Optional, and if not found, create and return a new Manager with that name using `orElseGet()`.

## Part 3: Streams API

1. Filter products from a list where price > 100.
2. Map a list of Products to their names and collect to a List.
3. Given a list of Products, calculate the total price of all products.
4. Find the most expensive product from a list of Products.
5. Group products by price range: <50, 50-100, >100 (use Collectors.groupingBy with custom classifier).
6. Given a Store, get all products from all sections (flatMap) and filter those with discounts.
7. Given a list of Products with tags, get all unique tags across all products (flatMap on Optional, then distinct).
8. Sort products by price descending, then by name ascending, and collect to a List.
9. Given a list of Stores, create a Map<String, Long> where key is location and value is count of total products across all sections.
10. Given a list of Products, partition them into two groups: those with Percent discounts and those without or with Value discounts. Then calculate average price for each partition.
