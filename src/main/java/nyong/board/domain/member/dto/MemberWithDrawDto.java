package nyong.board.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Data
public class MemberWithDrawDto {

    @NotBlank (message = "비밀번호를 입력하세요")
    String checkPassword;
}
