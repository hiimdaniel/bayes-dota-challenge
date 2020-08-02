package gg.bayes.challenge.rest.controller;

import gg.bayes.challenge.repository.LogRepository;
import gg.bayes.challenge.repository.MatchRepository;
import gg.bayes.challenge.repository.entity.AttackLogEntry;
import gg.bayes.challenge.repository.entity.LogType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("it")
@WebAppConfiguration
public class MatchControllerIT {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private Map<LogType, LogRepository> repositoryMap;

    @Autowired
    private MatchRepository matchRepository;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        repositoryMap.values().forEach(CrudRepository::deleteAll);
        matchRepository.deleteAll();
    }

    @Test
    public void testProcessingPostRequestShouldResultLongMatchId() throws Exception {
        String testPostBody = "[00:36:44.722] npc_dota_hero_death_prophet hits npc_dota_hero_dragon_knight with death_prophet_exorcism for 25 damage (2229->2204)\n" +
            "[00:36:44.722] npc_dota_hero_dragon_knight hits npc_dota_hero_death_prophet with item_mjollnir for 121 damage (2007->1886)\n" +
            "[00:36:44.722] npc_dota_hero_dragon_knight hits npc_dota_hero_death_prophet with dota_unknown for 54 damage (1886->1832)";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/match")
            .contentType(MediaType.TEXT_PLAIN)
            .content(testPostBody))
            .andReturn();
        try {
            String resultString = result.getResponse().getContentAsString();
            Long resultMatchId = Long.valueOf(resultString);
        } catch (IllegalArgumentException e) {
            Assert.fail("Response could not be parsed to Long");
        }
    }

    @Test
    public void testPostRequestShouldSaveAttackToDB() throws Exception {
        String testPostBody = "[00:36:44.722] npc_dota_hero_death_prophet hits npc_dota_hero_dragon_knight with death_prophet_exorcism for 25 damage (2229->2204)\n" +
            "[00:36:33.048] npc_dota_hero_dragon_knight buys item item_buckler\n" +
            "[00:36:27.864] npc_dota_hero_bloodseeker uses item_tpscroll";
        LogRepository attackRepository = repositoryMap.get(LogType.ATTACK_LOG);
        Assert.assertTrue(attackRepository.findAll().isEmpty());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/match")
            .contentType(MediaType.TEXT_PLAIN)
            .content(testPostBody))
            .andReturn();

        String resultString = result.getResponse().getContentAsString();
        Long resultMatchId = Long.valueOf(resultString);

        Assert.assertEquals(1, attackRepository.findAll().size());
        AttackLogEntry logEntry = (AttackLogEntry) attackRepository.findAll().get(0);
        Assert.assertEquals("npc_dota_hero_death_prophet", logEntry.getHeroName());
        Assert.assertEquals("npc_dota_hero_dragon_knight", logEntry.getOpponentName());
        Assert.assertEquals("death_prophet_exorcism", logEntry.getAttackName());
        Assert.assertTrue(logEntry.getDamage() == 25);
        Assert.assertEquals(Integer.valueOf(2229), logEntry.getOpponentOriginalHealth());
        Assert.assertEquals(Integer.valueOf(2204), logEntry.getOpponentNewHeath());
        Assert.assertEquals(resultMatchId, logEntry.getMatchId());
        Assert.assertEquals("[00:36:44.722] npc_dota_hero_death_prophet hits npc_dota_hero_dragon_knight with death_prophet_exorcism for 25 damage (2229->2204)", logEntry.getRawLog());
    }
}
