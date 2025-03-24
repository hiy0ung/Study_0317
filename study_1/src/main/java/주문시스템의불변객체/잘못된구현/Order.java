package 주문시스템의불변객체.잘못된구현;

import java.util.List;

// 다르게 사용하는 곳도 많지만 최대한 요새 트렌드에 맞게 사용해보자,,,!
public class Order {
        private String orderId;
        private List<String> items;
        private double totalAmount;

        // 전체 생성자 (alt + insert - constructor)
        public Order(String orderId, List<String> items, double totalAmount) {
            this.orderId = orderId;
            this.items = items; // 외부 리스트를 직접 참조하려고 함 (불변객체 사용 X)
            this.totalAmount = totalAmount;
        }

        public void addItem(String item) {
            items.add(item); // 상태가 변경이 가능함 (불변객체 사용 X)
            recalculateAmount();
        }

    private void recalculateAmount() {
        // 금액 재계산 로직
    }
}
