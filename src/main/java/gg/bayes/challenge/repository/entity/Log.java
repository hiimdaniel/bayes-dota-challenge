package gg.bayes.challenge.repository.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@Data
public abstract class Log {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
        parameters = {@Parameter(
            name = "uuid_gen_strategy_class",
            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
        )
        })
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "hero_name")
    private String heroName;

    @Column(name = "timestamp")
    private Long timestamp;

    @Column(name = "raw_log")
    private String rawLog;

    @Column(name = "match_id")
    private Long matchId;

}
