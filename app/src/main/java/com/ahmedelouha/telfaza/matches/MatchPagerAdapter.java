package com.ahmedelouha.telfaza.matches;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.RecyclerView;

import com.ahmedelouha.telfaza.data.Match;

import java.util.List;

/**
 * Created by raaja on 16-12-2017.
 */

public class MatchPagerAdapter extends FragmentStatePagerAdapter {

    String currentMatch,oldMatch;
    MatchFragment currentFragment,oldFragment;
    RecyclerView.RecycledViewPool viewPool;

    public MatchPagerAdapter(FragmentManager fragmentManager,String currentMatch
            ,String oldMatch){
        super(fragmentManager);
        this.currentMatch = currentMatch;
        this.oldMatch = oldMatch;
        viewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            oldFragment = MatchFragment.getInstance();
            oldFragment.setChildViewPool(viewPool);
            return oldFragment;
        }else{
            currentFragment = MatchFragment.getInstance();
            currentFragment.setChildViewPool(viewPool);
            return currentFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position==1){
            return currentMatch;
        }else{
            return oldMatch;
        }
    }

    void updateMatches(List<List<Match>> oldMatches,List<List<Match>> currentMatch){
        oldFragment.updateNewMatches(oldMatches);
        currentFragment.updateNewMatches(currentMatch);
    }

    void updateSwipteState(boolean swipeState){
        oldFragment.updateSwipeRefresh(swipeState);
        currentFragment.updateSwipeRefresh(swipeState);
    }
}
