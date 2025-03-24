package 컨벤션.개선된컨벤션;

import java.util.Objects;

public class UserDto {
    private String username;
    private int age;
    private String phoneNumber;

//    public void setUsername(String username) {
//        this.username = username;
//    }
    public void setUsername(String username) {
        // null 체크
        this.username = Objects.requireNonNull(username, "Username cannot be null");
    }

    public boolean isValidage() {
        // 0보다 작으면 알아서 false 뱉음 (boolean 타입이니까)
        return age >= 0;
    }
}
