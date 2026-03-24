package core.basesyntax;

import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderServiceImplTest {
    private static final String TEST_FILE = "src/test/resources/test_input.csv";
    private FileReaderServiceImpl fileReader;

    @BeforeEach
    public void setUp() {
        fileReader = new FileReaderServiceImpl();
    }

    @Test
    public void read_validFile_returnsAllLines() throws IOException {
        Files.writeString(Path.of(TEST_FILE),
                "type,fruit,quantity\nb,apple,10\ns,banana,5");
        List<String> result = fileReader.read(new File(TEST_FILE));
        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals("type,fruit,quantity", result.get(0));
        Assertions.assertEquals("b,apple,10", result.get(1));
    }

    @Test
    public void read_emptyFile_returnsEmptyList() throws IOException {
        Files.writeString(Path.of(TEST_FILE), "");
        List<String> result = fileReader.read(new File(TEST_FILE));
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void read_nonExistentFile_throwsRuntimeException() {
        File nonExistent = new File("non_existent_file.csv");
        Assertions.assertThrows(RuntimeException.class,
                () -> fileReader.read(nonExistent));
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE));
    }
}
