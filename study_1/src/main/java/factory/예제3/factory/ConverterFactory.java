package factory.예제3.factory;

import factory.예제3.DocumentConverter;

public abstract class ConverterFactory {
    public final byte[] convertDocument(byte[] input) {
        DocumentConverter converter = createConverter();
        String outputFormat= converter.getOutputFormat();

        return converter.convert(input);
    }
    // 같은 패키지 내에서 사용할 수 있도록 private 대신 protected 사용
    protected abstract DocumentConverter createConverter();
}
