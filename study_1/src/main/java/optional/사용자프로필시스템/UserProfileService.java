package optional.사용자프로필시스템;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserProfileService {
    // 사용자 아이디를 키로 사용하는 사용자 프로필 저장소
    private Map<String, UserProfile> userProfiles = new HashMap<>();

    public Optional<UserProfile> findUserById(String userId) {
        return Optional.ofNullable(userProfiles.get(userId));
    }

    public String getDisplayAddress(String userId) {
        return findUserById(userId)
                .map(UserProfile::getAddress)
                .map(address -> String.format("%s, %s, &s",
                        address.getStreet(),
                        address.getCity(),
                        address.getZipCode()))
                .orElse("주소 정보 없음"); // String 은 굳이 new 키워드로 생성하는 게 아니니까 orElseGet() 안 써도 됨
    }

    // 1. 사용자의 이메일을 반환하는 메소드 (아이디 기준)
    public String getUserEmail(String userId) {
        return findUserById(userId)
                .map(UserProfile::getEmail)
                .orElse("사용자 정보 없음");
        // 풀이
//        return findUserById(userId)
//                .map(UserProfile::getEmail)
//                .filter(email -> email.contains("@"))
//                .orElseThrow(() -> new IllegalArgumentException("Invalid email for user: " + userId));
    }



    // 2. 사용자의 이메일을 업데이트 하는 메소드 (아이디 기준)
    public void updateUserEmail(String userId, String newEmail) {
        findUserById(userId)
                .ifPresent(user -> user.setEmail(newEmail));
        // 풀이
//        findUserById(userId).ifPresent(user -> {
//            user.setEmail(newEmail);
//            System.out.println("이메일 업데이트 완료: " + newEmail);
//        });
    }

    // 3. 사용자 데이터를 저장소에 추가하는 메서드
    public void addUser(UserProfile userProfile) {
        userProfiles.put(userProfile.getUserId(), userProfile);
    }
}
