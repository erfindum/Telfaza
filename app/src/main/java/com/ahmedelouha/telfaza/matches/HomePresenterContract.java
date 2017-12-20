package com.ahmedelouha.telfaza.matches;

import com.ahmedelouha.telfaza.data.Match;

import java.util.List;

/**
 * Created by raaja on 15-12-2017.
 */

public interface HomePresenterContract {

     interface View{
        void setPresenter(HomePresenterContract.Presenter presenter);

        void showSwipeRefresh();

        void hideSwipeRefresh();

        void updateNewMatches(List<List<Match>> completedMatches,List<List<Match>> onGoingMatches);

        void UpdateFailedRefresh();

    }

    interface Presenter{

        void refreshMatches();

    }
}
