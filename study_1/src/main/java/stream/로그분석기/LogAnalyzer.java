package stream.로그분석기;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LogAnalyzer {
    public static void main(String[] args) {
        List<LogEntry> logs = getLogs();

        // 1. 에러 로그 필터링 및 소스별 집계
//        List<LogEntry> filterByErrorLogAndSource = logs.stream()
//                .filter(log -> log.getLevel().equals("ERROR") && log.getSource().equals("app"))
//                .toList();
//        System.out.println("에러 필터링 및 소스별 집계 : ");
//        filterByErrorLogAndSource.forEach(log -> System.out.println(log.getMessage()));

        // 풀이
        // 이런 건 보통 쿼리로 처리
        // 전체 데이터를 다 가지고 와서 처리르 하는 거라서 비효율적임
        // 쿼리에서 먼저 필터링과 카운팅을 거치고 가지고 오는 것이 편함
        Map<String, Long> errorBySources = logs.stream()
                .filter(log -> "Error".equals(log.getLevel()))
                .collect(Collectors.groupingBy(
                        LogEntry::getSource,
                        Collectors.counting()
                ));

        // 2. 시간대별 로그 수 집계
        Map<Integer, Long> logsByHour = logs.stream()
                .collect(Collectors.groupingBy(
                        log -> log.getTimestamp().getHour(),
                        Collectors.counting()
                ));
        System.out.println("시간대별 로그 수 집계: ");
        logsByHour.forEach((hour, count) -> System.out.println(hour + "시 " + count + "건"));

        // 3. 특정 키워드를 포함한 로그 검색
        List<LogEntry> findLog = logs.stream()
                .filter(log -> log.getMessage().contains("Application"))
                .toList();

        System.out.println("특정 키워드를 포함한 로그 검색: ");
        findLog.forEach(msg -> System.out.println(msg.getMessage()));
        
        // 풀이
        String keyword = "exception";
        List<LogEntry> exceptionLogs = logs.stream()
                .filter(log -> log.getMessage().contains(keyword))
                .sorted(Comparator.comparing(LogEntry::getTimestamp)) // 어떤 걸 기준으로 정렬할건지
                .toList();

        // 4. 로그 레벨별 메시지 연결
        Map<String, List<String>> logLevelByMsg = logs.stream()
                .collect(Collectors.groupingBy(
                        LogEntry::getLevel,
                        Collectors.mapping(LogEntry::getMessage, Collectors.toList())
                ));
        System.out.println("로그 레벨 별 메시지 : ");
        logLevelByMsg.forEach((level, msg) -> System.out.println("레벨: " + level + "메세지: " + msg));
        
        // 풀이
        logs.stream()
                .collect(Collectors.groupingBy(
                        LogEntry::getLevel,
                        Collectors.mapping(
                                LogEntry::getMessage,
                                Collectors.joining("\n")
                        )
                ));
    }

    private static List<LogEntry> getLogs() {
        return List.of(
                new LogEntry(LocalDateTime.now(), "INFO", "Application started", "app"),
                new LogEntry(LocalDateTime.now(), "ERROR", "Division by zero", "app"),
                new LogEntry(LocalDateTime.now(), "ERROR", "Null pointer exception", "app"),
                new LogEntry(LocalDateTime.now(), "INFO", "Application stopped", "app")
        );
    }
}
