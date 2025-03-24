package builder.개선;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// from 필수
// 선택 매개변수 기본값을 전체 다
public class EmailMessage {
    private final String from;
    private final List<String> to;
    private final List<String> cc;
    private final String subject;
    private final String content;
    private final List<String> attachments;
    private final boolean urgent;
    private final LocalDateTime scheduledtime;

    private EmailMessage(Builder builder) {
        this.from = builder.from;
//        this.to = builder.to;
        this.to = List.copyOf(builder.to); // 방어적 복사? 주소를 바꾸지 않고 안에 있는 값만 가지고 오기 위해서 사용
//        this.cc = builder.cc;
        this.cc = List.copyOf(builder.cc);
        this.subject = builder.subject;
        this.content = builder.content;
//        this.attachments = builder.attachments;
        this.attachments = List.copyOf(builder.attachments);
        this.urgent = builder.urgent;
        this.scheduledtime = builder.scheduledtime;
    }

    // inner class 생성
    public static class Builder {
        private final String from;
        private List<String> to = new ArrayList<>();
        private List<String> cc = new ArrayList<>();
        private String subject = "";
        private String content = "";
        private List<String> attachments = new ArrayList<>();
        private boolean urgent = false;
        private LocalDateTime scheduledtime = LocalDateTime.now();

        public Builder(String from) {
            this.from = Objects.requireNonNull(from, "From address must not be null");
        }

        public Builder to(List<String> to) {
//            this.to = new ArrayList<>(to);
            this.to.addAll(to); // 이 방식으로 써도 됨
            return this;
        }
        public Builder cc(List<String> cc) {
//            this.to = new ArrayList<>(cc);
            this.cc.addAll(cc);
            return this;
        }
        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }
        public Builder content(String content) {
            this.content = content;
            return this;
        }
        public Builder attachments(List<String> attachments) {
            this.attachments = new ArrayList<>(attachments);
            return this;
        }
        public Builder urgent(boolean urgent) {
            this.urgent = urgent;
            return this;
        }
        public Builder scheduledtime(LocalDateTime scheduledtime) {
            this.scheduledtime = scheduledtime;
            return this;
        }

        public EmailMessage build() {
            return new EmailMessage(this);
        }

    }
}
