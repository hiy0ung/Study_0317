package factory.예제1;

import java.math.BigDecimal;

// 2. 구체적인 결제 구현
// implement 받아올 때 alt + enter
public class CreditCardPayment implements Payment {
    private PaymentStatus status = PaymentStatus.PENDING; // 기본값 결제대기

    @Override
    public void processPayment(BigDecimal amount) {
        // 신용카드 결제 로직
        System.out.println("카드");
        status = PaymentStatus.COMPLETED;
    }

    @Override
    public PaymentStatus getStatus() {
        return status;
    }
}
