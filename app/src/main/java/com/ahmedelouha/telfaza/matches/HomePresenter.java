package com.ahmedelouha.telfaza.matches;

import android.util.Log;

import com.ahmedelouha.telfaza.data.DataRepositoryContract;
import com.ahmedelouha.telfaza.data.Match;

import java.util.List;

/**
 * Created by raaja on 16-12-2017.
 */

public class HomePresenter implements HomePresenterContract.Presenter {


    private DataRepositoryContract dataModel;
    private HomePresenterContract.View view;
    private boolean isRefreshing;

    public HomePresenter(DataRepositoryContract dataModel, HomePresenterContract.View view ){
        this.dataModel = dataModel;
        this.view = view;
        view.setPresenter(this);
    }


    @Override
    public void refreshMatches() {
        if(isRefreshing){
            return;
        }
        isRefreshing = true;
        view.showSwipeRefresh();
        dataModel.refreshMatches(new DataRepositoryContract.OnMatchRefreshedCallback() {
            @Override
            public void onMatchRefresh(List<List<Match>> oldMatches, List<List<Match>> currentMatches) {
                Log.d(HomePresenter.class.getName(),"Refresh Success");
                view.updateNewMatches(oldMatches,currentMatches);
                view.hideSwipeRefresh();
                isRefreshing = false;
            }

            @Override
            public void onRefreshFailed() {
                Log.d(HomePresenter.class.getName(),"RefreshFailed");
                view.UpdateFailedRefresh();
                view.hideSwipeRefresh();
                isRefreshing = false;
            }
        });
    }
}
