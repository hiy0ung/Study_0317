package stream.학생성적처리시스템;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* 학생 성보를 나타내는 클래스
* 이름, 점수, 성적 등급을 포함
*/
// @Data 는 웬만하면 안 쓰는 것을 추천 (알 수 없는 오류가 남)
@Getter
@Setter
@ToString
public class Student {
    private String name;
    private int score;
    private String grade; // A, B, C, D

    /**
     * 학생 객체를 생성하고 이름과 점수를 설정한 후에, 점수에 따른 성적등급을 자동으로 계산
     *
     * @param name 학생 이름
     * @param score 시험 점수 (0-100)
     */
    public Student(String name, int score) {
        this.name = name;
        this.score = score;
        this.grade = calculateGrade(score);
    }

    /**
     * 점수 에 따라 성적등급을 계산하는 메서드
     * 90점 이상: A
     * 80점 이상: B
     * 70점 이상: C
     * 70점 미만: D
     *
     * @param score 시험 점수
     * @return 성적 등급(A, B, C, D)
     */
    private String calculateGrade(int score) {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        else return "D";
    }
}
