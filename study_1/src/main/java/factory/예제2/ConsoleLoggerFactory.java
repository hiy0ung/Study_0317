package factory.예제2;

public class ConsoleLoggerFactory extends LoggerFactory{
    // 이중으로 추상화를 한 것...?
    @Override
    public Logger createLogger() {
        return new ConsoleLogger(LogLevel.INFO);
    }
}
