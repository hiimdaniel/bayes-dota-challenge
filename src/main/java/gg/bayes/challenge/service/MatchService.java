package gg.bayes.challenge.service;

import gg.bayes.challenge.exception.FileProcessingException;

public interface MatchService {
    Long ingestMatch(String payload) throws FileProcessingException;

    void processFile(String payload, Long matchId) throws FileProcessingException;
}
