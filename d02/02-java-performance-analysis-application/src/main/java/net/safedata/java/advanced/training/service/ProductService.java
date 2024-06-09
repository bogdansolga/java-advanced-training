package net.safedata.java.advanced.training.service;

import net.safedata.java.advanced.training.domain.model.ProductEntity;
import net.safedata.java.advanced.training.domain.repository.ProductRepository;
import net.safedata.java.advanced.training.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private static final long BYTES_IN_MB = 1048576;

    private static final Random RANDOM = new Random(20000);

    private static final Runtime RUNTIME = Runtime.getRuntime();

    private final DecimalFormat decimalFormat = new DecimalFormat("#,###.#");

    // kept in memory to show the case of a continuously growing memory
    private final List<Product> products = new ArrayList<>();
    private double totalSales = 0;

    private final JdbcTemplate jdbcTemplate;
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(DataSource dataSource, ProductRepository productRepository) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.productRepository = productRepository;
    }

    //@EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void insertSomeProducts() {
        List<ProductEntity> productsToBeInserted = new ArrayList<>();
        IntStream.rangeClosed(0, 100)
                 .parallel() // low-hanging fruit --> always parallel
                 .forEach(index -> productsToBeInserted.add(buildProductEntity(index)));

        productRepository.saveAll(productsToBeInserted);
    }

    private static ProductEntity buildProductEntity(int index) {
        return new ProductEntity(index, "The product " + index, 1000 * RANDOM.nextInt(50000));
    }

    @Scheduled(
            fixedRate = 3,
            timeUnit = TimeUnit.SECONDS
    )
    public void simulateProductsProcessing() {
        System.out.println();

        final long memoryBefore = getFreeMemoryInMB();
        LOGGER.info("JVM memory in use before: {} MB", memoryBefore);

        final long now = System.currentTimeMillis();
        processALotOfProducts();
        LOGGER.info("The entire processing took {} ms", System.currentTimeMillis() - now);

        final long memoryAfter = getFreeMemoryInMB();
        LOGGER.info("JVM memory in use after: {} MB", memoryAfter);
    }

    /*
    @Scheduled(
            fixedRate = 20,
            timeUnit = TimeUnit.SECONDS
    )
    */
    public void simulateProductsProcessingUsingAStopwatch() {
        System.out.println();

        StopWatch stopWatch = new StopWatch("simulateProductsProcessing");

        final long memoryBefore = getFreeMemoryInMB();
        LOGGER.info("JVM memory in use before the processing: {} MB", memoryBefore);

        stopWatch.start("Processing a lot of products");
        processALotOfProducts();
        stopWatch.stop();

        stopWatch.start("A short expensive task");
        sleepALittle(200);
        stopWatch.stop();

        stopWatch.start("A long expensive task");
        CompletableFuture.runAsync(() -> sleepALittle(5000)); // executed on the ForkJoin pool
        stopWatch.stop();

        final long memoryAfter = getFreeMemoryInMB();
        LOGGER.info("JVM memory in use after the processing: {} MB", memoryAfter);

        LOGGER.info("Execution summary: {}", stopWatch.prettyPrint());
    }

    private void processALotOfProducts() {
        final int productsNumber = RANDOM.nextInt(50000); //000
        generateProducts(productsNumber);

        final double totalPrice = getProductsPriceSum(products);
        LOGGER.info("The total price of {} products is {}", products.size(), decimalFormat.format(totalPrice));

        totalSales += totalPrice;
        LOGGER.info("The total sales is currently {}", decimalFormat.format(totalSales));
    }

    private void generateProducts(int productsNumber) {
        IntStream.rangeClosed(0, productsNumber)
                 .parallel() // low-hanging fruit --> always parallel
                 .forEach(index -> products.add(buildProduct(index)));

        @SuppressWarnings("unused")
        final Stream<Product> dynamicallyParallelStream =
                StreamSupport.stream(products.spliterator(), products.size() > 100);
    }

    //@Scheduled(fixedRate = 5000)
    public void generateALotOfProducts() {
        final long memoryBefore = getFreeMemoryInMB();
        LOGGER.info("JVM memory in use before generating a lot of data: {} MB", memoryBefore);

        generateProducts(1_000_000);
        LOGGER.info("The cost of the current products is {}", getProductsPriceSum(products));

        final long memoryAfter = getFreeMemoryInMB();
        LOGGER.info("JVM memory in use after generating a lot of data: {} MB", memoryAfter);
    }

    private static double getProductsPriceSum(Collection<Product> products) {
        //TODO replace with StreamSupport.parallel
        return products.stream()
                       .filter(Objects::nonNull)
                       .mapToDouble(Product::getPrice)
                       .sum();
    }

    private long getFreeMemoryInMB() {
        return (RUNTIME.totalMemory() - RUNTIME.freeMemory()) / BYTES_IN_MB;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public List<Product> getALotOfProducts(final String productType, final String retrievingType) {
        final int howMany = RANDOM.nextInt(70);
        final List<Product> products = new ArrayList<>(howMany);
        IntStream.range(0, howMany)
                 .peek(this::sleepALittle)
                 .forEach(index -> products.add(buildProduct(index)));

        return products;
    }

    public synchronized List<Product> getSynchronizedProducts(final String productType) {
        return getALotOfProducts(productType, "synchronized");
    }

    private Product buildProduct(final int index) {
        //sleepALittle(10);
        return new Product(index, "The product " + index, 1000 * RANDOM.nextInt(50000) + 10);
    }

    private void sleepALittle(final int bound) {
        //if (true) return; //TODO uncomment to add some processing time
        try {
            Thread.sleep(RANDOM.nextInt(Math.abs(bound) + 10));
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @SuppressWarnings("unused")
    public void potentialMemoryLeaksGenerators() {
        // double brace init - potential cause for a memory leak
        new HashMap<Integer, Product>() {{
            IntStream.range(0, 200)
                     .forEach(index -> put(index, buildProduct(RANDOM.nextInt())));
        }};

        { // inner / private / anonymous block
            @SuppressWarnings("unused")
            final Product product = buildProduct(20);
        }
    }

    public List<ProductEntity> getAllDatabaseProducts() {
        return jdbcTemplate.query("SELECT * FROM product", (rs, row) -> buildProductEntityFromResultSet(rs));
        //return productRepository.findAll();
    }

    private static ProductEntity buildProductEntityFromResultSet(ResultSet rs) throws SQLException {
        return new ProductEntity(rs.getInt("id"), rs.getString("name"),
                rs.getDouble("price"));
    }
}
