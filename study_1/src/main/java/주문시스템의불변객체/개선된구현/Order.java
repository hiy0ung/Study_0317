package 주문시스템의불변객체.개선된구현;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Order {
        private final String orderId;
        private final List<String> items;
        private final double totalAmount;

    public Order(String orderId, List<String> items, double totalAmount) {
        this.orderId = Objects.requireNonNull(orderId, "OrderId must not be null"); // 널 유효성 검사
        this.items = List.copyOf(items); // 리스트를 복사한 새로운 객체에 주입 >> 불변 리스트 생성 (방어적 복사!)
        this.totalAmount = totalAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public List<String> getItems() {
        return items; // 불변 리스트
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public Order addItem(String item) { // 불변 리스트를 생성한 후 상태를 변경 시키고 생성자를 통해서 주입한 후 return
        List<String> newItems = new ArrayList<>(items); // items를 기준으로 복사해서 새로운 리스트
        newItems.add(item);
        return new Order(orderId, newItems, recalculateAmount(newItems));
    }

    private double recalculateAmount(List<String> items) {
        return items.size() * 10.0;
    }
}
