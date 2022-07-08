package nyong.board.domain.member.service.impl;

import lombok.RequiredArgsConstructor;
import nyong.board.domain.member.Member;
import nyong.board.domain.member.dto.MemberInfoDto;
import nyong.board.domain.member.dto.MemberSignUpDto;
import nyong.board.domain.member.dto.MemberUpdateDto;
import nyong.board.domain.member.dto.SecurityUtil;
import nyong.board.domain.member.repository.MemberRepository;
import nyong.board.domain.member.service.MemberService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(MemberSignUpDto memberSignUpDto) throws Exception {
        Member member = memberSignUpDto.toEntity();
        member.addUserAuthority();
        member.encodePassword(passwordEncoder);

        if (memberRepository.findByUsername(memberSignUpDto.getUsername()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        memberRepository.save(member);
    }

    @Override
    public void update(MemberUpdateDto memberUpdateDto) throws Exception {
        Member member = memberRepository.findByUsername(SecurityUtil.getLoginUsername())
                .orElseThrow(() -> new Exception("회원이 존재하지 않습니다"));

        memberUpdateDto.getName().ifPresent(member::updateName);
        memberUpdateDto.getNickName().ifPresent(member::updateNickName);
    }

    @Override
    public void updatePassword(String checkPassword, String toBePassword) throws Exception {
        Member member = memberRepository.findByUsername(SecurityUtil.getLoginUsername())
                .orElseThrow(() -> new Exception("회원이 존재하지 않습니다."));

        if (!member.matchPassword(passwordEncoder, checkPassword)){
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        member.updatePassword(passwordEncoder, toBePassword);
    }

    @Override
    public void withdraw(String checkPassword) throws Exception {
        Member member = memberRepository.findByUsername(SecurityUtil.getLoginUsername())
                .orElseThrow(() -> new Exception("회원이 존재하지 않습니다."));

        memberRepository.delete(member);
    }

    @Override
    public MemberInfoDto getInfo(Long id) throws Exception {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new Exception("회원이 없습니다."));

        return new MemberInfoDto(findMember);
    }

    @Override
    public MemberInfoDto getMyInfo() throws Exception {
        Member findMember = memberRepository.findByUsername(SecurityUtil.getLoginUsername())
                .orElseThrow(() -> new Exception("회원이 존재하지 않습니다."));

        return new MemberInfoDto(findMember);
    }
}
