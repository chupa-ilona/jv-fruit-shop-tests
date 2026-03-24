package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.impl.DataConverterImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class DataConverterImplTest {
    private static DataConverter converter;

    @BeforeAll
    public static void setUp() {
        converter = new DataConverterImpl();
    }

    @Test
    public void testConvertToTransaction_validData_OK() {
        List<String> input = new ArrayList<>();
        input.add("type,fruit,quantity");
        input.add("b,apple,10");
        input.add("s,orange,5");
        input.add("p,banana,15");
        input.add("r,orange,5");
        List<FruitTransaction> transactions = converter.convertToTransaction(input);
        Assertions.assertEquals(4, transactions.size());
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        Assertions.assertEquals("apple", transactions.get(0).getFruit());
        Assertions.assertEquals(10, transactions.get(0).getQuantity());
    }

    @Test
    public void testConvertToTransaction_negativeQuantity_throwsException() {
        List<String> input = new ArrayList<>();
        input.add("type,fruit,quantity");
        input.add("b,apple,-10");
        Assertions.assertThrows(IllegalArgumentException.class, () -> converter.convertToTransaction(input));
    }

    @Test
    public void convertToTransaction_emptyList_returnsEmpty() {
        List<String> input = List.of("type,fruit,quantity");
        List<FruitTransaction> result = converter.convertToTransaction(input);
        Assert.assertTrue(result.isEmpty());
    }
}
