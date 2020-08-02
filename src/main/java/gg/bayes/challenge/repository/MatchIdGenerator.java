package gg.bayes.challenge.repository;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.UUID;

public class MatchIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor,
                                 Object o) throws HibernateException {
        Long currentTimeInMills = System.currentTimeMillis();
        return UUID.randomUUID().getMostSignificantBits() & currentTimeInMills;
    }
}
