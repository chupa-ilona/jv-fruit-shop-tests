package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private ReportGeneratorImpl reportGenerator;

    @BeforeEach
    public void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    public void generate_emptyStorage_returnsOnlyHeader() {
        Storage storage = new Storage(new HashMap<>());
        String report = reportGenerator.generate(storage);
        assertEquals("fruit,quantity\n", report);
    }

    @Test
    public void generate_singleFruit_containsCorrectLine() {
        Storage storage = new Storage(new HashMap<>(Map.of("apple", 100)));
        String report = reportGenerator.generate(storage);
        assertTrue(report.contains("fruit,quantity"));
        assertTrue(report.contains("apple,100"));
    }

    @Test
    public void generate_multipleFruits_containsAllLines() {
        Map<String, Integer> data = new HashMap<>();
        data.put("apple", 90);
        data.put("banana", 107);
        Storage storage = new Storage(data);
        String report = reportGenerator.generate(storage);
        assertTrue(report.contains("fruit,quantity"));
        assertTrue(report.contains("apple,90"));
        assertTrue(report.contains("banana,107"));
    }

    @Test
    public void generate_alwaysStartsWithHeader() {
        Storage storage = new Storage(new HashMap<>(Map.of("mango", 50)));
        String report = reportGenerator.generate(storage);
        assertTrue(report.startsWith("fruit,quantity\n"));
    }

    @Test
    public void generate_eachFruitOnSeparateLine() {
        Map<String, Integer> data = new HashMap<>();
        data.put("apple", 10);
        data.put("banana", 20);
        Storage storage = new Storage(data);
        String report = reportGenerator.generate(storage);
        String[] lines = report.split("\n");
        assertEquals(3, lines.length);
    }
}
