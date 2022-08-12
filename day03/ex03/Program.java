import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        int count1 = 0;
        ParsUrls urls = null;
        ArrayList<String> urlList;
        if (args.length == 1 && args[0] != null){
            count1 = pars_count(args);
        }
        if (count1 == 0){
            return;
        }
        urls = new ParsUrls();
        try {
            urls.downloadUrls("files_urls.txt", count1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int pars_count(String[] args){
        Scanner scanner = new Scanner(args[0]).useDelimiter("=");
        if(scanner.next().equals("--threadsCount")) {
            if (scanner.hasNextInt())
                return scanner.nextInt();
        }
        return 0;
    }
}
