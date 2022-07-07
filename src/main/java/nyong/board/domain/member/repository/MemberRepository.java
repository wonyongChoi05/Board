package nyong.board.domain.member.repository;

import nyong.board.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

    // 존재 여부 확인
    boolean existsByUsername(String username);
}
