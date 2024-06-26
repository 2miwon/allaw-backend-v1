package site.allawbackend.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import site.allawbackend.chat.service.GptServiceException;
import site.allawbackend.common.ApiResponse;
import site.allawbackend.opinion.service.OpinionNotFoundException;

import static org.springframework.http.HttpStatus.*;
import static site.allawbackend.common.ApiResponse.error;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(UserNotAuthenticatedException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotAuthenticatedException(UserNotAuthenticatedException e) {
        log.error("사용자가 로그인하지 않았습니다.");
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(error(e.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFoundException(UserNotFoundException e) {
        log.error("사용자를 찾을 수 없습니다. user id = {}", e.getUserId());
        return ResponseEntity
                .status(NOT_FOUND)
                .body(error(e.getMessage()));
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnauthorizedUserException(UnauthorizedUserException e) {
        log.error("권한이 없는 사용자입니다. user id = {}", e.getUserId());
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(error(e.getMessage()));
    }

    @ExceptionHandler(BillNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleBillNotFoundException(BillNotFoundException e) {
        log.error("법안을 찾을 수 없습니다. bill id = {}", e.getBillId());
        return ResponseEntity
                .status(NOT_FOUND)
                .body(error(e.getMessage()));
    }

    @ExceptionHandler(BillMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleBillMismatchException(BillMismatchException e) {
        log.error("법안 정보가 일치하지 않습니다. bill id = {}", e.getBillId());
        return ResponseEntity
                .status(NOT_FOUND)
                .body(error(e.getMessage()));
    }

    @ExceptionHandler(OpinionNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleOpinionNotFoundException(OpinionNotFoundException e) {
        log.error("의견을 찾을 수 없습니다. opinion id = {}", e.getOpinionId());
        return ResponseEntity
                .status(NOT_FOUND)
                .body(error(e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException e) {
        log.error("처리 중 오류 발생: {}", e.getMessage());
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(error(e.getMessage()));
    }

    @ExceptionHandler(GptServiceException.class)
    public ResponseEntity<ApiResponse<Void>> handleGptServiceException(GptServiceException e) {
        log.error("GPT 서비스 응답 중 오류 발생: {}", e.getMessage());
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(error(e.getMessage()));
    }

}
