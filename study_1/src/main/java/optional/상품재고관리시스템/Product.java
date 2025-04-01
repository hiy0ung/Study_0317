package optional.상품재고관리시스템;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String id;
    private String name;
    private int stock;
    private BigDecimal price; // BigDecimal ㅅㅏ용 이유는 금액 정확도 유지?
}
