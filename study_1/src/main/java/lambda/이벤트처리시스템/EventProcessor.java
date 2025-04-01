package lambda.이벤트처리시스템;

import java.util.*;

record Event(String userId) {

}

// 다양한 이벤트 타입 대해서 핸들러 등록하고 처리할 수 있는 기능을 제공
public class EventProcessor {
    /**
     * 이벤트 핸들러를 정의하는 함수형 인터페이스
     */
    // 요즘 많이 씀...?

    @FunctionalInterface
    interface EventHandler<T> {
        void handle(T event);
        // 내부에 퍼블릭한 함수(메소드) 하나만 있어야함
        // 여려기 만들고 싶으면 default 걸어줘야함
//        default void handle2() {
//
//        }
    }
    // 이벤트 타입별 핸들러 목록을 저장하는 맵
    private final Map<String, List<EventHandler>> eventHandlers = new HashMap<>();

    // 득점 이벤트 타입에 대한 핸들러를 등록하는 메서드
    public void registerHandler(String eventType, EventHandler<Event> handler) {
        // computeIfAbsent -> 키 값이 없으면 키 뒤에 default 값이 반환. 함수(람다식)가 올 수 있다
        //                    있으면 기존 value 반환
        eventHandlers.computeIfAbsent(eventType, k -> new ArrayList<>())
                .add(handler);
    }
    // 특정 이벤트를 처리하는 메서드
    public void processEvent(String eventType, Event event) {
        // getOrDefault -> 키 값을 기준으로 value 를 가지고 옴, 없으면 default 로 뭘 넣어줄건지 (람다가 들어오지 못함)
        //                 키가 없으면 빈 리스트를 반환하게 함
        eventHandlers.getOrDefault(eventType, Collections.emptyList())
                .forEach(handler -> handler.handle(event));
    }
    // 다양한 이벤트 핸들러를 등록하는 예시
    public void example() {
        // 로깅 핸들러
        registerHandler("USER_LOGIN", event -> System.out.println("User logged in: " + event.userId()));

        // 이메일 발송 핸들러
        registerHandler("USER_REGISTRATION", event -> System.out.println("Sending welcome email to: " + event.userId()));
    }

    // 이메일 발송 로직 sendEmail
    public void sendEmail(String userId) { // 굳이 어떤 이벤트가 일어나는 것과 연관이 있지는 않음
        System.out.println("Send email to: " + userId);
    }

    // 재고 업데이트 로직 updateInventory
    public void updateInventory(Event event) { // 이벤트가 일어나야지만 발생하는 로직
        System.out.println("Updating inventory for user: " + event.userId());
    }

    // 구매 확인 발송 로직 sendConfirmation
    public void sendConfirmation(Event event) { // 이벤트가 일어나야지만 발생하는 로직
        System.out.println("Sending purchase confirmation to: " + event.userId());

    }

    public static void main(String[] args) {
        EventProcessor eventProcessor = new EventProcessor();
        eventProcessor.example();
    }
}
