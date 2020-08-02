package gg.bayes.challenge.service.processor;

import gg.bayes.challenge.repository.entity.CastAbilityLogEntry;
import gg.bayes.challenge.repository.entity.LogType;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CastingLogProcessor extends BaseLogProcessor<CastAbilityLogEntry> {

    private static final String REGEX_PATTERN = "([0-9\\.\\:\\[\\]]{14}) ([A-Za-z0-9_]{1,255}) (casts ability) ([A-Za-z0-9_]{1,255}) (\\(lvl) ([0-9]{1,10})(\\) on) ([A-Za-z0-9_]{1,255})";

    @Override
    public Pattern getPattern() {
        return Pattern.compile(REGEX_PATTERN);
    }

    @Override
    protected CastAbilityLogEntry mapEntity(Matcher matcher, Long matchId) {
        CastAbilityLogEntry logEntry = new CastAbilityLogEntry();
        logEntry.setTimestamp(mapRelativeTimestamp(matcher.group(1)));
        logEntry.setHeroName(matcher.group(2));
        logEntry.setAbilityName(matcher.group(4));
        logEntry.setAbilityLevel(Integer.valueOf(matcher.group(6)));
        logEntry.setTargetHeroName(matcher.group(8));
        logEntry.setRawLog(matcher.group(0));
        logEntry.setLogType(LogType.CAST_ABILITY_LOG);
        logEntry.setMatchId(matchId);
        return logEntry;
    }

    @Override
    protected String getName() {
        return this.getClass().getSimpleName();
    }
}
