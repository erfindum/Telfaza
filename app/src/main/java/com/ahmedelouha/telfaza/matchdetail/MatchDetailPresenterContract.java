package com.ahmedelouha.telfaza.matchdetail;

import com.ahmedelouha.telfaza.data.Match;
import com.ahmedelouha.telfaza.data.StreamingLink;

import java.util.List;

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

         void displayMatchStreamList(String channelName, List<StreamingLink> streamingLinkList);

         void displayGoalLink(String url);
    }

    interface Presenter{

        void refreshMatch(String matchId);

        void openStreamList(Match match,int urlType);
    }
}
