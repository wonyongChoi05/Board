package nyong.board.domain.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    // 추가로 Filter 에서 발생하는 예외는 ControllerAdvice 까지 넘어오지 않는다.
    // 스프링 시큐리티 필터에서 발생한 권한 없는 예외라던지 등은, 따로 Filter 의 handler 를 설정해 주어 처리해주어야 함.
    // 현재는 필터의 예외를 처리하는 핸들러(Security Config)를 만들지 않았기에 권한이 없는 경우 403 에러가 발생한다.

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleMemberEx(Exception exception){
        return new ResponseEntity(HttpStatus.OK);
    }
}
