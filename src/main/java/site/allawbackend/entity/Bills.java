package site.allawbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@Entity
public class Bills {
    @Id
    private Integer billNo;

    private String billId;

    private String title;

    private String proposer;

    private String date;

    private String file_link;

    private ZonedDateTime created_at;

    private ZonedDateTime updated_at;
}
