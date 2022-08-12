public class Program {
    public static void main(String[] args) throws UserNotFoundException {

        UsersArrayList users = new UsersArrayList();

        int max_usrs = (int)(Math.random() * 150 + 15);
        System.out.println("Generating " + max_usrs + " users.");
        for (int i = 0; i < max_usrs; i++) {
            User tmp;
            if (i % 2 == 0) {
                tmp = new User("Man " + i, i);
            } else {
                tmp = new User("Woman " + i, i);
            }
            users.addUser(tmp);
        }

        try {
            System.out.println("User in the middle of array: " + users.retIndex(max_usrs / 2).toString());
        }
        catch (TypeNotPresentException e) {
            System.out.println(e.toString());
        }
        try {
            System.out.println("User with id 15: " + users.retId(15).toString());
        }
        catch (TypeNotPresentException e) {
            System.out.println(e.toString());
        }
        System.out.println("Total capacity: " + users.numUser());
    }
}