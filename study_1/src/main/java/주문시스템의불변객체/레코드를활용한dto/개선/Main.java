package 주문시스템의불변객체.레코드를활용한dto.개선;

import 주문시스템의불변객체.레코드를활용한dto.기존.CustomerDto;

public class Main {
    public static void main(String[] args) {
        // 생성자가 없어도 가능
        CustomerRecord record  = new CustomerRecord("test", "test", "test");
        // toString()도 만들지 않아도 찍힘
        System.out.println(record.toString()); // [name=test, email=test, address=test]

        CustomerDto dto = new CustomerDto("test", "test", "test");
        // toString()이 없으면 주소값이 뜸
        System.out.println(dto.toString()); // {name='test', email='test', address='test'}
    }
}
