package lambda.수학연산계산기;

import lambda.이벤트처리시스템.EventProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Calculator {
    // 람다식을 만들기 위해서는 interface를 만들어야됨
    // @FunctionalInterface 어노테이션 사용
    // >> 안에 메소드 하나
    @FunctionalInterface
    interface MathOperation {
        double calculate(double x, double y);
    }

    private final Map<String, MathOperation> operations = new HashMap<>();

    // 생성자
    // 생성자로 기본 계산기 만듦
    public Calculator() {
//        operations.put("add", (x, y) -> x + y)
        operations.put("add", Double::sum);
        operations.put("subtract", (x, y) -> x - y);
        operations.put("multiply", (x, y) -> x * y);
        operations.put("divide", (x, y) -> {
            if (y == 0) throw new ArithmeticException("Division by zero");
            return x / y;
        });

        // 고급 연산
        operations.put("power", Math::pow);
        operations.put("max", Math::max);
        operations.put("min", Math::min);
    }

    // calculate 메소드 만들기
    // Optional 을 사용해서 null check 를 하도록 반환할 때
    // 매개변수: double x, double y, operation key: String
    // 키가 존재하는지 먼저 찾기 > 널 체크 > 키에 대한 value 를 람다식,,,
    public double calculate(String operationKey, double x, double y) {
        return Optional.ofNullable(operations.get(operationKey)) // 넣은 키값이 있는지
                .orElseThrow(() -> new IllegalArgumentException("Unknown operation: " + operationKey))
                .calculate(x, y); // 위에 인터페이스 정의한 거
    }

    // 연산을 추가
    // 연산 이름, 연산 식
    // key, value(람다식)
    public void addOperation(String operationKey, MathOperation mathOperation) {
        operations.put(operationKey, mathOperation);
    }

    /**
     * 계산기 사용 예시를 보여주는 메서드
     */
    public void example() {
        // 기본 연산 사용
        System.out.println("5 + 3 = " + calculate("add", 5, 3));          // 출력: 8.0
        System.out.println("4 * 2 = " + calculate("multiply", 4, 2));     // 출력: 8.0
        System.out.println("2^3 = " + calculate("power", 2, 3));          // 출력: 8.0

        System.out.println("\n사용자 정의 연산 예시:");

        // 사용자 정의 연산 추가 - 평균 계산
        addOperation("average", (x, y) -> (x + y) / 2);
        System.out.println("평균(10, 20) = " + calculate("average", 10, 20));  // 출력: 15.0

        // 사용자 정의 연산 추가 - 백분율 계산
        addOperation("percentage", (x, y) -> (x / y) * 100);
        System.out.println("백분율(25, 100) = " + calculate("percentage", 25, 100));  // 출력: 25.0

        // 복합 연산 추가 - 두 수의 합과 곱의 제곱근 계산
        addOperation("complexCalc", (x, y) -> {
            double sum = x + y;
            double product = x * y;
            return Math.sqrt(sum * product);
        });
        System.out.println("복합연산(3, 4) = " + calculate("complexCalc", 3, 4));  // 출력: 5.29...
    }

    /**
     * 메인 메서드 - 예제 실행을 위한 진입점
     */
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.example();
    }
}
