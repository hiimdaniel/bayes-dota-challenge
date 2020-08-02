package gg.bayes.challenge.repository.entity;

import lombok.Data;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "cast_ability_log")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "id"))
})
public class CastAbilityLogEntry extends Log {

    @Column(name = "ability_name")
    private String abilityName;

    @Column(name = "ability_level")
    private Integer abilityLevel;

    @Column(name = "target_hero_name")
    private String targetHeroName;

}
