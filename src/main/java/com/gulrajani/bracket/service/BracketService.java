package com.gulrajani.bracket.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.NonNull;

import com.gulrajani.bracket.entity.Match;
import com.gulrajani.bracket.entity.Tournament;
import com.gulrajani.bracket.entity.User;

public class BracketService {
    private List<Match> matches;
    private Tournament tournament;
    private final Map<Tournament, List<Match>> tournaments = new HashMap<>();
    private final MatchingStrategy strategy;

    public BracketService(MatchingStrategy strategy) {
        this.strategy = strategy;
    }

    public void createTournament(String name) throws IllegalStateException {
        matches = new ArrayList<>();
        tournament = Tournament.builder().date(LocalDate.now()).name(name).users(new ArrayList<>()).build();
        tournaments.put(tournament, matches);
    }

    public void addUser(String email) {
        if (tournament.getUsers().stream().anyMatch(u -> u.getEmail().toLowerCase().equals(email.toLowerCase()))) {
            throw new IllegalArgumentException("Email already registered:" + email);
        }
        tournament.getUsers().add(User.builder().name(email).email(email).build());
    }

    private void generateBrackets(@NonNull List<Match> matches) {
        Match newMatch = null;
        List<Match> winners = new ArrayList<>();
        for (Match match : matches) {
            if (newMatch == null) {
                newMatch = Match.build(new User(), new User());
                match.setMatch(newMatch);
                winners.add(newMatch);
            } else {
                if (newMatch != null) {
                    match.setMatch(newMatch);
                    newMatch = null;
                }
            }
        }
        
        if (winners.size() == 1) {
            winners.get(0).setMatch(Match.build(null, null));
            return;
        } else {
            generateBrackets(winners);
        }
    }

    public boolean remove(long remover, long keeper) {
        for (Match match : matches) {
            if (removeMatch(remover, keeper, match)) {
                return true;
            }
        }
        return false;
    }

    private boolean removeMatch(long remover, long other, Match match) {
        if (match.remove(remover, other)) {
            return true;
        } else {
            if (match.getMatch().getUserId1() != null) {
                return removeMatch(remover, other, match.getMatch());
            }
        }
        return false;
    }


    public boolean advance(long winner, long loser) {
        for (Match match : matches) {
            if (advanceMatch(winner, loser, match)) {
                return true;
            }
        }
        return false;
    }

    private boolean advanceMatch(long winner, long loser, Match match) {
        if (match.advance(winner, loser)) {
            return true;
        } else {
            if (match.getMatch().getUserId1() != null) {
                return advanceMatch(winner, loser, match.getMatch());
            }
        }
        return false;
    }

    public List<Match> getBrackets() {
        return matches;
    }

    public void startTournament(String name) {
        Tournament tour = getTourament(name);
        List<User> users = tour.getUsers();
        List<Match> matches = strategy.match(users);
        generateBrackets(matches);
        tournaments.get(tour).addAll(matches);
    }

    List<User> getUsersForTournament(String name) {
        return getTourament(name).getUsers();
    }

    private Tournament getTourament(String name) {
        Optional<Tournament> tour = tournaments.keySet().stream().filter(t -> t.getName().equals(name)).findFirst();
        if (!tour.isPresent()) {
            throw new IllegalArgumentException("Tournament does not exist:" + name);
        }
        return tour.get();
    }
}
/*
 * [] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [a b] [c d] [ e f ] [g h] [ i j] [k l] [m n] [o p]
 */
