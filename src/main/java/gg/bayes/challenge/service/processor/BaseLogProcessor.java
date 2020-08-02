package gg.bayes.challenge.service.processor;

import gg.bayes.challenge.repository.entity.Log;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
public abstract class BaseLogProcessor<T extends Log> implements LogProcessor<T> {

    private static final String TIMESTAMP_FORMAT = "HH:mm:ss.SSS";

    @Override
    public boolean canHandle(String line) {
        Matcher matcher = getPattern().matcher(line);
        boolean result = matcher.find();
        if (result) {
            log.debug("{} can handle log entry: {}", getName(), line);
        }
        return result;
    }

    @Override
    public Optional<T> processLine(String line, Long matchId) throws ParseException {
        Matcher matcher = getPattern().matcher(line);
        if (matcher.find()) {
            return Optional.of(mapEntity(matcher, matchId));
        }
        return Optional.empty();
    }

    protected Long mapRelativeTimestamp(String rawTimestamp) {
        rawTimestamp = rawTimestamp.replace("[", "").replace("]", "");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT);
        LocalTime localTime = LocalTime.parse(rawTimestamp, formatter);
        return localTime.toNanoOfDay();
    }

    protected abstract T mapEntity(Matcher matcher, Long matchId) throws ParseException;

    protected abstract String getName();

    protected abstract Pattern getPattern();
}
