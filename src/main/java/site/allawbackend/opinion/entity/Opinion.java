package site.allawbackend.opinion.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.allawbackend.common.BaseEntity;
import site.allawbackend.entity.Bill;
import site.allawbackend.entity.User;
import site.allawbackend.opinion.dto.OpinionDto;

@Getter
@NoArgsConstructor
@Entity
@Table(name="opinion")
public class Opinion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opinion_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @Column
    private String detail;

    //찬, 반으로 대체
    @Column
    private int grade;

    public Opinion(User user, Bill bill, String detail, int grade) {
        this.user = user;
        this.bill = bill;
        this.detail = detail;
        this.grade = grade;
    }

    public void update(String detail, int grade) {
        this.detail = detail;
        this.grade = grade;
    }
    public OpinionDto toDto() {
        return new OpinionDto(
                this.id,
                this.user.getName(),
                this.bill.getBillNo(),
                this.detail,
                this.grade,
                this.getCreatedDate(),
                this.getLastModifiedDate()
        );
    }
}
