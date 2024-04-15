package site.allawbackend.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter

public class District {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="region_id")
    private Region region;

    @OneToMany(mappedBy = "district")
    private List<Member> members = new ArrayList<>();

    public void setRegion(Region region) {
        this.region = region;
        region.getDistricts().add(this);
    }
}
