package gg.bayes.challenge.repository;


import gg.bayes.challenge.repository.entity.AttackLogEntry;

import java.util.List;

public interface AttackRepository extends LogRepository<AttackLogEntry> {
    List<AttackLogEntry> findAllByMatchIdAndHeroNameOrderByTimestamp(Long matchId, String heroName);
}
