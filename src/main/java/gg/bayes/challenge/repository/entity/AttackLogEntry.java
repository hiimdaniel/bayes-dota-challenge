package gg.bayes.challenge.repository.entity;

import lombok.Data;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "attack_log")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "id"))
})
public class AttackLogEntry extends Log {

    @Column(name = "opponent_name")
    private String opponentName;

    @Column(name = "attack_name")
    private String attackName;

    @Column(name = "damage")
    private Integer damage;

    @Column(name = "opponent_original_health")
    private Integer opponentOriginalHealth;

    @Column(name = "opponent_new_health")
    private Integer opponentNewHeath;


}
