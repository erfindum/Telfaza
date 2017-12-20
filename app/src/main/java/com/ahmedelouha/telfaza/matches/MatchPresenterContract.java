package com.ahmedelouha.telfaza.matches;

import com.ahmedelouha.telfaza.data.Match;

import java.util.List;

/**
 * Created by raaja on 16-12-2017.
 */

public interface MatchPresenterContract {

    interface View{
        void showMatchDetail(String matchId);
    }

    interface Presenter{
        void bindMatchHolder(int position,MatchHolderContract matchHolder);
        void bindLeagueName( LeagueNameHolderContract leagueName);
        void onClickMatch(MatchHolderContract matchHolder,String matchId);
        void updateMatches(List<Match> matches);
        int  getMatchCount();
    }
}