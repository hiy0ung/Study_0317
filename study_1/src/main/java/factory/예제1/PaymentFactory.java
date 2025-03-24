package factory.예제1;

// 3. 심플 팩토리
public class PaymentFactory {
    public static Payment createPayment(PaymentMethod method) {
        return switch (method) {
            // 에로우 케이스문? - break 필요없음 알아서 걸림
            case CREDIT_CARD -> new CreditCardPayment();
            case PAYPAL -> new PayPalPayment();
            default -> throw new IllegalStateException("Unsupported payment method");
        }; // 마침표 역할 스위치문 닫음 리턴 바로 하고 있으니까?
    }
}
