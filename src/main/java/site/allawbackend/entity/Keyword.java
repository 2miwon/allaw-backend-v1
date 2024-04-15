package site.allawbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.allawbackend.dto.KeywordDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="keyword")
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String value;

    @OneToMany(mappedBy = "keyword", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Subscription> Subscriptions;

    public Keyword(String value) {
        this.value = value;
    }

    public KeywordDto toDto() {
        return new KeywordDto(this.id, this.value);
    }
}
