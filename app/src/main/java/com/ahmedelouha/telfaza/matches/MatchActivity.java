package com.ahmedelouha.telfaza.matches;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedelouha.telfaza.R;
import com.ahmedelouha.telfaza.data.DataRepositoryModel;
import com.ahmedelouha.telfaza.data.Match;
import com.ahmedelouha.telfaza.utils.FontCache;

import java.util.List;

/**
 * Created by raaja on 16-12-2017.
 */

public class MatchActivity extends AppCompatActivity implements HomePresenterContract.View{

    private HomePresenterContract.Presenter presenter;
    ViewPager pager;
    MatchPagerAdapter adapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        pager = (ViewPager) findViewById(R.id.pager1);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        adapter = new MatchPagerAdapter(getSupportFragmentManager(),getResources().getString(R.string.currentMatch),
                getResources().getString(R.string.completedMatch));
        pager.setAdapter(adapter);
        pager.setCurrentItem(1);
        new HomePresenter(DataRepositoryModel.getInstance(),this);
    }

    @Override
    public void setPresenter(HomePresenterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showSwipeRefresh() {
        if(getPager().getCurrentItem() == 0){
            adapter.updateSwipteState(true);
        }else{
            adapter.updateSwipteState(true);
        }
    }

    @Override
    public void hideSwipeRefresh() {
        adapter.updateSwipteState(false);
    }

    void refreshMatches(){
        presenter.refreshMatches();
    }

    @Override
    public void updateNewMatches(List<List<Match>> completedMatches, List<List<Match>> onGoingMatches) {
        adapter = (MatchPagerAdapter) getPager().getAdapter();
        adapter.updateMatches(completedMatches,onGoingMatches);
    }

    @Override
    public void UpdateFailedRefresh() {
        Toast.makeText(this,getString(R.string.refreshFailed),Toast.LENGTH_LONG)
        .show();
    }

    private ViewPager getPager(){
        return this.pager;
    }


}
