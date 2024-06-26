package site.allawbackend.chat.service;

public class GptServiceException extends RuntimeException {
    public GptServiceException(String message) {
        super(message);
    }
}
