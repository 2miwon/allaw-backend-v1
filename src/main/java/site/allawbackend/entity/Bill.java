package site.allawbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.allawbackend.common.BaseEntity;

@Getter
@Entity
@NoArgsConstructor
public class Bill extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private Long id;

    @Column(name = "bill_no")
    private Integer billNo;

    private String title;

    private String proposer;

    private String date;

    @Column(name = "file_link")
    private String fileLink;

    @Lob
    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;
    public Bill(Long id, Integer billNo, String title, String proposer, String date, String fileLink) {
        this.id = id;
        this.billNo = billNo;
        this.title = title;
        this.proposer = proposer;
        this.date = date;
        this.fileLink = fileLink;
    }

    public void saveSummary(String summary){
        this.summary = summary;
    }
}

