package site.allawbackend.opinion.service;

import lombok.Getter;

@Getter
public class OpinionNotFoundException extends RuntimeException{
    private final Long opinionId;

    public OpinionNotFoundException(Long opinionId) {
        super("존재하지 않는 의견입니다.");
        this.opinionId = opinionId;
    }
}
