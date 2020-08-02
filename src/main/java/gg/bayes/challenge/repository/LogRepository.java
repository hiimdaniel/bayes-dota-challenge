package gg.bayes.challenge.repository;


import gg.bayes.challenge.repository.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LogRepository<T extends Log> extends JpaRepository<T, UUID> {
}
