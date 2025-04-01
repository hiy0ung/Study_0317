package strategy.할인정책시스템;

public class PercentageDiscount implements DiscountStrategy {
    private double percentage;

    // 할인 범위: 0보다 작거나, 100보다 크면 IllegalArgumentException 터트리기
    public PercentageDiscount(double percentage) {
        if (percentage < 0 || percentage < 100) {
            throw new IllegalArgumentException("0-100 범위여야 합니다.");
        }
        this.percentage = percentage;
    }

    @Override
    public double calculateDiscountAmount(double price) {
        return price * (percentage / 100);
    }
}
