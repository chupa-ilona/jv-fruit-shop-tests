package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private Map<String, Integer> storage;
    private ReturnOperation returnOperation;

    @BeforeEach
    void setUp() {
        storage = new HashMap<>();
        returnOperation = new ReturnOperation();
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void handle_validReturn_addsQuantity() {
        storage.put("orange", 20);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", 10);
        returnOperation.handle(storage, transaction);
        assertEquals(30, storage.get("orange"));
    }

    @Test
    void handle_returnToNewFruit_setsQuantity() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", 15);
        returnOperation.handle(storage, transaction);
        assertEquals(15, storage.get("orange"));
    }

    @Test
    void handle_zeroQuantity_doesNotChangeStorage() {
        storage.put("orange", 20);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", 0);
        returnOperation.handle(storage, transaction);
        assertEquals(20, storage.get("orange"));
    }
}