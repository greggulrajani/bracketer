package com.gulrajani.bracket.service;

import java.util.List;

import com.gulrajani.bracket.entity.Match;
import com.gulrajani.bracket.entity.User;

public interface MatchingStrategy {
    List<Match> match(List<User> users);
}
