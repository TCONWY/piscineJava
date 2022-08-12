public class Program {
    public static int MAX_USERS = 10;
    public static void main(String[] args) {
        for (int i = 0; i < MAX_USERS; i++) {
            User tmp;
            if (i % 2 == 0) {
                tmp = new User("Man", i);
            }	else {
                tmp = new User("Woman", i);
            }
            System.out.println("User number: '" + i + "', id: '" + tmp.getId() + "';");
        }
    }
}
