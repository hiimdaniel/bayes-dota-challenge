package gg.bayes.challenge.service.impl;

import gg.bayes.challenge.exception.FileProcessingException;
import gg.bayes.challenge.repository.MatchRepository;
import gg.bayes.challenge.repository.entity.Log;
import gg.bayes.challenge.repository.entity.Match;
import gg.bayes.challenge.service.MatchService;
import gg.bayes.challenge.service.processor.LogProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MatchServiceImpl implements MatchService {


    private final MatchRepository matchRepository;

    private final List<LogProcessor> logProcessors;

    private final LogRepositoryService logRepositoryService;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository,
                            List<LogProcessor> logProcessors,
                            LogRepositoryService logRepositoryService) {
        this.matchRepository = matchRepository;
        this.logProcessors = logProcessors;
        this.logRepositoryService = logRepositoryService;
    }

    @Override
    public Long ingestMatch(String payload) throws FileProcessingException {
        if (payload != null && !payload.isEmpty()) {
            Instant currentTime = Instant.now();
            Match match = new Match();
            match.setTimestamp(currentTime);
            return matchRepository.save(match).getId();
        }
        throw new FileProcessingException("Input file could not be null or empty!");
    }

    @Override
    @Async
    public void processFile(String payload, Long matchId) throws FileProcessingException {
        try (BufferedReader reader = new BufferedReader(new StringReader(payload))) {
            String line = reader.readLine();
            while (line != null) {
                Optional<Log> extractedLogEntry = processLine(line, matchId);
                String finalLine = line;
                extractedLogEntry.ifPresentOrElse(logRepositoryService::save, () -> log.warn("Unable to process line by any line processor: {}", finalLine));
                line = reader.readLine();
            }
        } catch (IOException | ParseException exc) {
            log.error("An exception occurred during file processing: ", exc);
            throw new FileProcessingException("An exception occurred during file processing", exc);
        }
    }

    private Optional<Log> processLine(String line, Long matchId) throws ParseException {
        for (LogProcessor processor : logProcessors) {
            if (processor.canHandle(line)) {
                return processor.processLine(line, matchId);
            }
        }
        return Optional.empty();
    }
}
