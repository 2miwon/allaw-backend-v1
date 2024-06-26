package site.allawbackend.opinion.dto;

import java.time.LocalDateTime;

public record OpinionDto(
        Long id,
        String userName,
        int billsNo,
        String detail,
        int grade,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate
) {
}
