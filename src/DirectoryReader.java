import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DirectoryReader {

    //итоговый список директорий для поиска
    private List<File> listDirectoryes = new ArrayList<>();
    //лист для удаления файлов которые нужно удалить из listDirectoryes
    private List<File> toRemove = new ArrayList<>();
    //список файлов которые исключаем из поиска
    private List<String> removeListFiles = new ArrayList<>();

    public List<File> getListDirectoryes() {
        return listDirectoryes;
    }

    public List<String> getRemoveListFiles() {
        return removeListFiles;
    }

    public void  fileReader(String fileName) {
        Pattern pattern, pattern2, pattern3;
        Matcher match, match2, match3;
        int lineCount = 0;

        try (
                // читаем файл из двоичного потока
                // в виде текста с нужной кодировкой
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "cp1251"))

        ) {
            String s;
            String strForParsing = "";

            pattern = Pattern.compile("\"(.*)\"-\"(.*)\"&\"(.*)\"");
            //ищем название директорий из строки
            pattern2 = Pattern.compile("([a-zA-Z]:)?((\\\\\\\\|\\\\)[a-zA-Z0-9()-_.\\s]+)+?");
            // ищем названия файлов которые нужно исключить из поиска
            pattern3 = Pattern.compile("\"\\w*\\.\\w*\"");
            // пока readLine() возвращает не null
            while ((s = br.readLine()) != null) {
                lineCount++;
                strForParsing = strForParsing + s;
            }
            match = pattern.matcher(strForParsing.replaceAll("\n", ""));
            if (match.find()) {
                //записываем для временного хранения строку с директориями для поиска
                String buf = match.group(1);
                match2 = pattern2.matcher(buf);
                while (match2.find()) {
                    listDirectoryes.add(new File(match2.group()));
                }
                //записываем для временого хранения строку с директориями которые нужно исключить
                buf = match.group(2);
                match2 = pattern2.matcher(buf);
                while (match2.find()) {
                    toRemove.add(new File(match2.group()));
                }
                //конечный список директорий, по которым будем искать файлы
                listDirectoryes.removeAll(toRemove);
                buf = match.group();
                match3 = pattern3.matcher(buf);
                while (match3.find()) {
                    removeListFiles.add(match3.group());
                }
            }
        } catch (Exception ex) {
            System.out.println("Reading error in line " + lineCount);
            ex.printStackTrace();
        }
    }
}
