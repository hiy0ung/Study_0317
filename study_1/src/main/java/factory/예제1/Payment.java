package factory.예제1;

import java.math.BigDecimal;

enum PaymentStatus {
    COMPLETED, // 결제 완료
    PENDING // 결제 대기
}

// 1. 결제 인터페이스
public interface Payment {
    // BigDecimal 동시성 관련해서 문제가 있을 수 잇기 때문에? (금액 동시성 제어 중요)
    void processPayment(BigDecimal amount);
    PaymentStatus getStatus();
}
