import java.io.File;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int THREAD_COUNT = 20;
    private static int taskDoneCount = 0;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        ConcurrentSkipListSet<String> allFiles = new ConcurrentSkipListSet<>();//Выбрал этот set потому что он потокобезопасный

        DirectoryReader directoryReader = new DirectoryReader();
        FileWriter fileWriter = new FileWriter();
        directoryReader.fileReader("SettingsForScanning.txt");
        System.out.println(directoryReader.getListDirectoryes());
        for (File dir : directoryReader.getListDirectoryes()) {

            File[] files = dir.listFiles();
            int length = files.length;
            int onePart = length / THREAD_COUNT;
            long startTime = System.currentTimeMillis();

            for (int i = 0; i < THREAD_COUNT; i++) {
                // стартовый индекс списка файлов
                int startIndex = i * onePart;
                // конечный индекс списка файлов
                int endIndex = onePart * (i + 1);
                if (i == THREAD_COUNT - 1) {
                    endIndex = files.length;
                }
                System.out.println("Thread#" + (i + 1) + " start index:" + startIndex + ", end index:" + (endIndex - 1));
                executor.execute(new SearchFileThread(startIndex, endIndex, files, directoryReader.getRemoveListFiles(), (fileList) -> {
                    synchronized (Main.class) {
                        taskDoneCount++;
                        allFiles.addAll(fileList);
                        if (taskDoneCount == THREAD_COUNT * directoryReader.getListDirectoryes().size()) {// check if all tasks finished
                            executor.shutdown(); // shutdown the thread pool
                            //System.out.println("allFiles = " + allFiles);
                            fileWriter.fileWriter(allFiles);
                            System.out.println("allFiles.size() = " + allFiles.size());
                            System.out.println("Time used: " + (System.currentTimeMillis() - startTime) + "ms");
                        }
                    }
                }));
            }
        }

    }
}
