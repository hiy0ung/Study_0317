package strategy.파일압축시스템;

import lombok.AllArgsConstructor;

import java.io.File;
import java.util.List;

// 실행 시점(runtime 시점)에 어떤 알고리즘을 사용할지 결정이 됨
// > 전략 패턴의 핵심!
@AllArgsConstructor
public class FileCompressor {
    private CompressionStrategy strategy;

    public void CompressFiles(List<File> files, String destination) {
        strategy.compressFiles(files, destination);
    }
}
