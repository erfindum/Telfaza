package com.ahmedelouha.telfaza.matches;

import android.support.v4.content.ContextCompat;
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

public class MatchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    MatchPresenter presenter;
    LayoutInflater inflater;

    MatchAdapter(LayoutInflater inflater){
        presenter = new MatchPresenter();
        this.inflater = inflater;
    }

    void updateData(List<Match> matchList){
        presenter.updateMatches(matchList);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==0){
            View childView = inflater.inflate(R.layout.item_league_name,parent,false);
            return new LeagueNameHolder(childView);
        }
        View childView= inflater.inflate(R.layout.item_match,parent,false);
        return new MatchHolder(childView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position == 0){
            presenter.bindLeagueName((LeagueNameHolderContract) holder);
            return;
        }
        presenter.bindMatchHolder(position, (MatchHolderContract) holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getMatchCount();
    }

    @Override
    public int getItemViewType(int position) {
         super.getItemViewType(position);
        if(position==0){
            return 0;
        }
        return 1;
    }
}
