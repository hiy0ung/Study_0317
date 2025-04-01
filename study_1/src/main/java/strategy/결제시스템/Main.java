package strategy.결제시스템;

public class Main {
    public static void main(String[] args) {
        // PaymentProcessor 에 NoArgs~ 안 붙어있으면 선언하면서 값 넣어줘야됨?
        PaymentStrategy strategy = new CreditCardPayment("test", "1234", "123", "20260505");
        PaymentProcessor processor = new PaymentProcessor(strategy);

        processor.pay(10000);
    }
}
