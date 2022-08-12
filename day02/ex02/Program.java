import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        if (args.length != 1){
            System.err.println("Error path");
            return;
        }
        String[] check = args[0].split( "=");
        if (!check[0].equals("--current-folder")){
            System.err.println("Error path");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        String[] files;
        String command;
        File directory = new File(check[1]);
        if(!directory.exists()) {
            System.err.println("Error path");
            return;
        }
        while(scanner.hasNextLine()){
            command = scanner.nextLine();
            if(command.equals("ls")){
                files = directory.list();
                File[] files22 = directory.listFiles();
                for(int i = 0; i < files.length; i++){
                    System.out.println(files[i] + " " + files22[i].length() + " KB");
                }
            }
            else if(command.regionMatches(0, "cd ", 0, 3)){
                String[] commands = command.split(" ");
                File directoryCheck;
                if(commands.length == 2) {
                    String newDirec = split_and_join(commands[1], directory);
                    directoryCheck = new File(newDirec);
                    if(directoryCheck.exists()){
                        directory = directoryCheck;
                        System.out.println(directory);
                    } else {
                        System.err.println("Error argument");
                    }
                } else {
                    System.err.println("Error argument");
                }
            }
            else if(command.regionMatches(0, "mv ", 0, 3)){
                String[] commands = command.split(" ");
                if(commands.length == 3) {
                    String str2_help = split_and_join(commands[2], directory);
                    String str1_help = split_and_join(commands[1], directory);
                    File file2 = new File(str2_help);
                    System.out.println(file2);
                    if (file2.isDirectory()) {
                        try {
                            Files.move(Paths.get(str1_help), Paths.get(str2_help + "/" + commands[1]));
                        } catch (IOException e) {
                            e.toString();
                        }
                    } else {
                        Path src = Paths.get(str1_help);
                        try {
                            Files.move(src, src.resolveSibling(str2_help));
                        } catch (IOException e) {
                            e.toString();
                        }
                    }
                } else {
                    System.err.println("Error mv");
                }
            }
            else if (command.equals("exit")) {
                scanner.close();
                return;
            }
            else {
                System.out.println("Error command");
            }
        }
    }

    public static String split_and_join(String str, File file){
        String dir = file.getAbsolutePath();
        int i = 0;
        String[] str2 = str.split("/");
        while (str2.length > i) {
            if (str2[i].equals("")) {
                file = file.getParentFile();
                dir = file.getAbsolutePath();
            }
            else {
                dir = dir + "/" + str2[i];
                file = new File(dir);
            }
            i++;
        }
        return dir;
    }

    public static File[] searchFile(String[] commands, File dir){
        String[] files = dir.list();
        if (files == null)
            return null;
        File[] files22 = dir.listFiles();
        if (files22 == null)
            return null;
        File[] ss = new File[2];
        for (int g = 0; g < 2; g++) {
            if (commands[g].indexOf('/') == -1) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].equals(commands[g]))
                        ss[g] = files22[i];
                }

            } else {
                String[] files123 = commands[g].split("/");
                for (int i = 0; i < files123.length; i++) {
                    String path;
                    if (files123[i].equals("")) {
                        ss[g] = dir.getParentFile();
                    } else {
                        if (ss[g] == null) {
                            ss[g] = new File(files123[i]);
                        }
                        path = ss[g].getPath();
                        ss[g] = new File(path + files123[i]);
                    }
                }
            }
        }
        return ss;
    }
}