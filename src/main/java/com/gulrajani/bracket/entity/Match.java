package com.gulrajani.bracket.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class Match extends Entity {
    private short refCt = 0;
    private boolean isFirst = false;
    @Setter
    private User userId1 = new User();
    @Setter
    private User userid2 = new User();
    private Match match;

    public static Match build(User user1, User user2) {
        Match newMatch = new Match();
        newMatch.setUserId1(user1);
        newMatch.setUserid2(user2);
        return newMatch;
    }

    public boolean advance(long winner, long loser) {
        User winnerUser;
        if (((winnerUser = getWinnerUser(winner, loser)) != null) && match.isEmpty()) {
            addUser(winnerUser);
            return true;
        }
        return false;
    }

    public boolean remove(long remover, long other) {
        User winnerUser;
        if (((winnerUser = getWinnerUser(remover, other)) != null) && match.isEmpty()) {
            return removeUser(winnerUser);
        }

        return false;
    }

    private boolean removeUser(User winnerUser) {
        if (winnerUser.equals(userId1)) {
            userId1 = new User();
            return true;
        } else if (winnerUser.equals(userid2)) {
            userid2 = new User();
            return true;
        }

        return false;
    }

    public void setMatch(Match match) {
        if (!match.equals(this.match)) {
            this.match = match;
            if (match.refCt == 0) {
                this.isFirst = true;
            }
            match.refCt++;
        }
    }

    private boolean addUser(User user) {
        if (match.getUserId1().equals(getUserId1()) ||
                match.getUserid2().equals(getUserId1()) ||
                match.getUserId1().equals(getUserid2()) ||
                match.getUserid2().equals(getUserid2())) {
            return false;
        }

        if (match.getUserId1().getId() == 0 && isFirst()) {
            match.setUserId1(user);
        } else {
            match.setUserid2(user);
        }
        return true;
    }

    private User getWinnerUser(long winner, long loser) {
        if (winner == userId1.getId() && loser == userid2.getId()) {
            return userId1;
        }

        if (winner == userid2.getId() && loser == userId1.getId()) {
            return userid2;
        }


        return null;
    }

    private boolean isEmpty() {
        if (userId1.getId() == 0 || userid2.getId() == 0) {
            return true;
        }

        return false;
    }




}
