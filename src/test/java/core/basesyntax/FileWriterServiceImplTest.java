package core.basesyntax;

import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterServiceImplTest {
    private FileWriterServiceImpl fileWriter;

    @BeforeEach
    public void setUp() {
        fileWriter = new FileWriterServiceImpl();
    }

    @Test
    public void write_validContent_writesToFile() throws IOException {
        Path tempFile = Files.createTempFile("test", ".csv");
        String content = "fruit,quantity\napple,10\nbanana,5";
        fileWriter.write(content, tempFile.toString());
        String actual = Files.readString(tempFile);
        Assertions.assertEquals(content, actual);
        Files.deleteIfExists(tempFile);
    }

    @Test
    public void write_emptyContent_writesEmptyFile() throws IOException {
        Path tempFile = Files.createTempFile("test", ".csv");
        fileWriter.write("", tempFile.toString());
        String actual = Files.readString(tempFile);
        Assertions.assertEquals("", actual);
        Files.deleteIfExists(tempFile);
    }

    @Test
    public void write_invalidPath_throwsRuntimeException() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileWriter.write("content", "/invalid/path/file.csv"));
    }

    @Test
    public void write_overwritesExistingFile() throws IOException {
        Path tempFile = Files.createTempFile("test", ".csv");
        fileWriter.write("old content", tempFile.toString());
        fileWriter.write("new content", tempFile.toString());
        String actual = Files.readString(tempFile);
        Assertions.assertEquals("new content", actual);
        Files.deleteIfExists(tempFile);
    }
}