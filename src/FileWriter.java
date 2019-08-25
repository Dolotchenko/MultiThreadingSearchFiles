import javax.sound.midi.Soundbank;
import java.io.*;
import java.util.concurrent.ConcurrentSkipListSet;

public class FileWriter {

    public void fileWriter( ConcurrentSkipListSet<String> setResult){

         try (  BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Files.txt"),"cp1251"),8*1024 );
         ){
            //String text = "Hello  World!\nHey! Teachers! Leave them kids alone.";
             for(String str:setResult)

            bw.write(str);
             System.out.println("Запись выполнена");
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
