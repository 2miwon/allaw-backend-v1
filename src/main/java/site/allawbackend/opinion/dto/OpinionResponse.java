package site.allawbackend.opinion.dto;

import java.time.LocalDateTime;

public record OpinionResponse(
        Long id,
        String userName,
        int billsNo,
        String detail,
        int grade,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate
) {
}
