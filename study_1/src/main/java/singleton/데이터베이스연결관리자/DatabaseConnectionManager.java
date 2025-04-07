package singleton.데이터베이스연결관리자;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {
    // double-checked 방법
    // volatile 키워드: 멀티스레드 환경에서 변수의 가시성(visibility) 보장
    // 메인 메모리에서 저장되기 떄문에 다른 스레드에서 항상 최신 값을 읽을 수 있다.
    private static volatile DatabaseConnectionManager instance;

    // 데이터베이스 연결 객체
    private Connection connection;

    // 데이터베이스 접속 정보
    private final String url;
    private final String username;
    private final String password;

    // 싱글톤 패턴의 핵심요소 중 하나
    // 생성자 막아둬야됨? - private (아무곳에서나 인스턴스를 생성할 수 없음)
    private DatabaseConnectionManager() {
        this.url = "jdbc:mysql://localhost:3306/db";
        this.username = "user";
        this.password = "password";
    }

    public static DatabaseConnectionManager getInstance() {
        // 첫번째 검사 - 동기화 없이 빠른 검사
        if (instance == null) {
            // 여러 스레드가 동시에 이 블록에 접근하는 것을 방지
            synchronized (DatabaseConnectionManager.class) {
                // 두번째 검사 -> 동기화 블록 내에서 다시 검사
                if (instance == null) {
                    instance = new DatabaseConnectionManager();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }

    // 연결을 닫는 메서드
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            // GC(가비지 컬렉터)가 알아서 회수할 수 있도록 제거 (참조 제거)
            // close 를 시켜도 인스턴스가 살아있기 때문에 null 로 바꿔줘야 함
            connection = null;
        }
    }

    // 인스턴스 자체를 초기화하는 메서드
    // 테스트 코드에서만 사용
    // 운영하는 쪽에서는 사용하면 안 됨 (주의!!!! 해야됨)
    // 싱글톤은 전역에서 관리함 > 다른 코드에 영향을 끼칠 수 있으므로 주의 해야됨
    public static void reset() {
        synchronized (DatabaseConnectionManager.class) {
            if (instance != null) {
                try {
                    instance.closeConnection();
                } catch (SQLException e) {
                    // 로깅처리
                }
            }
            instance = null;
        }
    }
}
