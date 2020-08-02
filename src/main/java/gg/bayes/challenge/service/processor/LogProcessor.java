package gg.bayes.challenge.service.processor;

import gg.bayes.challenge.repository.entity.Log;

import java.text.ParseException;
import java.util.Optional;

public interface LogProcessor<T extends Log> {


    boolean canHandle(String line);

    Optional<T> processLine(String line, Long matchId) throws ParseException;
}
