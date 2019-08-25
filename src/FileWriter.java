import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.ConcurrentSkipListSet;

public class FileWriter {

    public void fileWriter(ConcurrentSkipListSet<String> setResult) {

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Files.txt"), "cp1251"), 8 * 1024)
        ) {
            for (String str : setResult)
                bw.write(str);
            System.out.println("recording completed");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
