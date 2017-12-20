package com.ahmedelouha.telfaza.matches;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedelouha.telfaza.R;
import com.ahmedelouha.telfaza.data.DataRepositoryModel;
import com.ahmedelouha.telfaza.matchdetail.MatchDetailActivity;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by raaja on 16-12-2017.
 */

public class MatchHolder extends RecyclerView.ViewHolder implements MatchHolderContract {

    View parent;
    String team1Name;
    String team2Name;
    String matchId;
    TextView team1Score,team2Score,status,channel;
    ImageView teamOneImg,teamTwoImg;
    MatchPresenterContract.Presenter presenter;
    String matchTime,matchDate;
    boolean shouldDisplayTime;


    public MatchHolder(View itemView) {
        super(itemView);
        this.parent = itemView;
        team1Score = parent.findViewById(R.id.txt2);
        team2Score = parent.findViewById(R.id.txt5);
        status = parent.findViewById(R.id.txt3);
        channel = parent.findViewById(R.id.txt4);
        teamOneImg = parent.findViewById(R.id.img1);
        teamTwoImg = parent.findViewById(R.id.img2);
        setListeners();
    }

    @Override
    public void setTeamNames(String teamOneName, String teamTwoName) {
        TextView team1 = parent.findViewById(R.id.txt1);
        TextView team2 = parent.findViewById(R.id.txt6);
        team1.setText(teamOneName);
        team2.setText(teamTwoName);
        this.team1Name = teamOneName;
        this.team2Name = teamTwoName;
    }

    void setListeners(){

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               presenter.onClickMatch(MatchHolder.this,matchId);
            }
        });
    }


    @Override
    public void setTeamLogos(String tamOneUrl, String teamTwoUrl) {
        Picasso.with(itemView.getContext()).load(DataRepositoryModel.TEAM_IMAGE_URL+tamOneUrl)
                .fit().centerCrop().into(teamOneImg);
        Picasso.with(itemView.getContext()).load(DataRepositoryModel.TEAM_IMAGE_URL+teamTwoUrl)
                .fit().centerCrop().into(teamTwoImg);
    }

    @Override
    public void setScores(String teamOneScore, String teamTwoScore) {
        team1Score.setText(teamOneScore);
        team2Score.setText(teamTwoScore);
    }

    @Override
    public void setStatus(String status) {

        if(shouldDisplayTime){
            updateStatus(matchTime);
            return;
        }

        switch (status){
            case "NS":
                updateStatus("- NA -");
                break;
            case "W1":
                updateStatus(team1Name+" \n"+itemView.getResources().getString(R.string.teamWon));
                break;
            case "W2":
                updateStatus(team2Name+" \n" +itemView.getResources().getString(R.string.teamWon) );
                break;
            case "R":
                updateStatus(itemView.getResources().getString(R.string.matchRunning));
                break;
            case "D":
                updateStatus(itemView.getResources().getString(R.string.matchDraw));
                break;
            default:
                updateStatus("- NA -");
        }
    }

    void updateStatus(String status){
        this.status.setText(status);
    }

    @Override
    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    @Override
    public void setChannel(String channel) {
        this.channel.setText(channel);
    }

    @Override
    public void setMatchTime(String date, String time) {
       matchTime = time;
        matchDate = date;
    }

    @Override
    public void setShouldDisplayTime(boolean shouldDisplayTime) {
        this.shouldDisplayTime = shouldDisplayTime;
    }

    @Override
    public void setPresenter(MatchPresenterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showMatchDetail(String matchId) {
        itemView.getContext().startActivity(new Intent(itemView.getContext()
                , MatchDetailActivity.class)
                .putExtra(MatchDetailActivity.MATCH_ID,matchId));
    }

}
