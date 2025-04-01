package strategy.할인정책시스템;

public class FixedAmountDiscount implements DiscountStrategy {
    private double fixedAmount;

    // 0보다 크면 됨, 0보다 작으면 IllegalArgumentException 터트리기
    public FixedAmountDiscount(double fixedAmount) {
        if (fixedAmount < 0) {
            throw new IllegalArgumentException("할인 금액은 0원보다 커야합니다.");
        }
        this.fixedAmount = fixedAmount;
    }

    @Override
    public double calculateDiscountAmount(double price) {
        // 금액 계산할 때 원래 금액보다 할인 금액이 클 수는 없음
        return Math.min(fixedAmount, price);
    }
}
