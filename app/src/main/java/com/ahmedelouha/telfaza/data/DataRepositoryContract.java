package com.ahmedelouha.telfaza.data;

import java.util.List;

/**
 * Created by raaja on 15-12-2017.
 */

public interface DataRepositoryContract {

    interface OnMatchRefreshedCallback{
        void onMatchRefresh(List<List<Match>> oldMatches,List<List<Match>> currentMatches);
        void onRefreshFailed();
    }

    interface onSelectedMatchRefreshedCallback{
        void onSelectedMatchRefreshed(Match match);
        void onRefreshFailed();
    }

    interface onChannelRefreshedCallback{
        void onChannelRefreshed(List<Channel> channelList);
        void onRefreshFailed();
    }

    void refreshMatches(OnMatchRefreshedCallback callback);

    void refreshSelectedMatch(String matchId, onSelectedMatchRefreshedCallback callback);

    void refreshChannels(onChannelRefreshedCallback callback);
}
