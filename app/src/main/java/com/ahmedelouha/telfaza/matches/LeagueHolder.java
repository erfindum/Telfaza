package com.ahmedelouha.telfaza.matches;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.ahmedelouha.telfaza.R;
import com.ahmedelouha.telfaza.data.Match;

import java.util.List;

/**
 * Created by raaja on 16-12-2017.
 */

public class LeagueHolder extends RecyclerView.ViewHolder implements LeagueHolderContract {

    RecyclerView mRecycler;
    MatchAdapter adapter;

    LeagueHolder(View view, RecyclerView.RecycledViewPool viewPool){
        super(view);
        mRecycler = view.findViewById(R.id.recycler1);
        mRecycler.setRecycledViewPool(viewPool);
        Context context = itemView.getContext();
        adapter = new MatchAdapter((LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        mRecycler.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        mRecycler.setLayoutManager(linearLayoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(context
                ,linearLayoutManager.getOrientation());
        itemDecoration.setDrawable(ContextCompat.getDrawable(context,R.drawable.img_list_divider));
        mRecycler.addItemDecoration(itemDecoration);
    }

    @Override
    public void setLeague(List<Match> leagues) {
        adapter.updateData(leagues);
    }
}
