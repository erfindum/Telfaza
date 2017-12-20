package com.ahmedelouha.telfaza.matches;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedelouha.telfaza.R;
import com.ahmedelouha.telfaza.data.DataRepositoryModel;
import com.squareup.picasso.Picasso;

/**
 * Created by raaja on 16-12-2017.
 */

public class LeagueNameHolder extends RecyclerView.ViewHolder implements LeagueNameHolderContract {

    ImageView imageView;
    View parent;

    LeagueNameHolder(View item){
        super(item);
        parent = item;
        imageView = parent.findViewById(R.id.img1);
    }

    @Override
    public void setLeagueName(String leagueName) {
        TextView league = parent.findViewById(R.id.txt1);
        league.setText(leagueName);
    }

    @Override
    public void setLeagueLogo(String leagueUrl) {
        Picasso.with(imageView.getContext()).load(DataRepositoryModel.LEAGUE_IMAGE_URL+leagueUrl)
                .fit().centerCrop().into(imageView);
    }
}
