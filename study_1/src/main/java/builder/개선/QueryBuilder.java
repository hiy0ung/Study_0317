package builder.개선;

import java.util.*;

// private 생성자
// static builder
// limit, offset 선택 (나머지 final)
// Builder (table만) > 필수 생성자?

// 강사님 풀이
public class QueryBuilder {
    private final String table;
    private final List<String> selectColumns;
    private final List<String> whereConditions;
    private final List<String> orderByColumns;
    private final Integer limit;
    private final Integer offset;

    // private 생성자
    private QueryBuilder(Builder builder) {
        this.table = builder.table;
        this.selectColumns = List.copyOf(builder.selectColumns); // 불변 객체(방어적 복사)
        this.whereConditions = List.copyOf(builder.whereConditions); // new ArrayList<>(builder.whereConditions) 사용해도 됨
        this.orderByColumns = List.copyOf(builder.orderByColumns);
        this.limit = builder.limit;
        this.offset = builder.offset;
    }

    // static builder
    public static class Builder {
        private final String table;
        private final List<String> selectColumns = new ArrayList<>();
        private final List<String> whereConditions = new ArrayList<>();
        private final List<String> orderByColumns = new ArrayList<>();
        private Integer limit;
        private Integer offset;

        public Builder(String table) {
            this.table = Objects.requireNonNull(table, "table must not be null");
        }

        // table
        // name, age, phoneNumber, email (컬럼)
        // String... >> 배열
        // > String[] columns = [name, age, phoneNumber, email]
        public Builder select(String... columns) {
            if (columns.length == 0) {
                this.selectColumns.add("*");
            } else {
                this.selectColumns.addAll(Arrays.asList(columns)); // asList(): 배열을 리스트로 변경
            }
            return this;
        }

        public Builder where(String condition) {
            this.whereConditions.add(condition);
            return this;
        }

        public Builder orderBy(String... columns) {
            this.orderByColumns.addAll(Arrays.asList(columns));
            return this;
        }

        public Builder limit(Integer limit) {
            if (limit < 0) {
                throw new IllegalStateException("Limit must be positive");
            }
            this.limit = limit;
            return this;
        }

        public Builder offset(Integer offset) {
            if (offset < 0) {
                throw new IllegalStateException("Offset must be positive");
            }
            this.offset = offset;
            return this;
        }

        public QueryBuilder build() {
            return new QueryBuilder(this);
        }
    }

    // 쿼리를 실행하기 위한 메소드
    // String을 자유자재로 넣었다 뺐다?
    public String buildQuery() {
        StringBuilder sb = new StringBuilder();
        // SELECT 절
        sb.append("SELECT ");
        // sb - "SELECT "
        // String.join(): , 기준으로 뒤에 오는 것들을 join 해줌
        // singletonList >> 싱글톤 패턴을 가지고 만드는 리스트, 리스트 가지고 있는 것을 하나를 만든다...?
        // "*"을 가지고 있는 리스트 하나를 만든다
        // 그게 아니면 selectColumns에 들어있는 값들 붙여줌
        // SELECT * or SELECT name, age, email
        sb.append(String.join(", ", selectColumns.isEmpty() ?
                Collections.singletonList("*") : selectColumns));

        // FROM 절
        sb.append(" FROM ").append(table);
        // 띄워쓰기 주의!!
        // "SELECT name, age, email FROM 테이블명"

        // WHERE 절
        if (!whereConditions.isEmpty()) {
            sb.append(" WHERE ")
                    .append(String.join(" AND ", whereConditions));
        }

        // ORDER BY 절
        if (!orderByColumns.isEmpty()) {
            sb.append(" ORDER BY ")
                    .append(String.join(", ", orderByColumns));
        }

        if (limit != null) {
            sb.append(" LIMIT ")
                    .append(limit);
        }

        if (offset != null) {
            sb.append(" OFFSET ")
                    .append(offset);
        }

        return sb.toString();
    }
}