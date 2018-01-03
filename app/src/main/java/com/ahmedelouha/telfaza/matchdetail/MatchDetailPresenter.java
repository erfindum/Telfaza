package com.ahmedelouha.telfaza.matchdetail;

import android.text.Html;
import android.text.SpannedString;

import com.ahmedelouha.telfaza.data.DataRepositoryContract;
import com.ahmedelouha.telfaza.data.Match;

/**
 * Created by raaja on 20-12-2017.
 */

public class MatchDetailPresenter implements MatchDetailPresenterContract.Presenter {

    private MatchDetailPresenterContract.View view;
    private DataRepositoryContract dataRepository;
    boolean isRefreshing;

    MatchDetailPresenter(MatchDetailPresenterContract.View view, DataRepositoryContract dataRepository){
        this.view = view;
        this.dataRepository = dataRepository;
        view.setPresenter(this);
    }

    @Override
    public void refreshMatch(String matchId) {
        if(isRefreshing){
            return;
        }
        isRefreshing = true;
        view.showSwipeRefresh();
        dataRepository.refreshSelectedMatch(matchId, new DataRepositoryContract.onSelectedMatchRefreshedCallback() {
            @Override
            public void onSelectedMatchRefreshed(Match match) {
                if(match!=null){
                    view.updateMatchDetail(match);
                    view.hideSwipeRefresh();
                    isRefreshing = false;
                    return;
                }
                view.updateMatchDetailFailed();
                view.hideSwipeRefresh();
                isRefreshing = false;

            }

            @Override
            public void onRefreshFailed() {
                view.updateMatchDetailFailed();
                view.hideSwipeRefresh();
                isRefreshing = false;
            }
        });
    }

    @Override
    public void openStreamList(Match match, int urlType) {

        if(urlType==0){
            if(match.link==null){
                return;
            }
            view.displayMatchStreamList(match.channelname,match.link);
            return;
        }
        if(urlType == 1){
            view.displayGoalLink(match.goalurl);
        }

    }
}
