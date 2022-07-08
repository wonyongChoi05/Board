package nyong.board.domain.member.service;

import nyong.board.domain.member.dto.MemberInfoDto;
import nyong.board.domain.member.dto.MemberSignUpDto;
import nyong.board.domain.member.dto.MemberUpdateDto;

public interface MemberService {

    // 회원가입
    void signUp(MemberSignUpDto memberSignUpDto) throws Exception;

    // 정보수정
    void update(MemberUpdateDto memberUpdateDto) throws Exception;

    // 회원탈퇴
    void updatePassword(String checkPassword, String toBePassword) throws Exception;

    // 회원삭제
    void withdraw(String checkPassword) throws Exception;

    // 정보조회
    MemberInfoDto getInfo(Long id) throws Exception;

    // 정보조회
    MemberInfoDto getMyInfo() throws Exception;

}
