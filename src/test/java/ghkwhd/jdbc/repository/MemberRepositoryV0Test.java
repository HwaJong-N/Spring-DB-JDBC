package ghkwhd.jdbc.repository;

import ghkwhd.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        Member member = new Member("memberV0", 10000);

        // 저장
        repository.save(member);

        // 조회
        Member findMember = repository.findById(member.getMemberId());
        // @Data가 ToString을 적절히 오버라이딩해서 결과를 잘 보여주기 때문에 실제 데이터가 보인다
        log.info("findMember={}", findMember);
        // @Data에 EqualsHashCode가 있어서 객체 내용을 비교 ( equals 비교는 true )
        // 객체의 참조값은 다름 ( == 비교는 false )
        assertThat(findMember).isEqualTo(member);


        // 수정
        repository.update(member.getMemberId(), 20000);
        Member updatedMember = repository.findById(member.getMemberId());
        assertThat(updatedMember.getMoney()).isEqualTo(20000);


        // 삭제
        repository.delete(member.getMemberId());
        assertThatThrownBy(() -> repository.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
    }
}