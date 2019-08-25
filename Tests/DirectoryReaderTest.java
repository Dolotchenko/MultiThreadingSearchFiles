import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class DirectoryReaderTest {

    private DirectoryReader directoryReader;

    @BeforeEach
    private void init() {
        directoryReader = new DirectoryReader();
        directoryReader.fileReader("SettingsForScanning.txt");
    }

    @Test
    void getListDirectoryes() {
        assertTrue(directoryReader.getListDirectoryes().size() > 0);
    }

    @Test
    void getRemoveListFiles() {
        assertTrue(directoryReader.getRemoveListFiles().size() > 0);
    }

    @Test
    void fileReader() throws AssertionFailedError {
        try {
            assertThrows(AssertionFailedError.class, () -> {
            directoryReader.fileReader("my");
            });
        } catch (AssertionFailedError e) {
            e.getMessage();
        }
    }

}