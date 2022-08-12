import java.util.Scanner;
public class Program {
    private static final int MAX_CHAR_CODES = 65_535;
    private static final int MAX_TOP_CHARS = 10;
    public static void main (String[] argc){
        Scanner scanner = new Scanner(System.in);
        int[] unicode = new int[65535];
        String input = scanner.nextLine();
        char[] murad = input.toCharArray();
        for(int i = 0; i < input.length(); i++)
            unicode[murad[i]]++;
        char[] str = getTopChars(unicode);
        unicode = new int[65535];
        for(int i = 0; i < input.length(); i++)
            unicode[murad[i]]++;
        int[] numbers = getTopInteger(unicode);
        printGraph(str, numbers);
        scanner.close();
    }
    public static void printGraph(char[] str, int[] numbers){
        int c = 0;
        int g = 10;
        for (int x = 0; x < MAX_TOP_CHARS; x++) {
            if (numbers[x] > 0){
                c++;
            }
        }
        if (numbers[0] < MAX_TOP_CHARS)
            g = numbers[0];
        if (g > 10)
            g = 10;
        for(int i = 0; i < (g + 2); i++){
            for(int f = 0; f < c; f++){
                if ((g + 2) - i == (int)(((float)g / (float)numbers[0] * (float)numbers[f]) + (float)2)) {
                    System.out.print(numbers[f] + "\t");
                }
                else if ((g + 2) - i == 1)
                    System.out.print(str[f] + "\t");
                else if ((g + 1) - i <= (((float)g / (float)numbers[0]) * (float)numbers[f]))
                    System.out.print("#" + "\t");
            }
            System.out.println();
        }
    }
    public static char[] getTopChars(int[] unicode){
        int max;
        int sd = 0;
        char[] str = new char[10];
        for (int i = 0; i < MAX_TOP_CHARS; i++){
            max = 0;
            for(int f = 0; f < MAX_CHAR_CODES; f++){
                if (max < unicode[f]){
                    max = unicode[f];
                    str[i] = (char)f;
                    sd = f;
                }
            }
            unicode[sd] = 0;
        }
        return (str);
    }
    public static int[] getTopInteger(int[] unicode)
    {
        int max;
        int sd = 0;
        int[] numbers = new int[10];
        for (int i = 0; i < MAX_TOP_CHARS; i++){
            max = 0;
            for(int f = 0; f < MAX_CHAR_CODES; f++){
                if (max < unicode[f]){
                    max = unicode[f];
                    numbers[i] = max;
                    sd = f;
                }
            }
            unicode[sd] = 0;
        }
        return (numbers);
    }
}