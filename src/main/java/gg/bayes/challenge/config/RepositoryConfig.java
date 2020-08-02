package gg.bayes.challenge.config;

import gg.bayes.challenge.repository.AttackRepository;
import gg.bayes.challenge.repository.CastAbilityRepository;
import gg.bayes.challenge.repository.KillLogRepository;
import gg.bayes.challenge.repository.LogRepository;
import gg.bayes.challenge.repository.PurchaseRepository;
import gg.bayes.challenge.repository.entity.LogType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;

@Configuration
public class RepositoryConfig {

    private final AttackRepository attackRepository;

    private final CastAbilityRepository castAbilityRepository;

    private final KillLogRepository killLogRepository;

    private final PurchaseRepository purchaseRepository;

    public RepositoryConfig(AttackRepository attackRepository,
                            CastAbilityRepository castAbilityRepository,
                            KillLogRepository killLogRepository,
                            PurchaseRepository purchaseRepository) {
        this.attackRepository = attackRepository;
        this.castAbilityRepository = castAbilityRepository;
        this.killLogRepository = killLogRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Bean
    public Map<LogType, LogRepository> repositoryMap() {
        Map<LogType, LogRepository> repositoryMap = new EnumMap<>(LogType.class);
        repositoryMap.put(LogType.ATTACK_LOG, attackRepository);
        repositoryMap.put(LogType.CAST_ABILITY_LOG, castAbilityRepository);
        repositoryMap.put(LogType.KILL_LOG, killLogRepository);
        repositoryMap.put(LogType.PURCHASE_LOG, purchaseRepository);
        return repositoryMap;
    }
}
