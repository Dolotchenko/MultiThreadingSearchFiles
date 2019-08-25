import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DirectoryReader {


    private Pattern pattern, pattern2, pattern3;
    private Matcher match,match2, match3;

    private List<File> listDirectoryes=new ArrayList<>();//итоговый список директорий для поиска
    private List<File> toRemove =new ArrayList<>();//лист для удаления файлов которые нужно удалить из listDirectoryes
    private List<String> removeListFiles =new ArrayList<>();//список файлов которые исключаем из поиска


    public List<File> getListDirectoryes() {
        return listDirectoryes;
    }
    public List<String> getRemoveListFiles() {
        return removeListFiles;
    }



    public List<File> fileReader(String fileName) {
        int lineCount = 0;

        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "cp1251"));
                // читаем файл из двоичного потока
                // в виде текста с нужной кодировкой
        ) {
            String s;
            String strForParsing="";

            pattern=Pattern.compile("\"(.*)\"-\"(.*)\"&\"(.*)\"");
            pattern2=Pattern.compile("([a-zA-Z]:)?((\\\\\\\\|\\\\)[a-zA-Z0-9()-_.\\s]+)+?");//ищем название директорий из строки
            pattern3=Pattern.compile("\"\\w*\\.\\w*\"");// ищем названия файлов которые нужно исключить из поиска
            while ((s = br.readLine()) != null) { // пока readLine() возвращает не null
                lineCount++;

              //  listDirectoryes.add(new File(s.trim()));
                System.out.println(s);
                strForParsing=strForParsing+s;
            }
            match=pattern.matcher(strForParsing.replaceAll("\n","" ));
            if (match.find()){
                String buf=match.group(1);//записываем для временного хранения строку с директориями для поиска
                match2 = pattern2.matcher(buf);
                while (match2.find()){
                    listDirectoryes.add(new File(match2.group()));
                }
                buf=match.group(2);//записываем для временого хранения строку с директориями которые нужно исключить
                match2 = pattern2.matcher(buf);
                while (match2.find()){
                    //System.out.println(match2.group());
                    toRemove.add(new File(match2.group()));
                }
                listDirectoryes.removeAll(toRemove);//конечный список директорий, по которым будем искать файлы
                buf=match.group();
                match3=pattern3.matcher(buf);
                //System.out.println(match3.find());
                while (match3.find()){
                    removeListFiles.add(match3.group());
                }

            }

        } catch (Exception ex) {
            System.out.println("Reading error in line " + lineCount);
            ex.printStackTrace();
        }
        return listDirectoryes;
    }

    }
