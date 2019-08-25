import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



class SearchFileThread implements Runnable {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
    private int startIndex;
    private int endIndex;
    private File[] listFiles;
    private List<String> fileList = new ArrayList<>();
    private TaskFinishListener listener;
    private List<String> fileListException = new ArrayList<>();

    public SearchFileThread(int startIndex, int endIndex, File[] listFiles, List<String> fileListException, TaskFinishListener listener) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.listFiles = listFiles;
        this.listener = listener;
        this.fileListException = fileListException;
    }

    public void run() {
        for (int i = startIndex; i < endIndex; i++) {
            if (!fileListException.contains("\"" + listFiles[i].getName() + "\"")) {//если имя файла не содержится в списке исключений
                fileList.add("[" + "\n" + "file=" + listFiles[i].getAbsolutePath() + "\n" + " date=" + dateFormat.format(new Date(listFiles[i].lastModified())) + "\n" + " size=" + listFiles[i].length() + "]");
            }
        }
        listener.onFinish(fileList);
    }
}

