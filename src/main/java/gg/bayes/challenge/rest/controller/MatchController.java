package gg.bayes.challenge.rest.controller;

import gg.bayes.challenge.exception.FileProcessingException;
import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItems;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;
import gg.bayes.challenge.service.MatchService;
import gg.bayes.challenge.service.impl.MatchDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/match")
public class MatchController {

    private final MatchService matchService;

    private final MatchDetailsService matchDetailsService;

    @Autowired
    public MatchController(MatchService matchService,
                           MatchDetailsService matchDetailsService) {
        this.matchService = matchService;
        this.matchDetailsService = matchDetailsService;
    }

    @PostMapping(consumes = "text/plain")
    public ResponseEntity<Long> ingestMatch(
        @RequestBody @NotNull @NotBlank String payload) throws FileProcessingException {
        log.info("Incoming file processing request");
        final Long matchId = matchService.ingestMatch(payload);
        log.info("New match has been created with ID: {}", matchId);
        matchService.processFile(payload, matchId);
        return ResponseEntity.ok(matchId);
    }

    @GetMapping("{matchId}")
    public ResponseEntity<List<HeroKills>> getMatch(@PathVariable("matchId") Long matchId) {
        log.info("Incoming Get HeroKills request with match ID: {}", matchId);
        return ResponseEntity.ok(matchDetailsService.getHeroKillsByMatchId(matchId));
    }

    @GetMapping("{matchId}/{heroName}/items")
    public ResponseEntity<List<HeroItems>> getItems(@PathVariable("matchId") Long matchId,
                                                    @PathVariable("heroName") String heroName) {
        log.info("Incoming Get HeroItems request with match ID: {} and hero name: {}", matchId, heroName);
        return ResponseEntity.ok(matchDetailsService.getHeroItemsByMatchIdAndHeroName(matchId, heroName));
    }

    @GetMapping("{matchId}/{heroName}/spells")
    public ResponseEntity<List<HeroSpells>> getSpells(@PathVariable("matchId") Long matchId,
                                                      @PathVariable("heroName") String heroName) {
        log.info("Incoming Get HeroSpells request with match ID: {} and hero name: {}", matchId, heroName);
        return ResponseEntity.ok(matchDetailsService.getHeroSpellsByMatchIdAndHeroName(matchId, heroName));
    }

    @GetMapping("{matchId}/{heroName}/damage")
    public ResponseEntity<List<HeroDamage>> getDamage(@PathVariable("matchId") Long matchId,
                                                      @PathVariable("heroName") String heroName) {
        log.info("Incoming Get HeroDamage request with match ID: {} and hero name: {}", matchId, heroName);
        return ResponseEntity.ok(matchDetailsService.getHeroDamagesByMatchIdAndHeroName(matchId, heroName));
    }
}
