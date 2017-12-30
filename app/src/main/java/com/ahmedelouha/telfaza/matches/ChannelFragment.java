package com.ahmedelouha.telfaza.matches;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.ahmedelouha.telfaza.R;
import com.ahmedelouha.telfaza.data.Channel;
import com.ahmedelouha.telfaza.matchstream.StreamPlayerActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

/**
 * Created by raaja on 28-12-2017.
 */

public class ChannelFragment extends Fragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ExpandableListView mExpandableListView;
    private ChannelAdapter channelAdapter;
    private AdView adView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        View parent = inflater.inflate(R.layout.fragment_home_channel,container,false);
        mSwipeRefreshLayout = parent.findViewById(R.id.swipeRefresh);
        mExpandableListView = parent.findViewById(R.id.expand_list);
        adView = parent.findViewById(R.id.adView);
        channelAdapter = new ChannelAdapter(getContext());
        mExpandableListView.setAdapter(channelAdapter);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimaryDark);
        return parent;
    }

    static ChannelFragment getInstance(){
        return new ChannelFragment();
    }

    void updateChannelList(List<Channel> channelList){
        channelAdapter.updateChannels(channelList);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshChannels();
            }
        });
        refreshChannels();
        loadAd();
    }

    void refreshChannels(){
        MatchActivity activity = (MatchActivity) getActivity();
        activity.refreshChannels();
    }

    void updateChannelSwipeState(boolean swipeState){
        if(mSwipeRefreshLayout!=null){
            mSwipeRefreshLayout.setRefreshing(swipeState);
        }
    }

    void loadAd(){
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
}
