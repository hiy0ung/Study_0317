package 컨벤션.실무에서자주발생하는문제예시.개선된예시;

import java.util.Objects;
import java.util.Optional;

class Order1 {
    OrderStatus status;
    Customer1 customer1;

    public OrderStatus getStatus() {
        return status;
    }

    public Customer1 getCustomer1() {
        return customer1;
    }
}

class Customer1 {
    String name;

    public String getName() {
        return name;
    }
}

enum OrderStatus {
    COMPLETED;
}

public class OrderProcessor {
    public void process(Order1 order) {
        Objects.requireNonNull(order, "Order cannot be null");

        if (OrderStatus.COMPLETED.equals((order.getStatus()))) {
            // 처리로직
        }
        // 가지고 오는 클래스가 널이 될 수 있음
        // 널이면 어떤 처리르 하겠다 명시
        Customer1 customer1 = Optional.ofNullable(order.getCustomer1())
                .orElseThrow(() -> new IllegalArgumentException("Customer cannot be null"));
        String customerName = customer1.getName();
    }
}
