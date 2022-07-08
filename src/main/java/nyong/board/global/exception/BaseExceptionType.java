package nyong.board.global.exception;

import org.springframework.http.HttpStatus;

public interface BaseExceptionType {

    // 에러코드
    int getErrorCode();

    // HTTP 상태
    HttpStatus getHttpStatus();

    // 에러메세지
    String getErrorMessage();
}
