# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Java Advanced Training repository containing educational examples organized by day (d01-d04) and topics. The project uses a multi-module Maven structure with Spring Boot 3.3.4 and Java 21.

## Build and Test Commands

### Building the Project

```bash
# Build entire project from root
mvn clean install

# Build specific day module
mvn clean install -pl d01

# Build specific topic module
mvn clean install -pl d01/01-functional-programming

# Build specific example (leaf module)
mvn clean install -pl d01/01-functional-programming/01-functional-interfaces/01-s01-predicates
```

### Running Tests

```bash
# Run all tests
mvn test

# Run tests for specific module
mvn test -pl d04/01-unit-and-integration-testing

# Run a single test class (from within the module directory)
cd d04/01-unit-and-integration-testing
mvn test -Dtest=ProductServiceTest

# Skip tests during build
mvn clean install -DskipTests
```

### Running Applications

```bash
# Run Spring Boot application (where applicable, e.g., performance analysis app)
mvn spring-boot:run -pl d02/02-java-performance-analysis-application

# Run a Main class directly
mvn exec:java -pl d01/01-functional-programming/01-functional-interfaces/01-s01-predicates \
  -Dexec.mainClass="net.safedata.java.advanced.training.predicate.PredicatesMain"
```

## Project Architecture

### Multi-Module Maven Structure

The project follows a **hierarchical 4-level Maven module structure**:

1. **Root POM** (`pom.xml`) - Parent aggregator with Spring Boot parent
2. **Day Modules** (`d01`, `d02`, `d03`, `d04`) - Organize examples by training day
3. **Topic Modules** - Group related examples (e.g., `01-functional-programming`, `02-jigsaw-modules-and-sealed-types`)
4. **Leaf Modules** - Individual examples/exercises (e.g., `01-s01-predicates`, `01-s02-consumers`)

**Important**: Each level has its own `pom.xml`. Topic modules often have their own intermediate POMs that aggregate leaf modules.

### Shared Domain Model

- `jpa-domain-model` - Shared JPA entities (`Product`, `Store`, `Manager`, etc.) used across multiple modules
- Referenced by modules via `dependencyManagement` in root POM
- Contains abstract entity base classes with common JPA annotations

### Module Organization by Day

**d01 - Core Java Features**
- `01-functional-programming` - Contains multiple sub-modules covering:
  - `01-functional-interfaces` - 7 sub-modules (s01-s07) covering Predicates, Consumers, Functions, Suppliers, Bi-functional interfaces, Operators, and Typed interfaces
  - `02-optional-class-and-streams-api` - Optional and Streams API examples
  - `domain-model` - Local domain model for functional programming examples
  - `hands-on-work` - Practice exercises
- `02-jigsaw-modules-and-sealed-types` - Java modules and sealed classes
- `03-java-reflection` - Reflection API examples

**d02 - Concurrency and Performance**
- `01-multi-threading-samples` - Threading, CompletableFuture, parallel processing examples
- `02-java-performance-analysis-application` - Spring Boot app with AOP, P6Spy for SQL monitoring, and performance analysis tools

**d03 - Code Quality**
- `01-clean-coding-examples` - Clean code principles and practices

**d04 - Testing**
- `01-unit-and-integration-testing` - JUnit 5, TestNG, REST-assured, and Spring Boot testing examples
  - Uses H2 in-memory database
  - Includes controller, service, and repository testing patterns
  - REST API testing with REST-assured

### Testing Frameworks

The project uses **both JUnit 5 and TestNG**:
- JUnit 5 (Jupiter) - Primary testing framework for most modules
- TestNG - Available via dependency management (version 7.10.2)
- REST-assured - For REST API testing
- Spring Boot Test - For integration testing with Spring context

### Package Naming Convention

Most code uses the base package: `net.safedata.java.advanced.training.*` with topic-specific sub-packages (e.g., `.predicate`, `.consumer`, `.async`, `.completablefuture`, `.testing`).

## Working with Modules

### Adding New Examples

When creating new examples in the existing structure:
1. Add the new module to the parent module's `<modules>` section
2. Set the parent in the new module's POM
3. Follow the existing naming convention: `XX-sYY-topic-name` where XX is sequence, YY is sub-sequence
4. Keep leaf modules simple - they typically only need parent declaration, no additional dependencies

### Running Specific Examples

Most leaf modules contain a `*Main.java` class with a main method. Navigate to the module and use:
```bash
mvn exec:java -Dexec.mainClass="full.qualified.ClassName"
```

Or build and run directly:
```bash
mvn clean package
java -cp target/classes full.qualified.ClassName
```

## Spring Boot Applications

The `d02/02-java-performance-analysis-application` module is a full Spring Boot application with:
- Spring Data JPA with H2 database
- P6Spy SQL logging integration
- Spring AOP for cross-cutting concerns
- RESTful endpoints

Configuration files are in `src/main/resources/application.properties`.
