package com.ahmedelouha.telfaza.matches;

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

public class LeagueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    LeagueRepositoryPresenter presenter;
    LayoutInflater inflater;
    RecyclerView.RecycledViewPool viewPool;

    LeagueAdapter(LayoutInflater inflater, RecyclerView.RecycledViewPool viewPool){
        presenter = new LeagueRepositoryPresenter();
        this.inflater = inflater;
        this.viewPool = viewPool;
    }

    void updateLeagues(List<List<Match>> leagueList){
        presenter.updateLeagues(leagueList);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewParent = inflater.inflate(R.layout.item_league_list,parent,false);
        return new LeagueHolder(viewParent,viewPool);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        presenter.bindLeagueHolder(position, (LeagueHolderContract) holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getLeagueCount();
    }
}
