package execption.은행계좌이체시스템;

import java.util.UUID;

/**
 * 은행 계좌 클래스 - Exception 처리 예제
 * 1. 사용자 정의 예외 (Custom Exception)
 * 2. 에외 계층 구조 (Exception Hierarchy)
 * 3. 체크 예외와 언체크 예외 (Checked vs Unchecked)
 * 4. 예외 포장 및 전환 (Exception Wrapping)
 * 5. 리소스 정리 (finally 블록)
 */
public class BankAccount {
    private String accountNumber;
    private String accountHolder; // 계좌 소유자
    private double balance;
    private boolean locked;
    private TransactionLogger logger;

    /**
     * 계좌 관련 기본 예외 클래스 - 모든 계좌 관련 예외의 부모 클래스
     * 내부 클래스로 만드는 이유 > 오늘 클래스가 너무 만ㅎ어서,,, 원래 밖에 해야됨
     */
    public static class BankAccountException extends Exception {
        public BankAccountException(String message) {
            super(message);
        }

        public BankAccountException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * 잔액 부족 예외 - 계좌에서 출금 가능한 금액보다 많은 금액을 출금하려 할 때 일어나는 exception
     */
    public static class InsufficientBalanceException extends BankAccountException {
        private final double requested; // final 준 이유는 변경될 가능성이 없음을 보장해주기 위해!
        private final double available;

        public InsufficientBalanceException(double requested, double available) {
            super(String.format("Insufficient balance. Requested: %.2f, Available: %.2f",
                    requested, available));
            this.requested = requested;
            this.available = available;
        }

        public double getRequested() {
            return requested;
        }

        public double getAvailable() {
            return available;
        }
    }
    /**
     * 유효하지 않은 송금액 예외 -> 0원 이하 금액이나 유효하지 않은 송금액일 때 발생
     */
    public static class InvalidTransferAmountException extends BankAccountException {
        public InvalidTransferAmountException(String message) {
            super(message);
        }
    }

    /**
     * 계좌 잠금 예외 - 계좌가 잠겨있을 때 작업을 시도할 경우 발생
     */
    public static class AccountLockedException extends BankAccountException {

        public AccountLockedException(String accountNumber) {
            super("Account is locked: " + accountNumber);
        }
    }

    // 프로그램이 돌아야지 나오는 exception 이기 떄문에 RuntimeException 사용
    public static class BankSystemException extends RuntimeException {
        public BankSystemException(String message) {
            super(message);
        }

        public BankSystemException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Transaction logger
     * 라이브러리가 있음 임의로 그냥 만든 거,,,
     */
    public interface TransactionLogger {
        void logTransaction(String message);
        void logError(String message, Throwable error);
    }

    private class ConsoleTransactionLogger implements TransactionLogger {

        @Override
        public void logTransaction(String message) {
            System.out.println("[Transaction] " + message);
        }

        @Override
        public void logError(String message, Throwable error) {
            System.out.println("[Error] " + message);
            if (error != null) {
                error.printStackTrace();
            }
        }
    }

    public BankAccount() {
        this.accountNumber = generateAccountNumber();
        this.balance = 0.0;
        this.locked = false;
        this.logger = new ConsoleTransactionLogger();
    }

    public BankAccount(String accountHolder, double initialBalance) {
        this(); // 위의 BankAccount 생성자
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }

    private String generateAccountNumber() {
        // 너무 길어서 8글자까지만 나오게
        return "ACCT-" + UUID.randomUUID().toString().substring(0, 8);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void deposit(double amount) throws InvalidTransferAmountException, AccountLockedException {
        try {
            if (amount <= 0) {
                throw new InvalidTransferAmountException("Deposit amount must be positive");
            }

            if (locked) {
                throw new AccountLockedException(accountNumber);
            }
            this.balance += amount;
            logger.logTransaction(String.format("Deposited %.2f to account %s. New balance: %.2f",
                    amount, accountNumber, balance));

        } catch (InvalidTransferAmountException | AccountLockedException e) {
            logger.logError("Deposit failed: " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.logError("Unexpected error during deposit", e);
            throw new BankSystemException("System error occurred during deposit operation");
        }
    }
}
