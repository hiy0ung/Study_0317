package strategy.결제시스템;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PayPalPayment implements PaymentStrategy {
    private String email; // paypal 계정
    private String password; // 비번


    @Override
    public void pay(int amount) {
        System.out.println(amount + "원을 PayPal로 결제합니다.");
        // 실제구현: PayPal API를 사용하여 결제 처리
    }
}
