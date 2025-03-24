package factory.예제1;

import java.math.BigDecimal;

// 4. 사용 예시
public class PaymentService {

    public void processPayment(PaymentMethod method, BigDecimal amount) {
        // 숨겨버림? (캡슐화)
        Payment payment = PaymentFactory.createPayment(method);
        payment.processPayment(amount);

        PaymentStatus status = payment.getStatus();
        // 결제 상태 처리
        System.out.println("status: " + status);
    }
}
