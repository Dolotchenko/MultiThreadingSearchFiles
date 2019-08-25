import java.io.File;
import java.util.*;
import java.util.concurrent.*;

public class Main_OLD {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        DirectoryReader directoryReader=new DirectoryReader();
        FileFinder fileFinder= new FileFinder();
        FileWriter fileWriter=new FileWriter();
        directoryReader.fileReader("SettingsForScanning.txt");
        Collection<Future<?>> futures = new LinkedList();
        int countDoneThreads=0;
        //fileFinder.chopped(directoryReader.getListDirectoryes(),2 );

        ConcurrentSkipListSet<String> setFiles = new ConcurrentSkipListSet<String>();

        ExecutorService executorService= Executors.newFixedThreadPool(5);
        for(List<File> subDirs: fileFinder.chopped(directoryReader.getListDirectoryes(),1 )){//выбираем все поддиректории и создаем
            // для каждой поддеррии новый поток
            futures.add(executorService.submit(new Visitor_OLD(subDirs, setFiles)));

        }
        executorService.shutdown();
        //executorService.awaitTermination(20, TimeUnit.SECONDS);
        //System.out.println("прошло 20 секунд");
        //fileWriter.fileWriter(setFiles);
       // System.out.println(setFiles.size());
        for (Future<?> future : futures) {

                while (!future.isDone()) {
                    //ожидаем пока выполнится задача т.к. нет смысла писать в файл пока все файлы не будут отысканы и не упорядочены
                }
                if(future.isDone()) {
                    countDoneThreads++;
                    System.out.println(countDoneThreads);
                }
        }
        if(countDoneThreads==futures.size()){
            fileWriter.fileWriter(setFiles);
            System.out.println("Количество файлов "+setFiles.size());
            System.out.println("Запись в файл выполнена");
        }



















    }

}