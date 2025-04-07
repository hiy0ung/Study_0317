package execption.은행계좌이체시스템;

public class BankDemo {
    public static void main(String[] args) {
        // 계좌 생성
        BankAccount account1 = new BankAccount("홍길동", 10000);
        BankAccount account2 = new BankAccount("김철수", 5000);
        BankAccount account3 = new BankAccount("이영희", 20000);

        try {
            account1.deposit(5000);
            account2.deposit(-5000);
        } catch (Exception e) {
            System.out.println("에외 발생 : " + e.getMessage());
        }
    }
}
