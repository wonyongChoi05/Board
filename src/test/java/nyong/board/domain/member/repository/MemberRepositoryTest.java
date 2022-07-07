package nyong.board.domain.member.repository;


import nyong.board.domain.member.Member;
import nyong.board.domain.member.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @AfterEach
    public void after() throws Exception {
        em.clear();
    }

    @Test
    public void 회원저장_성공() throws Exception {
        // given
        Member member = Member.builder()
                .username("username")
                .password("1234")
                .name("MemberA")
                .nickName("Nyong")
                .role(Role.USER)
                .age(18)
                .build();

        // when
        Member saveMember = memberRepository.save(member);

        // then
        Member findMember = memberRepository.findById(saveMember.getId())
                .orElseThrow(() -> new RuntimeException("저장된 회원이 없습니다."));

        assertThat(findMember).isEqualTo(saveMember);
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void 오류_회원가입시_아이디가_없음() throws Exception {
        //given
        Member member = Member.builder()
                .password("1234567890")
                .name("Member1")
                .nickName("NickName1")
                .role(Role.USER)
                .age(22).
                build();

        //when, then
        assertThrows(Exception.class, () -> memberRepository.save(member));
    }
    
    @Test
    public void 성공_회원수정() throws Exception{
        // given
        Member member1 = Member.builder().username("username").password("1234567890").name("Member1").role(Role.USER).nickName("NickName1").age(22).build();
        memberRepository.save(member1);
        em.clear();

        String updatePassword = "updatePassword";
        String updateName = "updateName";
        String updateNickName = "updateNickName";
        int updateAge = 33;

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // when
        Member findMember = memberRepository.findById(member1.getId())
                .orElseThrow(Exception::new);

        findMember.updateName(updateName);
        findMember.updatePassword(passwordEncoder, updatePassword);
        findMember.updateNickName(updateNickName);
        em.flush();

        // then
        Member findUpdateMember = memberRepository.findById(findMember.getId()).orElseThrow(() -> new Exception());

        assertThat(findUpdateMember).isSameAs(findMember);
        assertThat(passwordEncoder.matches(updatePassword, findUpdateMember.getPassword())).isTrue();
        assertThat(findUpdateMember.getName()).isEqualTo(updateName);
        assertThat(findUpdateMember.getName()).isNotEqualTo(member1.getName());
    }
}