package com.ahmedelouha.telfaza.matches;

import com.ahmedelouha.telfaza.data.Channel;
import com.ahmedelouha.telfaza.data.Match;

import java.util.List;

/**
 * Created by raaja on 15-12-2017.
 */

public interface HomePresenterContract {

     interface View{
        void setPresenter(HomePresenterContract.Presenter presenter);

        void showMatchSwipeRefresh();

        void hideMatchSwipeRefresh();

        void showChannelSwipeRefresh();

        void hideChannelSwipeRefresh();

        void updateNewMatches(List<List<Match>> completedMatches,List<List<Match>> onGoingMatches);

        void updateMatchFailedRefresh();

        void updateNewChannels(List<Channel> channelList);

        void updateChannelFailedRefresh();

    }

    interface Presenter{

        void refreshMatches();

        void refreshChannels();

    }
}
