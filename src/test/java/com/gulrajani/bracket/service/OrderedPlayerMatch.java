package com.gulrajani.bracket.service;

import java.util.ArrayList;
import java.util.List;

import com.gulrajani.bracket.entity.Match;
import com.gulrajani.bracket.entity.User;

/**
 * Matches users to match in seq order -- used for testing
 */
public class OrderedPlayerMatch implements MatchingStrategy {

    @Override
    public List<Match> match(List<User> users) {
        User[] usersary = users.toArray(new User[] {});

        List<Match> matches = new ArrayList<>();
        for (int i = 1; i < usersary.length; i += 2) {
            matches.add(Match.build(usersary[i - 1], usersary[i]));
        }
        return matches;
    }

}
