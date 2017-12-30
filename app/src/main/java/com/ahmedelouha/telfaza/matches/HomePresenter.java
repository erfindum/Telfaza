package com.ahmedelouha.telfaza.matches;

import android.util.Log;

import com.ahmedelouha.telfaza.data.Channel;
import com.ahmedelouha.telfaza.data.DataRepositoryContract;
import com.ahmedelouha.telfaza.data.Match;

import java.util.List;

/**
 * Created by raaja on 16-12-2017.
 */

public class HomePresenter implements HomePresenterContract.Presenter {


    private DataRepositoryContract dataModel;
    private HomePresenterContract.View view;
    private boolean isMatchRefreshing, isChannelRefreshing;

    public HomePresenter(DataRepositoryContract dataModel, HomePresenterContract.View view ){
        this.dataModel = dataModel;
        this.view = view;
        view.setPresenter(this);
    }


    @Override
    public void refreshMatches() {
        if(isMatchRefreshing){
            return;
        }
        isMatchRefreshing = true;
        view.showMatchSwipeRefresh();
        dataModel.refreshMatches(new DataRepositoryContract.OnMatchRefreshedCallback() {
            @Override
            public void onMatchRefresh(List<List<Match>> oldMatches, List<List<Match>> currentMatches) {
                Log.d(HomePresenter.class.getName(),"Refresh Success");
                view.updateNewMatches(oldMatches,currentMatches);
                view.hideMatchSwipeRefresh();
                isMatchRefreshing = false;
            }

            @Override
            public void onRefreshFailed() {
                Log.d(HomePresenter.class.getName(),"RefreshFailed");
                view.updateMatchFailedRefresh();
                view.hideMatchSwipeRefresh();
                isMatchRefreshing = false;
            }
        });
    }

    @Override
    public void refreshChannels() {
        if (isChannelRefreshing){
            return;
        }
        isChannelRefreshing = true;
        view.showChannelSwipeRefresh();
        dataModel.refreshChannels(new DataRepositoryContract.onChannelRefreshedCallback() {
            @Override
            public void onChannelRefreshed(List<Channel> channelList) {
                view.updateNewChannels(channelList);
                view.hideChannelSwipeRefresh();
                isChannelRefreshing = false;
            }

            @Override
            public void onRefreshFailed() {
                view.updateChannelFailedRefresh();
                view.hideChannelSwipeRefresh();
                isChannelRefreshing = false;
            }
        });

    }
}
