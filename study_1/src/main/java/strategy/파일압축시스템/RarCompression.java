package strategy.파일압축시스템;

import java.io.File;
import java.util.List;

public class RarCompression implements CompressionStrategy {
    @Override
    public void compressFiles(List<File> files, String destination) {
        System.out.println("파일을 Rar 형식으로 압축합니다.");

        System.out.println("압축할 파일 수: " + files.size());

        System.out.println("압축 파일 저장 경로: " + destination);
    }
}
