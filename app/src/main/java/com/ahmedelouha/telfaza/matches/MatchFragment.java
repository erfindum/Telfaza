package com.ahmedelouha.telfaza.matches;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmedelouha.telfaza.R;
import com.ahmedelouha.telfaza.data.Match;

import java.util.List;

/**
 * Created by raaja on 16-12-2017.
 */

public class MatchFragment extends Fragment {

    RecyclerView mRecycler;
    LeagueAdapter leagueAdapter;
    SwipeRefreshLayout swipeRefresh;
    RecyclerView.RecycledViewPool childViewPool;

    void setChildViewPool(RecyclerView.RecycledViewPool viewPool){
        this.childViewPool = viewPool;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home_pager,container,false);
        mRecycler = view.findViewById(R.id.recycler1);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimaryDark);
        leagueAdapter = new LeagueAdapter(inflater,childViewPool);
        mRecycler.setAdapter(leagueAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    static MatchFragment getInstance(){
        return new MatchFragment();
    }

    void updateNewMatches(List<List<Match>> matchList){
        leagueAdapter.updateLeagues(matchList);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshMatches();
            }
        });
        refreshMatches();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void refreshMatches(){
        MatchActivity activity = (MatchActivity) getActivity();
        activity.refreshMatches();
    }

    void updateSwipeRefresh(boolean state){
        if(swipeRefresh!=null) {
            swipeRefresh.setRefreshing(state);
        }
    }

}
