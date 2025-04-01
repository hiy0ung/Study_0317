package stream.로그분석기;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LogEntry {
    private LocalDateTime timestamp;
    private String level;
    private String message;
    private String source;
}
