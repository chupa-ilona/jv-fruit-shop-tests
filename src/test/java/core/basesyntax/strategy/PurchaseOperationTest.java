package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private Map<String, Integer> storage;
    private PurchaseOperation purchaseOperation;

    @BeforeEach
    void setUp() {
        storage = new HashMap<>();
        purchaseOperation = new PurchaseOperation();
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void handle_validPurchase_subtractsQuantity() {
        storage.put("apple", 100);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 40);
        purchaseOperation.handle(storage, transaction);
        assertEquals(60, storage.get("apple"));
    }

    @Test
    void handle_purchaseExactAmount_setsZero() {
        storage.put("apple", 50);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 50);
        purchaseOperation.handle(storage, transaction);
        assertEquals(0, storage.get("apple"));
    }

    @Test
    void handle_insufficientStock_throwsException() {
        storage.put("apple", 10);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 50);
        assertThrows(RuntimeException.class,
                () -> purchaseOperation.handle(storage, transaction));
    }

}