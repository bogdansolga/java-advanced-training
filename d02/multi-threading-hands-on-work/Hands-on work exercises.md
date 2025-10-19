# Multi-Threading and Async Processing Hands-On Exercises

## Part 1: Threading Basics

1. Create a platform thread that prints all products from a given list with their prices.
2. Create a virtual thread that calculates the total price of all products in a Section.
3. Create 3 virtual threads with custom names ("price-calculator", "discount-checker", "tag-processor") that process different aspects of a Product.
4. Create a Runnable that processes a Store by printing all section names, execute it using both a platform thread and a virtual thread.
5. Compare execution time: Process 1000 Products using platform threads vs virtual threads (simple operation like getting product names).
6. Create multiple virtual threads that each process a different Section from a Store independently (parallel section processing).
7. Launch parallel virtual threads to filter products by different criteria (price range, has discount, has tags) across multiple Sections simultaneously.
8. Create separate virtual threads that each process their own List of Products and return results independently (no shared state).
9. Process multiple Stores in parallel using virtual threads, where each thread creates and returns its own list of product names.
10. Create a ThreadLocal variable to store the current processing context (e.g., "StoreId") and access it from different threads processing different stores.

## Part 2: ExecutorService and Futures

1. Create a fixed thread pool (4 threads) and submit 10 tasks that each process a Product and return its price.
2. Create a Callable that calculates the total price for a Section's products, submit it to an ExecutorService and get the result using Future.get().
3. Use ExecutorCompletionService to process multiple Stores in parallel and collect their total product counts as they complete.
4. Submit 5 Callable tasks that find products by price range in different sections, collect all results with a timeout of 2 seconds.
5. Create a cached thread pool and submit tasks to apply discounts to 100 products, measure execution time vs single-threaded approach.
6. Use Executors.newScheduledThreadPool() to periodically check (every 2 seconds) if a Product's price exceeds a threshold.
7. Submit multiple Callable tasks that search for managers by name in different stores, cancel slow tasks after 1 second using Future.cancel().
8. Create a parallel processing pattern: fork phase (submit Product price calculations), join phase (collect and sum all prices).
9. Use invokeAll() to execute multiple Callables that count products per StoreSection type, process results when all complete.
10. Use invokeAny() to query multiple "stores" (simulated with different delays) for a product's availability, return the first successful result.

## Part 3: CompletableFuture

1. Create a CompletableFuture.supplyAsync() that loads a Product and use thenApply() to extract its name.
2. Chain thenApply() operations: load Product → get price → apply discount → format as string.
3. Use thenCompose() to load a Section, then load its Products list (nested async operation).
4. Use thenCombine() to load two Products asynchronously and calculate their combined price.
5. Create a CompletableFuture that loads a Store's sections, use thenAccept() to print each section name without returning a value.
6. Use exceptionally() to handle errors when loading a Product (simulate ProductNotFoundException) and return a default Product.
7. Use handle() to process both successful result and exception when calculating discounted price (handle division by zero).
8. Chain multiple async operations: load Store → get all Sections → flatMap to all Products → calculate total price using thenApplyAsync().
9. Use CompletableFuture.allOf() to load multiple Stores in parallel, wait for all to complete, then aggregate total product count.
10. Use CompletableFuture.anyOf() to query multiple stores for a product's best price, return the first response received.

## Part 4: Advanced Patterns

1. Implement parallel map-reduce: process Products list in parallel (map prices), then reduce to calculate average price.
2. Create a pipeline: load Sections → process Products in parallel → filter with discounts → collect to Map<StoreSection, List<Product>>.
3. Use CompletableFuture with custom Executor (fixed thread pool of 3) to process Products with controlled parallelism.
4. Implement timeout handling: query multiple stores for product availability with completeOnTimeout() returning empty Optional after 1 second.
5. Create a CompletableFuture chain with whenComplete() to log execution time for each stage of Store processing.
6. Implement fan-out pattern: one Store triggers parallel processing of all its Sections, each Section processes its Products.
7. Use thenCompose() to chain: find Store by location → find Section by type → find Product by price range.
8. Create error recovery chain: try to load Product from Store1, if fails try Store2, if fails try Store3, finally return default Product.
9. Combine 3 CompletableFutures using thenCombine() twice: load Product, load Discount, load Manager info, combine all into a report DTO.
10. Implement a parallel batch processor: split List of 100 Products into batches of 10, process each batch asynchronously, combine results maintaining order.
