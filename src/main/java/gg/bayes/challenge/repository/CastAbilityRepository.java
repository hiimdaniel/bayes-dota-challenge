package gg.bayes.challenge.repository;

import gg.bayes.challenge.repository.entity.CastAbilityLogEntry;

import java.util.List;

public interface CastAbilityRepository extends LogRepository<CastAbilityLogEntry> {

    List<CastAbilityLogEntry> findAllByMatchIdAndHeroName(Long matchId, String heroName);
}
