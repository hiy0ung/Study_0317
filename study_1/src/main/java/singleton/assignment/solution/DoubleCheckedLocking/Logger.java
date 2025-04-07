package singleton.assignment.solution.DoubleCheckedLocking;

import singleton.assignment.basecode.LogLevel;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    // volatile
    // > 인스턴스 생성 순서 보장
    // > 각 스레드가 최신 값을 보게 됨
    private static volatile Logger instance;

    // 로그 파일 경로 (기본값)
    private String logFilePath;

    // 로그 레벨 (DEBUG, INFO, WARNING, ERROR)
    private LogLevel minLogLevel;

    private Logger() {
        this.logFilePath = "application.log";
        this.minLogLevel = LogLevel.INFO;
    }

    // 필요할 때 인스턴스 생성
    // 멀티스레드 환경에서는 동시에 여러 스레드가 접근하면 인스턴스가 두 번 생성될 위험이 있음
    // getInstance 를 할 때마다 synchronized 락을 잡으므로 성능이 저하
    // > 1번 검사는 락 없이
    // > 동기화(synchronized) 블록 진입 후 락 안에서 2차 체크 후 인스턴스 생성
    public static Logger getInstance() {
        // 1번 검사 - 동기화 없이 빠르게 검사
        if (instance == null) {
            // 2번 검사 - 동기화 블록 (여러 스레드가 동시에 접근하는 것을 방지)
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    // 로거 초기화 메서드
    // 생성자랑 비슷하게 생겼지만 void 반환 타입
    // setter와 비슷한 역할
    public void initialize(String logFilePath, LogLevel level) {
        this.logFilePath = logFilePath;
        this.minLogLevel = level;
    }

    /**
     * 로그 메시지를 파일에 기록합니다.
     * @param level 로그 레벨
     * @param message 로그 메시지
     */
    public void log(LogLevel level, String message) {
        // 설정된 최소 레벨보다 낮은 레벨의 로그는 무시
        if (level.getValue() < minLogLevel.getValue()) {
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = dateFormat.format(new Date());
        String logEntry = String.format("[%s] [%s] %s%n", timestamp, level, message);

        try (PrintWriter writer = new PrintWriter(new FileWriter(logFilePath, true))) {
            writer.print(logEntry);
            // 콘솔에도 출력
            System.out.print(logEntry);
        } catch (IOException e) {
            System.err.println("로그 파일 작성 중 오류 발생: " + e.getMessage());
        }
    }

    /**
     * DEBUG 레벨 로그 메시지를 기록합니다.
     * @param message 로그 메시지
     */
    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    /**
     * INFO 레벨 로그 메시지를 기록합니다.
     * @param message 로그 메시지
     */
    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    /**
     * WARNING 레벨 로그 메시지를 기록합니다.
     * @param message 로그 메시지
     */
    public void warning(String message) {
        log(LogLevel.WARNING, message);
    }

    /**
     * ERROR 레벨 로그 메시지를 기록합니다.
     * @param message 로그 메시지
     */
    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    /**
     * ERROR 레벨 로그 메시지와 예외 스택 트레이스를 기록합니다.
     * @param message 로그 메시지
     * @param e 예외 객체
     */
    public void error(String message, Exception e) {
        log(LogLevel.ERROR, message + " - " + e.getMessage());
        // 예외 스택 트레이스 출력
        e.printStackTrace(System.err);
    }

    /**
     * 최소 로그 레벨을 설정합니다.
     * @param level 설정할 로그 레벨
     */
    public void setMinLogLevel(LogLevel level) {
        this.minLogLevel = level;
    }

    /**
     * 파일 경로를 설정합니다.
     * @param logFilePath 설정할 파일 경로
     */
    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    /**
     * 현재 설정된 최소 로그 레벨을 반환합니다.
     * @return 현재 최소 로그 레벨
     */
    public LogLevel getMinLogLevel() {
        return this.minLogLevel;
    }

    /**
     * 로그 파일 경로를 반환합니다.
     * @return 로그 파일 경로
     */
    public String getLogFilePath() {
        return this.logFilePath;
    }

    public static void reset() {

    }
}
