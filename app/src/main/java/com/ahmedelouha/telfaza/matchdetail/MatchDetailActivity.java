package com.ahmedelouha.telfaza.matchdetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedelouha.telfaza.R;
import com.ahmedelouha.telfaza.channeldetail.ChannelListActivity;
import com.ahmedelouha.telfaza.data.DataRepositoryModel;
import com.ahmedelouha.telfaza.data.Match;
import com.ahmedelouha.telfaza.data.StreamingLink;
import com.ahmedelouha.telfaza.matches.MatchActivity;
import com.ahmedelouha.telfaza.utils.FontCache;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by raaja on 19-12-2017.
 */

public class MatchDetailActivity extends AppCompatActivity implements MatchDetailPresenterContract.View {

    public static final String MATCH_ID = "match_id";

    public static final String INTENT_TYPE ="intent_type";
    public static final int INTENT_TYPE_HOME = 23;
    public static final int INTENT_TYPE_NOTIFICATION = 24;

    private AppCompatImageButton backButton;
    private SwipeRefreshLayout mSwipeRefresh;
    private TextView leagueTxt,teamOneTxt,teamTwoTxt,scoreTxt,statusTxt
            ,channelTxt;
    private Button streamBtn, goalBtn;
    private ImageView leagueImg,team1Img,team2Img;
    private Match selectedMatch;

    private MatchDetailPresenter presenter;
    private int matchType;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);

        backButton = (AppCompatImageButton) findViewById(R.id.btn4);

        leagueTxt = (TextView) findViewById(R.id.txt1);
        teamOneTxt = (TextView) findViewById(R.id.txt4);
        teamTwoTxt = (TextView) findViewById(R.id.txt6);
        scoreTxt = (TextView) findViewById(R.id.txt2);
        statusTxt = (TextView) findViewById(R.id.txt3);
        channelTxt = (TextView) findViewById(R.id.txt5);

        streamBtn = (Button) findViewById(R.id.btn1);
        goalBtn = (Button) findViewById(R.id.btn2);

        leagueImg = (ImageView) findViewById(R.id.img1);
        team1Img= (ImageView) findViewById(R.id.img2);
        team2Img = (ImageView) findViewById(R.id.img3);

        matchType = getIntent().getIntExtra(INTENT_TYPE,13);

        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        mSwipeRefresh.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimaryDark);

        streamBtn.setTypeface(FontCache.getTypeface(this,getString(R.string.dinDisplayNormal)));
        goalBtn.setTypeface(FontCache.getTypeface(this,getString(R.string.dinDisplayNormal)));

        new MatchDetailPresenter(this, DataRepositoryModel.getInstance());
        presenter.refreshMatch(getIntent().getStringExtra(MATCH_ID));

        setListeners();

    }

    void setListeners(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refreshMatch(getIntent().getStringExtra(MATCH_ID));
            }
        });

        streamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkMatchNull(selectedMatch)){
                    return;
                }

                presenter.openStreamList(selectedMatch,0);
            }
        });

        goalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkMatchNull(selectedMatch)){
                    return;
                }
                presenter.openStreamList(selectedMatch,1);
            }
        });
    }

    boolean checkMatchNull(Match match){
        return match == null;
    }


    @Override
    public void setPresenter(MatchDetailPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showSwipeRefresh() {
        mSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideSwipeRefresh() {
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void updateMatchDetail(Match match) {
        selectedMatch = match;
        Picasso.with(this).load(DataRepositoryModel.TEAM_IMAGE_URL+match.team1image)
                .fit().centerCrop().into(team1Img);
        Picasso.with(this).load(DataRepositoryModel.TEAM_IMAGE_URL+match.team2image)
                .fit().centerCrop().into(team2Img);
        Picasso.with(this).load(DataRepositoryModel.LEAGUE_IMAGE_URL+match.leagueimage)
                .fit().centerCrop().into(leagueImg);

        leagueTxt.setText(match.leaguename);
        String score = match.score1+"  -  "+match.score2;
        scoreTxt.setText(score);
        setStatus(match);
        teamOneTxt.setText(match.team1name);
        teamTwoTxt.setText(match.team2name);
        channelTxt.setText(match.channelname);
        displayButtons(match);

    }

    public void setStatus(Match match) {

        if(match.shouldDisplayTime){
            updateStatus(match.time);
            return;
        }

        switch (match.status){
            case "NS":
                updateStatus("- NA -");
                break;
            case "W1":
                updateStatus(match.team1name+" \n"+getString(R.string.teamWon));
                break;
            case "W2":
                updateStatus(match.team2name+" \n" +getString(R.string.teamWon) );
                break;
            case "R":
                updateStatus(getString(R.string.matchRunning));
                break;
            case "D":
                updateStatus(getString(R.string.matchDraw));
                break;
            default:
                updateStatus("- NA -");
        }
    }

    void displayButtons(Match match){
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date date = format.parse(match.date);
            Calendar matchCalender = Calendar.getInstance();
            matchCalender.setTime(date);
            String hours = match.time.substring(0,match.time.indexOf(":"));
            String minutes = match.time.substring(match.time.indexOf(":")+1);
            matchCalender.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hours));
            matchCalender.set(Calendar.MINUTE, Integer.parseInt(minutes));
            if(matchCalender.getTimeInMillis()+54_00_000<Calendar.getInstance().getTimeInMillis()){
                streamBtn.setVisibility(View.GONE);
                goalBtn.setVisibility(View.VISIBLE);
            }else{
                streamBtn.setVisibility(View.VISIBLE);
                goalBtn.setVisibility(View.GONE);
            }

        }catch (ParseException e){

        }
    }

    void updateStatus(String matchStatus){
        statusTxt.setText(matchStatus);
    }

    @Override
    public void updateMatchDetailFailed() {
        Toast.makeText(this,R.string.selectedRefreshFailed,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayMatchStreamList(String channelName, List<StreamingLink> streamingLinkList) {
        ArrayList<StreamingLink> streamingLinks = (ArrayList<StreamingLink>) streamingLinkList;
        startActivity(new Intent(this, ChannelListActivity.class)
        .putExtra(ChannelListActivity.CHANNEL_NAME,channelName)
        .putExtra(ChannelListActivity.STREAM_LINK_LIST,streamingLinks));
    }

    @Override
    public void displayGoalLink(String url) {
       startActivity(new Intent(this,GoalWebActivity.class)
            .putExtra(GoalWebActivity.GOAL_WEB_URL,url));
    }

   /* @Override
    public void displayMatchStreamList(String Url) {
        try {
            Uri uri = Uri.parse(URLDecoder.decode(Url, "UTF-8"));
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(browserIntent);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        catch (ActivityNotFoundException e){
            e.printStackTrace();
        }

    } */

    @Override
    public void onBackPressed() {
        if(matchType == INTENT_TYPE_NOTIFICATION){
            startActivity(new Intent(this, MatchActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
        super.onBackPressed();
    }
}
