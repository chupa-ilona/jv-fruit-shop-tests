package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private ShopServiceImpl shopService;

    @BeforeEach
    public void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        OperationStrategy strategy = new OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(strategy);
    }

    @Test
    public void process_balanceOperation_setsQuantity() {
        shopService.process(List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100)
        ));
        Assertions.assertEquals(100, shopService.getStorage().getStorage().get("apple"));
    }

    @Test
    public void process_supplyOperation_addsQuantity() {
        shopService.process(List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 50),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 30)
        ));
        Assertions.assertEquals(80, shopService.getStorage().getStorage().get("apple"));
    }

    @Test
    public void process_purchaseOperation_subtractsQuantity() {
        shopService.process(List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 40)
        ));
        Assertions.assertEquals(60, shopService.getStorage().getStorage().get("banana"));
    }

    @Test
    public void process_returnOperation_addsQuantity() {
        shopService.process(List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 50),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10)
        ));
        Assertions.assertEquals(60, shopService.getStorage().getStorage().get("banana"));
    }

    @Test
    public void process_multipleFruits_tracksEachSeparately() {
        shopService.process(List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20)
        ));
        Assertions.assertEquals(90, shopService.getStorage().getStorage().get("apple"));
        Assertions.assertEquals(107, shopService.getStorage().getStorage().get("banana"));
    }

    @Test
    public void process_emptyList_storageRemainsEmpty() {
        shopService.process(List.of());
        Assertions.assertTrue(shopService.getStorage().getStorage().isEmpty());
    }

    @Test
    public void getStorage_returnsCorrectStorageInstance() {
        Storage storage = shopService.getStorage();
        Assertions.assertNotNull(storage);
    }
}
