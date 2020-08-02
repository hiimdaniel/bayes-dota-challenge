package gg.bayes.challenge.repository.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Data
@Table(name = "match")
public class Match {

    @Id
    @GeneratedValue(generator = "match-id-generator")
    @GenericGenerator(name = "match-id-generator",
        strategy = "gg.bayes.challenge.repository.MatchIdGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "timestamp")
    private Instant timestamp;
}
