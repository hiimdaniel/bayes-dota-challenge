package gg.bayes.challenge.service.impl;

import gg.bayes.challenge.repository.LogRepository;
import gg.bayes.challenge.repository.entity.Log;
import gg.bayes.challenge.repository.entity.LogType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LogRepositoryService {

    private final Map<LogType, LogRepository> repositoryMap;

    public LogRepositoryService(Map<LogType, LogRepository> repositoryMap) {
        this.repositoryMap = repositoryMap;
    }

    public void save(Log log) {
        repositoryMap.get(log.getLogType()).save(log);
    }

}
