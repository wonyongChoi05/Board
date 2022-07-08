package nyong.board.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import nyong.board.domain.member.Member;

@AllArgsConstructor
@Data
public class MemberSignUpDto {

    private String username;
    private String password;
    private String name;
    private String nickName;
    private int age;

    @Builder
    public Member toEntity() {
        return Member.builder()
                .username(username)
                .password(password)
                .name(name)
                .nickName(nickName)
                .age(age)
                .build();
    }
}
