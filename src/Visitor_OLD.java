import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

public class Visitor_OLD implements Runnable {

    List<File> listForSearch;

    public ConcurrentSkipListSet<String> getSetResult() {
        return setResult;
    }

    ConcurrentSkipListSet<String> setResult;
    FileWriter fileWriter;


    DirectoryReader directoryReader;
    FileFinder fileFinder;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

    Visitor_OLD(List<File> dirsFromFile, ConcurrentSkipListSet<String> setResult){
        this.listForSearch=dirsFromFile;
        this.setResult=setResult;

    }

    @Override
    public void run() {

        System.out.println("Поток "+Thread.currentThread().getId()+" запущен ");
        listFileForListFolder(listForSearch);
    }



    public void listFilesForFolder(final File folder) {

        if (folder.listFiles() != null) {
            for (final File fileEntry : folder.listFiles()) {

                if (fileEntry.isDirectory()) {
                    listFilesForFolder(fileEntry);
                } else {
                    fileEntry.listFiles();
                    setResult.add("["+"\n"+"file="+fileEntry+"\n"+" date="+dateFormat.format( new Date(fileEntry.lastModified()))+"\n"+" size="+fileEntry.length()+"]");
                     //System.out.print("["+"\n"+"file="+fileEntry+"\n"+" date="+dateFormat.format( new Date(fileEntry.lastModified()))+"\n"+" size="+fileEntry.length()+"]");

                }

            }

        }

    }

        public void listFileForListFolder(List<File> dirs){
        for(File directory:dirs){
            listFilesForFolder(directory);
            System.out.println("Меня вызывали!!!");
        }
    }
}
