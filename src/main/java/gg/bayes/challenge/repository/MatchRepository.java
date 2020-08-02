package gg.bayes.challenge.repository;


import gg.bayes.challenge.repository.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MatchRepository extends JpaRepository<Match, UUID> {
}
