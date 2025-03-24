package 주문시스템의불변객체.레코드를활용한dto.개선;

import java.util.Objects;

public record CustomerRecord(
        String name,
        String email,
        String address
) {
    // 컴팩트 생성자 (record에서만 재공)
    // record 클래스는 생성자를 잘 만들지 않음
    // 만드는 것은 보통 유효성 감사
    public CustomerRecord {
        Objects.requireNonNull(name, "Name must not be null");
        Objects.requireNonNull(email, "Email must not be null");
        Objects.requireNonNull(address, "Address must not be null");

        if (!email.contains("@")) {
            throw new IllegalStateException("Invalid email format");
        }
    }
}

// final을 붙이지 않는 이유 >> 기본적으로 final이기 떄문에 굳이 붙이지 않아도 됨!
