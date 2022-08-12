public class UsersArrayList implements UsersList {
    User[] users = new User[10];
    int max = 10;
    int num = 0;

    @Override
    public void addUser(User us) {
        if (num == max){
            User[] users = new User[max * 2];
            for(int i = 0; i < max; i++){
                users[i] = this.users[i];
            }
            this.users = users;
            max *= 2;
        }
        this.users[this.num] = us;
        this.num++;
    }

    @Override
    public User retId(int id) throws UserNotFoundException {
        for (int i = 0; i < this.num; i++){
            if (this.users[i].getId() == id) {
                return this.users[i];
            }
        }
        throw new UserNotFoundException();
    }

    @Override
    public User retIndex(int in) throws UserNotFoundException {
        if (this.num >= in){
            return this.users[in];
        }
        throw new UserNotFoundException();
    }


    public int getCount(){
        return num;
    }

    @Override
    public int numUser() {
        return this.num;
    }
}
