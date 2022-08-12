import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int input = 0;
        boolean a = true;
        if (scanner.hasNextInt()){
            input = scanner.nextInt();
        }
        else{
            System.err.println("input error");
            System.exit(-1);
        }
        int step = 0;
        int sqrt;

        if (input <= 1) {
            System.err.println("Illegal Argument");
            System.exit(-1);
        }
        else if (input == 2){
            System.out.println(a + " " + "1");
        }
        else{
            sqrt = my_sqrt(input);
            for (int i = 2; i <= sqrt + 1; i++){
                step++;
                if (input % i == 0){
                    a = false;
                    System.out.println(a + " " + step);
                    scanner.close();
                    System.exit(0);;
                }
            }
            System.out.println(a + " " + step);
        }
        scanner.close();
        System.exit(0);
    }
    public static int my_sqrt(int i){
        long div = 1;
        long res = 0;
        while (i > 0){
            i -= div;
            div += 2;
            if (i >= 0){
                res++;
            }
        }
        return (int) res;
    }
}
