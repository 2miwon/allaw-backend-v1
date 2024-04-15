package site.allawbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "party_id")
    private Party party;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;

    private int fulfillmentRate;
    private int participationRate;
    private int attendanceRate;
    private int voteRate;

    public void setDistrict(District district) {
        this.district = district;
        district.getMembers().add(this);
    }

    public void setParty(Party party) {
        this.party = party;
        party.getMembers().add(this);
    }


}
