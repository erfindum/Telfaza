package com.ahmedelouha.telfaza.matches;

import com.ahmedelouha.telfaza.data.Match;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raaja on 16-12-2017.
 */

public class MatchPresenter implements MatchPresenterContract.Presenter {
    List<Match> matchList;


    MatchPresenter(){
        matchList = new ArrayList<>();
    }

    @Override
    public void bindMatchHolder(int position, MatchHolderContract matchHolder) {
        Match match = matchList.get(position-1);
        matchHolder.setTeamLogos(match.team1image,match.team2image);
        matchHolder.setShouldDisplayTime(match.shouldDisplayTime);
        matchHolder.setMatchTime(match.date,match.time);
        matchHolder.setMatchId(match.id);
        matchHolder.setPresenter(this);
        matchHolder.setChannel(match.channelname);
        matchHolder.setScores(match.score1,match.score2);
        matchHolder.setStatus(match.status);
        matchHolder.setTeamNames(match.team1name,match.team2name);
    }

    @Override
    public void bindLeagueName(LeagueNameHolderContract leagueName) {
        Match match = matchList.get(0);
        leagueName.setLeagueLogo(match.leagueimage);
        leagueName.setLeagueName(match.leaguename);
    }

    @Override
    public void onClickMatch(MatchHolderContract matchHolder, String matchId) {
        matchHolder.showMatchDetail(matchId);
    }

    @Override
    public void updateMatches(List<Match> matches) {
        matchList = matches;
    }

    @Override
    public int getMatchCount() {
        if (matchList.isEmpty()){
            return 0;
        }
        return matchList.size()+1;
    }
}
