package site.allawbackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {

        @NotNull
        public String keyword;
        @NotNull
        private String subject;
        @NotNull
        private String messageBody;
}
