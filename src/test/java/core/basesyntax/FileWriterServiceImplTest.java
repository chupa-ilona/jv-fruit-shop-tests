package core.basesyntax;

import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterServiceImplTest {
    private static final String TEST_FILE = "src/test/resources/test_output.csv";
    private FileWriterServiceImpl fileWriter;

    @BeforeEach
    public void setUp() {
        fileWriter = new FileWriterServiceImpl();
    }

    @Test
    public void write_validContent_writesToFile() throws IOException {
        String content = "fruit,quantity\napple,10\nbanana,5";
        fileWriter.write(content, TEST_FILE);
        String actual = Files.readString(Path.of(TEST_FILE));
        Assertions.assertEquals(content, actual);
    }

    @Test
    public void write_emptyContent_writesEmptyFile() throws IOException {
        fileWriter.write("", TEST_FILE);
        String actual = Files.readString(Path.of(TEST_FILE));
        Assertions.assertEquals("", actual);
    }

    @Test
    public void write_invalidPath_throwsRuntimeException() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileWriter.write("content", "/invalid/path/file.csv"));
    }

    @Test
    public void write_overwritesExistingFile() throws IOException {
        fileWriter.write("old content", TEST_FILE);
        fileWriter.write("new content", TEST_FILE);
        String actual = Files.readString(Path.of(TEST_FILE));
        Assertions.assertEquals("new content", actual);
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE));
    }
}
