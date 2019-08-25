import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileFinder {

    int n = 0;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
List<File> fileList=new ArrayList<>();


    public List<File> listFilesForFolder(final File folder) {

        if (folder.listFiles() != null) {
            for (final File fileEntry : folder.listFiles()) {

                if (fileEntry.isDirectory()) {
                    listFilesForFolder(fileEntry);
                    fileList.add(fileEntry);
                } else {
                  //  System.out.print("["+"\n"+"file="+fileEntry+"\n"+" date="+dateFormat.format( new Date(fileEntry.lastModified()))+"\n"+" size="+fileEntry.length()+"]");

                }

            }
        }
        return fileList;

    }

//    public void listFileForListFolder(List<File> dirs){
//        for(File directory:dirs){
//            listFilesForFolder(directory);
//        }
//    }

    protected    <T> List<List<T>> chopped(List<T> list, final int L) {
        List<List<T>> parts = new ArrayList<List<T>>();
        final int N = list.size();
        for (int i = 0; i < N; i += L) {
            parts.add(new ArrayList<T>(
                    list.subList(i, Math.min(N, i + L)))
            );
        }
        System.out.println(parts);
        return parts;
    }




}
