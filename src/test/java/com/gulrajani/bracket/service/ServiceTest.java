package com.gulrajani.bracket.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.gulrajani.bracket.entity.Match;
import com.gulrajani.bracket.entity.User;

public class ServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private BracketService service;

    private List<User> users;

    @Before
    public void setUp() {
        service = new BracketService(new OrderedPlayerMatch());
        createTouramentWith8Teams();
        users = service.getUsersForTournament("new");
    }

    @Test
    public void verify_user_is_advanced_when_no_other_user_has_been() {
        assertTrue(service.advance(getUID(2), getUID(3)));
        List<Match> matches = service.getBrackets();
        assertEquals(getUID(2), matches.get(1).getMatch().getUserid2().getId());
        assertEquals(0L, matches.get(1).getMatch().getUserId1().getId());
    }

    @Test
    public void verify_second_bracket_is_merged_with_existing() {
        assertTrue(service.advance(getUID(0), getUID(1)));
        List<Match> matches = service.getBrackets();

        assertEquals(getUID(0), matches.get(1).getMatch().getUserId1().getId());
        assertEquals(0L, matches.get(1).getMatch().getUserid2().getId());
        assertTrue(service.advance(getUID(2), getUID(3)));
        assertEquals(getUID(2), matches.get(1).getMatch().getUserid2().getId());
    }

    @Test
    public void verify_second_player_can_be_selected() {
        assertTrue(service.advance(getUID(1),getUID(0)));
        List<Match> matches = service.getBrackets();

        assertEquals(getUID(1), matches.get(1).getMatch().getUserId1().getId());
        assertEquals(0L, matches.get(1).getMatch().getUserid2().getId());
    }
  

    @Test
    public void verify_only_one_user_from_bracket_can_advance() {
        assertTrue(service.advance(getUID(1), getUID(0)));
        List<Match> matches = service.getBrackets();

        assertEquals(getUID(1), matches.get(1).getMatch().getUserId1().getId());
        assertEquals(0L, matches.get(1).getMatch().getUserid2().getId());
        assertTrue(service.advance(getUID(0), getUID(1)));

        assertEquals(getUID(1), matches.get(1).getMatch().getUserId1().getId());
        assertEquals(0L, matches.get(1).getMatch().getUserid2().getId());
    }

    @Test
    public void verify_advance_player_to_third_level() {

        assertTrue(service.advance(getUID(0), getUID(1)));
        assertTrue(service.advance(getUID(2), getUID(3)));

        assertTrue(service.advance(getUID(4), getUID(5)));
        assertTrue(service.advance(getUID(6), getUID(7)));

        assertTrue(service.advance(getUID(2), getUID(0)));
        assertTrue(service.advance(getUID(4), getUID(6)));

        List<Match> matches = service.getBrackets();
        assertEquals(getUID(2), matches.get(1).getMatch().getMatch().getUserId1().getId());
        assertEquals(getUID(4), matches.get(1).getMatch().getMatch().getUserid2().getId());

    }

    @Test
    public void verify_lower_tier_is_bound_to_next_level_lower_tier() {
        assertTrue(service.advance(getUID(2), getUID(3)));
        assertTrue(service.advance(getUID(0), getUID(1)));
        List<Match> matches = service.getBrackets();

        assertEquals(getUID(2), matches.get(1).getMatch().getUserid2().getId());
        assertEquals(getUID(0), matches.get(1).getMatch().getUserId1().getId());

    }

    @Test
    public void verify_user_can_not_be_entered_twice() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Email already registered:greg");

        service.createTournament("new");
        service.addUser("greg@g.com");
        service.addUser("greg@g.com");

    }

    @Test
    public void verify_user_entered_twice_in_mixed_case_is_disallowed() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Email already registered:greg");

        service.createTournament("new");
        service.addUser("GREg@g.com");
        service.addUser("greg@g.com");
    }

    @Test
    public void verify_remove_single_node_with_no_parent_succeeds() {
        assertTrue(service.advance(getUID(2), getUID(3)));
        assertTrue(service.remove(getUID(2), 0));

        List<Match> matches = service.getBrackets();
        assertEquals(0, matches.get(1).getMatch().getUserid2().getId());
        assertEquals(0L, matches.get(1).getMatch().getUserId1().getId());
    }

    @Test
    public void verify_lower_node_can_not_be_removed() {
        assertTrue(service.advance(getUID(0), getUID(1)));
        assertTrue(service.advance(getUID(2), getUID(3)));
        List<Match> matches = service.getBrackets();

        assertEquals(getUID(2), matches.get(1).getMatch().getUserid2().getId());

        assertTrue(service.advance(getUID(2), getUID(0)));

        assertEquals(getUID(2), matches.get(1).getMatch().getMatch().getUserId1().getId());
        assertEquals(0, matches.get(1).getMatch().getMatch().getUserid2().getId());

        assertFalse(service.remove(getUID(2), getUID(3)));
        assertEquals(getUID(2), matches.get(1).getUserId1().getId());


    }

    private void createTouramentWith8Teams() {
        service.createTournament("new");
        service.addUser("greg");
        service.addUser("bob");
        service.addUser("xander");
        service.addUser("annabel");
        service.addUser("elijha");
        service.addUser("gregor");
        service.addUser("sasha");
        service.addUser("betty");
        service.addUser("fred");
        service.addUser("barney");
        service.addUser("dino");
        service.addUser("wilmha");
        service.addUser("george");
        service.addUser("elrow");
        service.addUser("nathan");
        service.addUser("wend");
        service.startTournament("new");

//        matches.add(Match.build(users.get(0), users.get(1)));
        //        matches.add(Match.build(users.get(2), users.get(3)));
        //        matches.add(Match.build(users.get(4), users.get(5)));
        //        matches.add(Match.build(users.get(6), users.get(7)));
        //        matches.add(Match.build(users.get(8), users.get(9)));
        //        matches.add(Match.build(users.get(10), users.get(11)));
        //        matches.add(Match.build(users.get(12), users.get(13)));
        //        matches.add(Match.build(users.get(14), users.get(15)));
    }

    //    @Test
    //    public void tes() throws IOException {
    //
    //        List<Tournament> tl = new ArrayList<>();
    //        tl.add(Tournament.builder().name("one").build());
    //        tl.add(Tournament.builder().name("two").build());
    //        tl.add(Tournament.builder().name("three").build());
    //        tl.add(Tournament.builder().name("four").build());
    //        tl.add(Tournament.builder().name("five").build());
    //
    //        ObjectMapper mapper = new ObjectMapper();
    //        String json = (mapper.writeValueAsString(tl));
    //        System.out.println(json);
    // 
    //
    //    }
    //
    //    @Test
    //    public void tes2() throws JsonParseException, JsonMappingException, IOException {
    //        String json =
    //                "[{\"id\":1,\"name\":\"one\",\"date\":null,\"atomicInteger\":5},{\"id\":2,\"name\":\"two\",\"date\":null,\"atomicInteger\":5},{\"id\":3,\"name\":\"three\",\"date\":null,\"atomicInteger\":5},{\"id\":4,\"name\":\"four\",\"date\":null,\"atomicInteger\":5},{\"id\":5,\"name\":\"five\",\"date\":null,\"atomicInteger\":5}]";
    //        ObjectMapper mapper = new ObjectMapper();
    //
    //        List<Tournament> readValue = mapper.readValue(json, new TypeReference<List<Tournament>>() {
    //        });
    //
    //        Tournament tournament = readValue.get(4);
    //        System.out.println(tournament.getId());
    //        Tournament a = Tournament.builder().name("six").build();
    //        System.out.println(a.getId());
    //    }
    //    

    private int getUID(int i) {
        return users.get(i).getId();
    }

    @Test
    public void sdf() {
        User user = new User();
        user.getId();
    }
}
