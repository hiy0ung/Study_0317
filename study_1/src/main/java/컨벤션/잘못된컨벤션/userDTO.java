package 컨벤션.잘못된컨벤션;

public class userDTO {
    public String User_name;
    public int AGE;
    public String Phone_NUMBER;

    public void Setusername(String USER_NAME) {
        this.User_name = USER_NAME;
    }

    public boolean validateage() {
        if (AGE < 0)
            return false;
        else
            return true;
    }
}
