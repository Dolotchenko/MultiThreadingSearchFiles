import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.concurrent.ConcurrentSkipListSet;


class FileWriterTest {
    private FileWriter fileWriter;
    ConcurrentSkipListSet<String> allFiles;

    @BeforeEach
    public void init() {
        fileWriter = new FileWriter();
        allFiles = new ConcurrentSkipListSet<>();
    }

    @Test
    void fileWriter() {
        allFiles.add("Тестовое слово");
        Assertions.assertTrue(allFiles.size()>0);
        fileWriter.fileWriter(allFiles);
    }
}