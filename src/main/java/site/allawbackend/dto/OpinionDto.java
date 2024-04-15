package site.allawbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OpinionDto {
    private Long id;
    private String userName;
    private int billsNo;
    private String detail;
    private int grade;
}
