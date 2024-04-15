package site.allawbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="opinion")
public class Opinion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billsNo")
    private Bills bills;

    @Getter
    @Setter
    @Column
    private String detail;

    @Getter
    @Setter
    @Column
    private int grade;

    public Opinion(User user, Bills bills, String detail, int grade) {
        this.user = user;
        this.bills = bills;
        this.detail = detail;
        this.grade = grade;
    }
}
