package com.ahmedelouha.telfaza.data;

import java.util.ArrayList;

/**
 * Created by raaja on 16-12-2017.
 */

public class Match {

    public static final int MATCH_TYPE_COMPLETE = 23;
    public static final int MATCH_TYPE_ONGOING = 24;

    public String team1name, team2name,team1image,team2image
            ,leagueimage,leaguename,score1,score2,time,channelname
            ,date,goalurl,status,id,team1,team2,league,channelimage;
    public ArrayList<StreamingLink> link;
    public boolean shouldDisplayTime;


}
