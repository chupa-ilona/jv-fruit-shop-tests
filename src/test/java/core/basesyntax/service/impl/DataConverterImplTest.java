package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static DataConverter converter;

    @BeforeAll
    static void setUp() {
        converter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_validData_ok() {
        List<String> input = new ArrayList<>();
        input.add("type,fruit,quantity");
        input.add("b,apple,10");
        input.add("s,orange,5");
        input.add("p,banana,15");
        input.add("r,orange,5");
        List<FruitTransaction> transactions = converter.convertToTransaction(input);
        assertEquals(4, transactions.size());
    }

    @Test
    void convertToTransaction_balanceOperation_ok() {
        List<String> input = List.of("type,fruit,quantity", "b,apple,10");
        List<FruitTransaction> transactions = converter.convertToTransaction(input);
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("apple", transactions.get(0).getFruit());
        assertEquals(10, transactions.get(0).getQuantity());
    }

    @Test
    void convertToTransaction_supplyOperation_ok() {
        List<String> input = List.of("type,fruit,quantity", "s,banana,50");
        List<FruitTransaction> transactions = converter.convertToTransaction(input);
        assertEquals(FruitTransaction.Operation.SUPPLY, transactions.get(0).getOperation());
        assertEquals("banana", transactions.get(0).getFruit());
        assertEquals(50, transactions.get(0).getQuantity());
    }

    @Test
    void convertToTransaction_purchaseOperation_ok() {
        List<String> input = List.of("type,fruit,quantity", "p,orange,15");
        List<FruitTransaction> transactions = converter.convertToTransaction(input);
        assertEquals(FruitTransaction.Operation.PURCHASE, transactions.get(0).getOperation());
        assertEquals("orange", transactions.get(0).getFruit());
        assertEquals(15, transactions.get(0).getQuantity());
    }

    @Test
    void convertToTransaction_returnOperation_ok() {
        List<String> input = List.of("type,fruit,quantity", "r,apple,5");
        List<FruitTransaction> transactions = converter.convertToTransaction(input);
        assertEquals(FruitTransaction.Operation.RETURN, transactions.get(0).getOperation());
        assertEquals("apple", transactions.get(0).getFruit());
        assertEquals(5, transactions.get(0).getQuantity());
    }

    @Test
    void convertToTransaction_emptyList_returnsEmpty() {
        List<String> input = List.of("type,fruit,quantity");
        List<FruitTransaction> transactions = converter.convertToTransaction(input);
        assertTrue(transactions.isEmpty());
    }

    @Test
    void convertToTransaction_invalidOperation_throwsException() {
        List<String> input = List.of("type,fruit,quantity", "x,banana,20");
        assertThrows(RuntimeException.class, () -> converter.convertToTransaction(input));
    }
}