package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private Map<String, Integer> storage;
    private BalanceOperation balanceOperation;

    @BeforeEach
    void setUp() {
        storage = new HashMap<>();
        balanceOperation = new BalanceOperation();
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void handle_validBalance_setsQuantity() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100);
        balanceOperation.handle(storage, transaction);
        assertEquals(100, storage.get("apple"));
    }

    @Test
    void handle_zeroQuantity_setsZero() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 0);
        balanceOperation.handle(storage, transaction);
        assertEquals(0, storage.get("apple"));
    }

    @Test
    void handle_overwritesExistingValue() {
        storage.put("apple", 50);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 200);
        balanceOperation.handle(storage, transaction);
        assertEquals(200, storage.get("apple"));
    }

    @Test
    void handle_newFruit_appearsInStorage() {
        assertNull(storage.get("mango"));
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "mango", 30);
        balanceOperation.handle(storage, transaction);
        assertEquals(30, storage.get("mango"));
    }
}