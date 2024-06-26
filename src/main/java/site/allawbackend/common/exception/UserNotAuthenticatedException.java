package site.allawbackend.common.exception;

public class UserNotAuthenticatedException extends RuntimeException {
    public UserNotAuthenticatedException() {
        super("사용자가 로그인하지 않았습니다.");
    }
}
