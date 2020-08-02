package gg.bayes.challenge.repository.entity;

import lombok.Data;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "purchase_log")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "id"))
})
public class PurchaseLogEntry extends Log {

    @Column(name = "item_name")
    private String itemName;
}
