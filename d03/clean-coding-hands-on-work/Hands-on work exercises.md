# Clean Coding Hands-On Exercises

## Overview
The `StoreService` class contains multiple clean coding issues. Fix them progressively, starting with simple improvements and moving to more elaborate refactorings.

## Part 1: Simple Naming and Constants (Quick Fixes)

1. **Extract Magic Number**: In `isExpensiveProduct()`, replace the magic number `500` with a named constant `EXPENSIVE_PRODUCT_THRESHOLD`.
2. **Improve Method Name**: Rename `doSomething()` to a descriptive name that clearly indicates it logs/prints store information.
3. **Extract Magic Number**: In `findExpensiveProducts()`, replace the magic number `1000` with a named constant.
4. **Extract Magic Number**: In `processProducts()`, replace the magic number `20` (discount percentage threshold) with a named constant.

## Part 2: Simplify Method Signatures (Moderate Effort)

5. **Reduce Parameters**: Refactor `createStore()` to accept a `StoreCreationRequest` or `StoreBuilder` object instead of 9 individual parameters.
6. **Replace Boolean Flag**: Refactor `getProducts()` to eliminate the boolean parameter by creating two explicit methods: `getAllProducts()` and `getAffordableProducts()`.
7. **Replace Multiple Booleans**: Refactor `generateCompleteStoreReport()` to use a `ReportConfiguration` object instead of 4 boolean parameters.

## Part 3: Extract Methods (More Effort)

8. **Extract Method**: In `getStoreStatistics()`, extract separate methods for:
   - Calculating total products
   - Calculating total price
   - Counting discounted products
   - Calculating average price
9. **Extract Method**: In `calculateTotalDiscountValue()`, extract a method `calculateDiscountAmount(Product product)` to handle discount calculation logic.
10. **Extract Methods**: In `updateStoreInventory()`, extract separate methods for:
    - Finding product by ID
    - Logging the update operation
    - Handling product not found scenario

## Part 4: Remove Code Duplication (Significant Effort)

11. **Eliminate Duplication**: Extract common product filtering logic from `findProductsInPriceRange()` and `findExpensiveProducts()` into a reusable method that accepts a `Predicate<Product>`.
12. **Eliminate Duplication**: Create a helper method `getAllProductsFromStore(Store store)` to eliminate the repeated section/product iteration pattern used across multiple methods.

## Part 5: Reduce Nesting (Elaborate Refactoring)

13. **Simplify Nested Loops**: Refactor `findProductsInPriceRange()` to use streams and flatMap instead of nested loops.
14. **Reduce Deep Nesting**: Refactor `calculateTotalDiscountValue()` using early returns and guard clauses to reduce nesting levels.
15. **Simplify Complex Conditions**: In `processProducts()`, refactor the deep nesting by:
    - Using streams
    - Extracting a method `hasSignificantPercentDiscount(Product product)` for the condition check
    - Removing all unnecessary comments

## Part 6: Replace Conditional Logic (Advanced Refactoring)

16. **Replace If-Else Chain**: Refactor `getStoreCategoryDescription()` using a Map-based lookup or Strategy pattern instead of if-else chain.
17. **Introduce Strategy**: Extract discount calculation logic from `calculateTotalDiscountValue()` into separate strategy classes for Percent and Value discount types.

## Part 7: Separate Concerns (Most Elaborate)

18. **Extract Class**: From `updateStoreInventory()`, extract a `ProductFinder` class responsible for finding products in stores.
19. **Extract Class**: Create a `StoreStatisticsCalculator` class and move all statistics calculation methods (`getStoreStatistics()`, pricing calculations) to it.
20. **Refactor God Method**: Break down `generateCompleteStoreReport()` into multiple focused methods or extract a `StoreReportGenerator` class with separate methods for each report section.

## Bonus Challenges

21. **Apply Single Responsibility Principle**: Split `StoreService` into multiple focused services: `StoreQueryService`, `StoreStatisticsService`, and `StoreReportService`.
22. **Introduce Functional Approach**: Refactor all methods that iterate over sections/products to use Stream API with functional pipelines.
23. **Apply Tell Don't Ask**: Review methods that extract data from Store and perform operations. Refactor to move behavior closer to data where appropriate.
24. **Improve Error Handling**: Replace `System.err.println()` with proper exception throwing and create custom exceptions like `ProductNotFoundException`.
25. **Apply Design Patterns**: Identify opportunities to apply Builder pattern (for Store creation), Strategy pattern (for discount calculations), and Factory pattern (for report generation).
