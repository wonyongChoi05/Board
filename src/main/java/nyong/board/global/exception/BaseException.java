package nyong.board.global.exception;

// 모든 예외 클래스의 부모 클래스(ex; RunTimeException)
public abstract class BaseException extends RuntimeException{

    public abstract BaseExceptionType getExceptionType();
}
