# Unit Testing Hands-On Exercises

## Overview
Write unit tests for the `ProductService` class CRUD methods covering both happy and unhappy paths. Use Mockito for mocking dependencies and JUnit 5 for test structure.

## Setup Requirements
- Mock the `ProductRepository` dependency using `@Mock` or `Mockito.mock()`
- Use `@InjectMocks` or manual instantiation for `ProductService`
- Verify interactions using `verify()` and `verifyNoMoreInteractions()`
- Use assertions from JUnit 5 (`assertEquals`, `assertThrows`, etc.)

## Part 1: Basic Happy Path Tests (Simple)

### save() method

1. **Test Successful Save**: Verify that `save()` method calls `productRepository.save()` with the correct Product entity and returns "OK".
2. **Test Save Returns Correct Result**: Verify that the return value from `save()` is exactly "OK".

### get() method

3. **Test Get Existing Product**: Mock repository to return a Product, verify that `get()` returns the correct ProductDTO with matching id and name.
4. **Test Product Conversion**: Verify that Product entity is correctly converted to ProductDTO with proper field mapping.

### getAll() method

5. **Test Get All Products**: Mock repository to return multiple Products, verify that `getAll()` returns the correct number of ProductDTOs.
6. **Test Empty List**: Mock repository to return empty list, verify that `getAll()` returns empty list without errors.

## Part 2: Unhappy Path Tests (Moderate)

### get() method

7. **Test Get Non-Existing Product**: Mock repository to return `Optional.empty()`, verify that `get()` throws `IllegalArgumentException`.
8. **Test Get Product With Special ID**: Verify that calling `get(13)` throws `IllegalArgumentException` with message "There is no product with the ID 13".
9. **Test Exception Message Content**: Verify that the exception thrown for non-existing product contains the correct product ID in the message.

### delete() method

10. **Test Delete Non-Existing Product**: Mock repository to return `Optional.empty()` for findById, verify that `delete()` throws `IllegalArgumentException`.
11. **Test Delete Successful**: Mock repository to return a Product, verify that `productRepository.delete()` is called exactly once.

### update() method

12. **Test Update Non-Existing Product**: Mock repository to return `Optional.empty()`, verify that `update()` throws `IllegalArgumentException`.

## Part 3: Verification and Interaction Tests (More Complex)

### save() method

13. **Test Repository Interaction**: Verify that `productRepository.save()` is called exactly once with a Product that has the correct name.
14. **Test No Extra Interactions**: After calling `save()`, verify that no other repository methods are called using `verifyNoMoreInteractions()`.

### update() method

15. **Test Update Flow**: Mock repository to return a Product, verify that:
    - `findById()` is called once
    - `save()` is called once
    - The saved Product has the updated name

16. **Test Update With Null Name**: Verify behavior when ProductDTO has null productName (should it throw exception or handle gracefully?).

### getAll() method

17. **Test Filtering Logic**: Create Products with empty names and valid names, verify that filtering logic is applied correctly in `getAll()`.
18. **Test Sorting**: Create multiple Products with different names, verify that `getAll()` returns them sorted by name.

## Part 4: Edge Cases (Advanced)

19. **Test Get With Negative ID**: Verify behavior when calling `get()` with negative ID (should repository be called?).
20. **Test Get With Zero ID**: Verify behavior when calling `get(0)` - should it proceed to repository or validate first?
21. **Test Save With Null DTO**: Verify that calling `save(null)` throws appropriate exception (NullPointerException).
22. **Test Update With Null DTO**: Verify behavior when calling `update(1, null)`.
23. **Test Delete With Valid ID**: Create a complete test verifying:
    - Product is fetched from repository
    - Product is deleted from repository
    - Verify correct Product object is passed to delete()

## Part 5: Argument Captors (Elaborate)

24. **Test Save With Argument Captor**: Use `ArgumentCaptor` to capture the Product passed to `repository.save()` and verify its name matches the DTO.
25. **Test Update With Argument Captor**: Use `ArgumentCaptor` to capture the updated Product in `save()` call and verify:
    - ID remains unchanged
    - Name is updated from DTO
    - It's the same object returned by findById

26. **Test Multiple Save Calls**: Call `save()` multiple times with different DTOs, use `ArgumentCaptor` to capture all calls and verify each Product has correct name.

## Part 6: Behavior Verification (Most Elaborate)

27. **Test Get With Complete Flow**: Write a comprehensive test that:
    - Mocks findById to return a Product with specific values
    - Calls get() method
    - Verifies the returned DTO has correct id and productName
    - Verifies repository.findById() was called exactly once with correct id
    - Verifies no other repository methods were called

28. **Test GetAll Ordering**: Create 5 Products with names in random order, verify that `getAll()`:
    - Returns correct count
    - Returns products sorted alphabetically by name
    - Calls repository.findAll() exactly once

29. **Test Update Complete Workflow**: Create a test that:
    - Mocks findById to return existing Product
    - Mocks save to return updated Product
    - Calls update() with new data
    - Captures the saved Product using ArgumentCaptor
    - Verifies the captured Product has updated name but same ID

30. **Test Error Path Complete**: For each CRUD method (get, update, delete), write a comprehensive test that:
    - Mocks repository to simulate failure (Optional.empty or exception)
    - Verifies the correct exception is thrown
    - Verifies the exception message is meaningful
    - Verifies repository is called with correct parameters
    - Verifies no save/delete is attempted after failure
