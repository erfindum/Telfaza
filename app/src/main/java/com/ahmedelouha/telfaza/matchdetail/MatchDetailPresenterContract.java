package com.ahmedelouha.telfaza.matchdetail;

import com.ahmedelouha.telfaza.data.Match;

/**
 * Created by raaja on 20-12-2017.
 */

public interface MatchDetailPresenterContract {

     interface View{
         void setPresenter(MatchDetailPresenter presenter);

        void showSwipeRefresh();

         void hideSwipeRefresh();

         void updateMatchDetail(Match match);

         void updateMatchDetailFailed();

         void displayMatchStream(String Url);
    }

    interface Presenter{

        void refreshMatch(String matchId);

        void openMatchLink(Match match,int urlType);
    }
}
