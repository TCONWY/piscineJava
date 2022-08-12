

public class Program {

     public static void main(String[] args) {
         int sum = 479598;
         int res = 0;

         res += sum % 10;
         sum = sum / 10;
         res += sum % 10;
         sum = sum / 10;
         res += sum % 10;
         sum = sum / 10;
         res += sum % 10;
         sum = sum / 10;
         res += sum % 10;
         sum = sum / 10;
         res += sum % 10;

         System.out.println(res);
    }
}
