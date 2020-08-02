package gg.bayes.challenge.service.processor;

import gg.bayes.challenge.repository.entity.KillLogEntry;
import gg.bayes.challenge.repository.entity.LogType;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class KillLogProcessor extends BaseLogProcessor<KillLogEntry> {

    private final static String REGEX_PATTERN = "([0-9\\.\\:\\[\\]]{14}) ([A-Za-z0-9_]{1,255}) (is killed by) ([A-Za-z0-9_]{1,255})";

    @Override
    protected KillLogEntry mapEntity(Matcher matcher, Long matchId) throws ParseException {
        KillLogEntry logEntry = new KillLogEntry();
        logEntry.setTimestamp(mapRelativeTimestamp(matcher.group(1)));
        logEntry.setHeroName(matcher.group(2));
        logEntry.setKilledBy(matcher.group(4));
        logEntry.setRawLog(matcher.group(0));
        logEntry.setLogType(LogType.KILL_LOG);
        logEntry.setMatchId(matchId);
        return logEntry;

    }

    @Override
    protected String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected Pattern getPattern() {
        return Pattern.compile(REGEX_PATTERN);
    }
}
