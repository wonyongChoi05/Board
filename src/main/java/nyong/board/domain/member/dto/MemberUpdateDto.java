package nyong.board.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@AllArgsConstructor
@Data
public class MemberUpdateDto {

    Optional<String> name;
    Optional<String> nickName;
    Optional<Integer> age;

}