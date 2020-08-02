package gg.bayes.challenge.service.impl;

import gg.bayes.challenge.repository.entity.AttackLogEntry;
import gg.bayes.challenge.repository.entity.CastAbilityLogEntry;
import gg.bayes.challenge.repository.entity.KillLogEntry;
import gg.bayes.challenge.repository.entity.PurchaseLogEntry;
import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItems;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MatchDetailsService {

    private final LogRepositoryService logRepositoryService;

    public MatchDetailsService(LogRepositoryService logRepositoryService) {
        this.logRepositoryService = logRepositoryService;
    }

    public List<HeroKills> getHeroKillsByMatchId(Long matchId) {
        List<KillLogEntry> logEntries = logRepositoryService.getKillLogsByMatchId(matchId);
        Map<String, HeroKills> heroKillsMap = new HashMap<>();
        logEntries.forEach(killLogEntry -> {
            HeroKills heroKills;
            if (heroKillsMap.containsKey(killLogEntry.getKilledBy())) {
                heroKills = heroKillsMap.get(killLogEntry.getKilledBy());
                Integer killCount = heroKills.getKills() + 1;
                heroKills.setKills(killCount);
            } else {
                heroKills = new HeroKills();
                heroKills.setKills(1);
                heroKills.setHero(killLogEntry.getKilledBy());
            }
            heroKillsMap.put(killLogEntry.getKilledBy(), heroKills);
        });
        return new ArrayList<>(heroKillsMap.values());
    }

    public List<HeroItems> getHeroItemsByMatchIdAndHeroName(Long matchId, String heroName) {
        List<PurchaseLogEntry> purchaseLogEntries = logRepositoryService.getPurchaseLogsByMatchIdAndHeroName(matchId, heroName);
        List<HeroItems> result = new ArrayList<>();
        purchaseLogEntries.forEach(purchaseLogEntry -> {
            HeroItems items = new HeroItems();
            items.setItem(purchaseLogEntry.getItemName());
            items.setTimestamp(purchaseLogEntry.getTimestamp());
            result.add(items);
        });
        return result;
    }

    public List<HeroSpells> getHeroSpellsByMatchIdAndHeroName(Long matchId, String heroName) {
        List<CastAbilityLogEntry> castAbilityLogEntries = logRepositoryService.getCastingLogsByMatchIdAndHeroName(matchId, heroName);
        Map<String, HeroSpells> heroSpellsMap = new HashMap<>();
        castAbilityLogEntries.forEach(castAbilityLogEntry -> {
            HeroSpells heroSpells;
            if (heroSpellsMap.containsKey(castAbilityLogEntry.getAbilityName())) {
                heroSpells = heroSpellsMap.get(castAbilityLogEntry.getAbilityName());
                Integer castingCount = heroSpells.getCasts() + 1;
                heroSpells.setCasts(castingCount);
            } else {
                heroSpells = new HeroSpells();
                heroSpells.setSpell(castAbilityLogEntry.getAbilityName());
                heroSpells.setCasts(1);
            }
            heroSpellsMap.put(castAbilityLogEntry.getAbilityName(), heroSpells);
        });
        return new ArrayList<>(heroSpellsMap.values());
    }

    public List<HeroDamage> getHeroDamagesByMatchIdAndHeroName(Long matchId, String heroName) {
        List<AttackLogEntry> attackLogEntries = logRepositoryService.getAttackLogsByMatchIdAndHeroName(matchId, heroName);
        Map<String, HeroDamage> attackMap = new HashMap<>();
        attackLogEntries.forEach(logEntry -> {
            HeroDamage heroDamage;
            if (attackMap.containsKey(logEntry.getOpponentName())) {
                heroDamage = attackMap.get(logEntry.getOpponentName());
                heroDamage.setDamageInstances(heroDamage.getDamageInstances() + 1);
                heroDamage.setTotalDamage(heroDamage.getTotalDamage() + logEntry.getDamage());

            } else {
                heroDamage = new HeroDamage();
                heroDamage.setTarget(logEntry.getOpponentName());
                heroDamage.setDamageInstances(1);
                heroDamage.setTotalDamage(logEntry.getDamage());
            }
            attackMap.put(logEntry.getOpponentName(), heroDamage);
        });
        return new ArrayList<>(attackMap.values());
    }
}
