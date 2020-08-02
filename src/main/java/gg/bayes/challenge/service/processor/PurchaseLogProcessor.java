package gg.bayes.challenge.service.processor;

import gg.bayes.challenge.repository.entity.LogType;
import gg.bayes.challenge.repository.entity.PurchaseLogEntry;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PurchaseLogProcessor extends BaseLogProcessor<PurchaseLogEntry> {

    private static final String REGEX_PATTERN = "([0-9\\.\\:\\[\\]]{14}) ([A-Za-z_]{1,255}) (buys item) ([A-Za-z0-9_]{1,255})";

    @Override
    public Pattern getPattern() {
        return Pattern.compile(REGEX_PATTERN);
    }

    @Override
    protected PurchaseLogEntry mapEntity(Matcher matcher, Long matchId) throws ParseException {
        PurchaseLogEntry logEntry = new PurchaseLogEntry();
        logEntry.setTimestamp(mapRelativeTimestamp(matcher.group(1)));
        logEntry.setHeroName(matcher.group(2));
        logEntry.setItemName(matcher.group(4));
        logEntry.setRawLog(matcher.group(0));
        logEntry.setLogType(LogType.PURCHASE_LOG);
        logEntry.setMatchId(matchId);
        return logEntry;
    }

    @Override
    protected String getName() {
        return this.getClass().getSimpleName();
    }
}
