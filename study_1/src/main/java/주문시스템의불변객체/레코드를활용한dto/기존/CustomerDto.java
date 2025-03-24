package 주문시스템의불변객체.레코드를활용한dto.기존;

import java.util.Objects;

// java 14 이전에 사용하던 방식
public final class CustomerDto { // final 붙이지 않고 사용하기도 함 / 클래스는 막아두지 않기도 함
    private final String name;
    private final String email;
    private final String address;

    public CustomerDto(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    // alt + insert  equals() and hashcode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerDto that)) return false;
        return Objects.equals(name, that.name)
                && Objects.equals(email, that.email)
                && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, address);
    }

    // toString
    @Override
    public String toString() {
        return "CustomerDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
