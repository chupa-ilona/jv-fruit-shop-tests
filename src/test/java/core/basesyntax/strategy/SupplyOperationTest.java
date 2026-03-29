package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private Map<String, Integer> storage;
    private SupplyOperation supplyOperation;

    @BeforeEach
    void setUp() {
        storage = new HashMap<>();
        supplyOperation = new SupplyOperation();
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void handle_validSupply_addsQuantity() {
        storage.put("banana", 50);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 30);
        supplyOperation.handle(storage, transaction);
        assertEquals(80, storage.get("banana"));
    }

    @Test
    void handle_supplyToNewFruit_setsQuantity() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "mango", 40);
        supplyOperation.handle(storage, transaction);
        assertEquals(40, storage.get("mango"));
    }

    @Test
    void handle_zeroQuantity_doesNotChangeStorage() {
        storage.put("banana", 50);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 0);
        supplyOperation.handle(storage, transaction);
        assertEquals(50, storage.get("banana"));
    }
}
