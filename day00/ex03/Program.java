import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int weekNum = 1;
        long grades_help = 0;
        long grades = 0;
        int min = 0;
        int maxWeek = 18;


        String inputWeek = scanner.nextLine();

        while (weekNum <= maxWeek && !inputWeek.equals("42")){
            if (!inputWeek.equals("Week " + weekNum)){
                System.err.println("invalid input");
                scanner.close();
                System.exit(-1);
            }
            min = minG(scanner);
            grades_help = min;
            if (grades_help == -1){
                System.err.println("invalid input");
                scanner.close();
                System.exit(-1);
            }
            for (int i = 1; i < weekNum; i++){
                grades_help *= 10;
            }
            grades += grades_help;
            weekNum++;
            inputWeek = scanner.nextLine();
        }
        for (int i = 1; i < weekNum; i++){
            System.out.println("Week" + i + " ");
            for (int j = 0; j < grade_help(i, grades); j++){
                System.out.print("=");
            }
            System.out.println(">");
        }
        scanner.close();
    }
    public static int minG(Scanner scanner) {
        int min = 10;
        int y;
        int maxGradesCount = 5;

        for (int i = 0; i < maxGradesCount; i++){
            if (scanner.hasNextInt()){
                y = scanner.nextInt();
                if (y < 0 || y > 9){
                    return -1;
                }
                if (y < min){
                    min = y;
                }
            }
            else{
                System.err.println("invalid input");
                scanner.close();
                System.exit(-1);
            }
        }
        scanner.nextLine();
        return (min);
    }

    public static int grade_help(int in, long grades){
        int ret;

        for(int i = 1; i < in; i++){
            grades /= 10;
        }
        ret = (int)(grades % 10);
        return ret;
    }
}
