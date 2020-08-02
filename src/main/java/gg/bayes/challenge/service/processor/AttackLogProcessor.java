package gg.bayes.challenge.service.processor;

import gg.bayes.challenge.repository.entity.AttackLogEntry;
import gg.bayes.challenge.repository.entity.LogType;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AttackLogProcessor extends BaseLogProcessor<AttackLogEntry> {


    private static final String REGEX_PATTERN = "([0-9\\.\\:\\[\\]]{14}) ([A-Za-z0-9_]{1,255}) (hits) ([A-Za-z0-9_]{1,255}) (with) ([A-Za-z0-9_]{1,255}) (for) ([0-9]{1,10}) (damage) (\\()([0-9]{1,10})(->)([0-9]{1,10})(\\))";

    @Override
    protected AttackLogEntry mapEntity(Matcher matcher, Long matchId){
        AttackLogEntry logEntry = new AttackLogEntry();
        logEntry.setTimestamp(mapRelativeTimestamp(matcher.group(1)));
        logEntry.setHeroName(matcher.group(2));
        logEntry.setOpponentName(matcher.group(4));
        logEntry.setAttackName(matcher.group(6));
        logEntry.setDamage(Integer.valueOf(matcher.group(8)));
        logEntry.setOpponentOriginalHealth(Integer.valueOf(matcher.group(11)));
        logEntry.setOpponentNewHeath(Integer.valueOf(matcher.group(13)));
        logEntry.setRawLog(matcher.group(0));
        logEntry.setLogType(LogType.ATTACK_LOG);
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
