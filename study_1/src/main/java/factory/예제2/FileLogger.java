package factory.예제2;

public class FileLogger implements Logger {
    // 콘솔로거랑 좀 달라서 이중 추상화가 들어감?????
    // 필수로 하는 값이 다르기 때문에?
    // 쓰임새가 다르다
    private final String filePath;
    private final LogLevel logLevel;

    public FileLogger(String filePath, LogLevel logLevel) {
        this.filePath = filePath;
        this.logLevel = logLevel;
    }

    @Override
    public void log(String message) {
        // 파일에 로그 작성 로직
        System.out.println("[ " + logLevel + " ] " + filePath );
    }

    @Override
    public void error(String message) {
        // 파일에 에러 로그 작성 로직
    }

    @Override
    public LogLevel getLogLevel() {
        return logLevel;
    }
}
