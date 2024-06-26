package site.allawbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.allawbackend.common.BaseEntity;
import site.allawbackend.dto.KeywordDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Keyword extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String content;

    @OneToMany(mappedBy = "keyword", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Subscription> Subscriptions;

    public Keyword(String content) {
        this.content = content;
    }

    public KeywordDto toDto() {
        return new KeywordDto(this.id, this.content);
    }
}
