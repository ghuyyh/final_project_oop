<<<<<<< HEAD
<<<<<<< HEAD
=======


>>>>>>> 806005a (change directory)
=======
>>>>>>> 813a30b (update user class)
public abstract class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }

}
