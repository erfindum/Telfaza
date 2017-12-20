package com.ahmedelouha.telfaza.matches;

import com.ahmedelouha.telfaza.data.Match;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raaja on 17-12-2017.
 */

public class LeagueRepositoryPresenter implements LeagueRepositoryPresenterContract.Presenter {

    List<List<Match>> leagueList;

    LeagueRepositoryPresenter (){
        leagueList = new ArrayList<>();
    }


    @Override
    public void bindLeagueHolder(int position, LeagueHolderContract leagueHolder) {
        leagueHolder.setLeague(leagueList.get(position));
    }

    @Override
    public void updateLeagues(List<List<Match>> leagueList) {
        this.leagueList = leagueList;
    }

    @Override
    public int getLeagueCount() {
        return leagueList.size();
    }
}
