package gg.bayes.challenge.repository;


import gg.bayes.challenge.repository.entity.PurchaseLogEntry;

import java.util.List;

public interface PurchaseRepository extends LogRepository<PurchaseLogEntry> {
    List<PurchaseLogEntry> findAllByMatchIdAndHeroNameOrderByTimestamp(Long matchId, String heroName);
}
