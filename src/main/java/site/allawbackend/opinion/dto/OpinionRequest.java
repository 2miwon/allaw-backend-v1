package site.allawbackend.opinion.dto;

import jakarta.validation.constraints.NotBlank;

public record OpinionRequest(
        Long billId,
        @NotBlank(message = "의견을 적어주세요.")
        String detail,
        @NotBlank(message = "점수를 매겨주세요.")
        int grade
) {
}
