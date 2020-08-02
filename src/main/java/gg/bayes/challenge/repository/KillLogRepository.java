package gg.bayes.challenge.repository;


import gg.bayes.challenge.repository.entity.KillLogEntry;

import java.util.List;

public interface KillLogRepository extends LogRepository<KillLogEntry> {
    List<KillLogEntry> findAllByMatchId(Long matchId);
}
