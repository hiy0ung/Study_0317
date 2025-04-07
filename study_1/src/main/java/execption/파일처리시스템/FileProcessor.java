package execption.파일처리시스템;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileProcessor {

    public static class FileProcessingException extends Exception {
        public FileProcessingException(String message) {
            super(message);
        }

        public FileProcessingException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * 파일을 찾을 수 없을 때 발생하는 예외
     */
    public static class FileNotFoundException extends FileProcessingException {
        private final String filePath;

        public FileNotFoundException(String filePath) {
            super("File not found: " + filePath);
            this.filePath = filePath;
        }

        public String getFilePath() {
            return filePath;
        }
    }

    /**
     * 파일 내용 형식이 올바르지 않을 때 발생하는 예외
     */
    public static class InvalidFileFormatException extends FileProcessingException {
        private final String filePath;
        private final int lineNumber;

        public InvalidFileFormatException(String filePath, int lineNumber, String message) {
            super(String.format("Invalid format in file %s at line %d: %s",
                    filePath, lineNumber, message));
            this.filePath = filePath;
            this.lineNumber = lineNumber;
        }

        public String getFilePath() {
            return filePath;
        }

        public int getLineNumber() {
            return lineNumber;
        }
    }

    /**
     * 파일 접근 권한이 없을 때 발생하는 예외
     */
    public static class FileAccessDeniedException extends FileProcessingException {
        public FileAccessDeniedException(String filePath) {
            super("Access denied to file: " + filePath);
        }
    }

    /**
     * 파일 쓰기 오류 예외
     */
    public static class FileWriteException extends FileProcessingException {
        public FileWriteException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * 파일 명세 검증에 실패했을 때 발생하는 Runtime 예외
     */
    public static class FileSpecificationException extends RuntimeException {
        public FileSpecificationException(String message) {
            super(message);
        }
    }
    // 파일을 읽고 처리하는 메서드
    public List<String> readAndProcessFile(String filePath) throws FileProcessingException {
        List<String> result = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        }

        if (!file.canRead()) {
            throw new FileAccessDeniedException(filePath);
        }

        // 파일을 읽거나 영상을 주고 받을 때(IO 작업이 있을 때) 리소스 차지를 함
        // 끝날 때 close 처리를 해야하는데, 밑처럼 사용하면 close 신경 안 써도 됨
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            while((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    String processed = processLine(line, lineNumber, filePath);
                    if (processed != null) {
                        result.add(processed);
                    }
                } catch (InvalidFileFormatException e) {
                    System.out.println();
                }
            }
        } catch (java.io.FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    return result;
    }
    private String processLine(String line, int lineNumber, String filePath)
            throws InvalidFileFormatException {
        // 빈 라인 무시
        if (line.trim().isEmpty()) {
            return null;
        }

        // 주석 라인 무시 (# 으로 시작하는 라인)
        if (line.trim().startsWith("#")) {
            return null;
        }

        // 형식 검증 예제: 콤마로 구분된 값이 2개 이상 있어야 함
        String[] parts = line.split(",");
        if (parts.length < 2) {
            throw new InvalidFileFormatException(
                    filePath, lineNumber, "Line must contain at least two comma-separated values");
        }

        // 데이터 처리 로직 (예: 대문자로 변환)
        return line.toUpperCase();
    }
}
