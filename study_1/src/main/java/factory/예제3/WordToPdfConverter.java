package factory.예제3;

public class WordToPdfConverter implements DocumentConverter{
    @Override
    public byte[] convert(byte[] input) {
        // word에서 pdf로 변환하는 로직
        return input;
    }

    @Override
    public String getOutputFormat() {
        return "PDF";
    }
}
