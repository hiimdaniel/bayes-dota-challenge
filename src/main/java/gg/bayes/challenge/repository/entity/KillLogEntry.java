package gg.bayes.challenge.repository.entity;

import lombok.Data;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "kill_log")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "id"))
})
public class KillLogEntry extends Log {

    @Column(name = "killed_by")
    private String killedBy;
}
