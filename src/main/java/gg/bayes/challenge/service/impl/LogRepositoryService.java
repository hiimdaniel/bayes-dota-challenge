package gg.bayes.challenge.service.impl;

import gg.bayes.challenge.repository.AttackRepository;
import gg.bayes.challenge.repository.CastAbilityRepository;
import gg.bayes.challenge.repository.KillLogRepository;
import gg.bayes.challenge.repository.LogRepository;
import gg.bayes.challenge.repository.PurchaseRepository;
import gg.bayes.challenge.repository.entity.AttackLogEntry;
import gg.bayes.challenge.repository.entity.CastAbilityLogEntry;
import gg.bayes.challenge.repository.entity.KillLogEntry;
import gg.bayes.challenge.repository.entity.Log;
import gg.bayes.challenge.repository.entity.LogType;
import gg.bayes.challenge.repository.entity.PurchaseLogEntry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LogRepositoryService {

    private final Map<LogType, LogRepository> repositoryMap;

    private final AttackRepository attackRepository;

    private final CastAbilityRepository castAbilityRepository;

    private final KillLogRepository killLogRepository;

    private final PurchaseRepository purchaseRepository;

    public LogRepositoryService(Map<LogType, LogRepository> repositoryMap,
                                AttackRepository attackRepository,
                                CastAbilityRepository castAbilityRepository,
                                KillLogRepository killLogRepository,
                                PurchaseRepository purchaseRepository) {
        this.repositoryMap = repositoryMap;
        this.attackRepository = attackRepository;
        this.castAbilityRepository = castAbilityRepository;
        this.killLogRepository = killLogRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public void save(Log log) {
        repositoryMap.get(log.getLogType()).save(log);
    }

    public List<KillLogEntry> getKillLogsByMatchId(Long matchId) {
        return killLogRepository.findAllByMatchId(matchId);
    }

    public List<PurchaseLogEntry> getPurchaseLogsByMatchIdAndHeroName(Long matchId, String heroName) {
        return purchaseRepository.findAllByMatchIdAndHeroNameOrderByTimestamp(matchId, heroName);
    }

    public List<CastAbilityLogEntry> getCastingLogsByMatchIdAndHeroName(Long matchId, String heroName) {
        return castAbilityRepository.findAllByMatchIdAndHeroName(matchId, heroName);
    }

    public List<AttackLogEntry> getAttackLogsByMatchIdAndHeroName(Long matchId, String heroName) {
        return attackRepository.findAllByMatchIdAndHeroNameOrderByTimestamp(matchId, heroName);
    }

}
