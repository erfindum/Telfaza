package com.ahmedelouha.telfaza.matches;

import com.ahmedelouha.telfaza.data.Match;

import java.util.List;

/**
 * Created by raaja on 16-12-2017.
 */

public interface LeagueRepositoryPresenterContract {

    interface Presenter{

        void bindLeagueHolder(int position, LeagueHolderContract leagueHolder);

        void updateLeagues(List<List<Match>> leagueList);

        int getLeagueCount();

    }
}
