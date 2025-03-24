package factory.예제3.factory;

import factory.예제3.DocumentConverter;
import factory.예제3.PdfToWordConverter;
import factory.예제3.WordToPdfConverter;

public class WordToPdfConverterFactory extends ConverterFactory {
    @Override
    protected DocumentConverter createConverter() {
        System.out.println("WORD에서 PDF로 바꾸는 컨버터 생성");
        return new WordToPdfConverter();
    }
}
