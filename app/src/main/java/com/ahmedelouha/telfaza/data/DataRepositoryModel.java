package com.ahmedelouha.telfaza.data;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.internal.LinkedHashTreeMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by raaja on 17-12-2017.
 */

public class DataRepositoryModel implements DataRepositoryContract {

    private static final String BASE_URL ="http://soccer.aprolibro.com/";
    public static final String TEAM_IMAGE_URL = BASE_URL+"team/";
    public static final String LEAGUE_IMAGE_URL = BASE_URL+"league/";

    private static DataRepositoryModel INSTANCE;

    private DataRepositoryModel(){

    }

    public static DataRepositoryModel getInstance(){
        if(INSTANCE == null){
            INSTANCE = new DataRepositoryModel();
        }
        return INSTANCE;
    }

    @Override
    public void refreshMatches(final OnMatchRefreshedCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MatchRefreshInterface service = retrofit.create(MatchRefreshInterface.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),"get_match=1");
        Call<ResponseBody> call = service.refreshMatches(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> mMatches) {
                if(mMatches.isSuccessful()){
                   // Log.d(DataRepositoryModel.class.getName(),String.valueOf(mMatches.body().match == null));
                    try {
                        String formattedResponse = mMatches.body().string();
                        if(formattedResponse != null && !formattedResponse.equals("")) {
                            List<Match> matches = new Gson().fromJson(formattedResponse, new TypeToken<List<Match>>() {
                            }.getType());
                            Log.d(DataRepositoryModel.class.getName(), formattedResponse + " \n" + String.valueOf(matches.isEmpty()));
                            sendRefreshedMatches(matches, callback);
                            return;
                        }
                        if (chekCallbackNull(callback)) {
                            return;
                        }
                        callback.onRefreshFailed();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    catch (JsonParseException e){
                        e.printStackTrace();
                        if (callback==null) {
                            return;
                        }
                        callback.onRefreshFailed();
                    }

                }else{
                    if(chekCallbackNull(callback)){
                        return;
                    }
                    callback.onRefreshFailed();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(chekCallbackNull(callback)){
                    return;
                }
                    callback.onRefreshFailed();
                //Log.d(DataRepositoryModel.class.getName(),t.getMessage());
            }
        });


    }


    boolean chekCallbackNull(DataRepositoryContract.OnMatchRefreshedCallback callback){
        return callback==null;
    }

    void sendRefreshedMatches(List<Match> matches,DataRepositoryContract.OnMatchRefreshedCallback callback){
        LinkedHashMap<String ,List<Match>> oldMatchMap = new LinkedHashMap<>();
        LinkedHashMap<String,List<Match>> currentMatchMap = new LinkedHashMap<>();
        for(Match match:matches){
            String leagueId = match.league;
            if(!oldMatchMap.containsKey(leagueId)&& !currentMatchMap.containsKey(leagueId)){
                oldMatchMap.put(leagueId,new ArrayList<Match>());
                currentMatchMap.put(leagueId, new ArrayList<Match>());
            }
        }
        for(Match match: matches){
            int matchType = validateTime(match);
            if(matchType==Match.MATCH_TYPE_ONGOING){
                currentMatchMap.get(match.league)
                        .add(match);
            }
            if(matchType == Match.MATCH_TYPE_COMPLETE){
                oldMatchMap.get(match.league)
                        .add(match);
            }
        }
        ArrayList<List<Match>> oldMatchList = new ArrayList<>();
        ArrayList<List<Match>> currentMatchList = new ArrayList<>();
        for(List<Match> entry : oldMatchMap.values()){
            if(!entry.isEmpty()){
                oldMatchList.add(entry);
            }
        }

        for (List<Match> entry : currentMatchMap.values()){
            if(!entry.isEmpty()){
                Collections.reverse(entry);
                currentMatchList.add(entry);
            }
        }

        if(chekCallbackNull(callback)){
            return;
        }
        callback.onMatchRefresh(oldMatchList,currentMatchList);

    }

    int validateTime(Match match){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date unformattedDate = format.parse(match.date);
            format = new SimpleDateFormat("dd-MM-yyyy");
            String hours = match.time.substring(0,match.time.indexOf(":"));
            String minutes = match.time.substring(match.time.indexOf(":")+1,match.time.lastIndexOf(":"));
            Calendar matchCalender = Calendar.getInstance();
            matchCalender.setTime(unformattedDate);
            matchCalender.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hours));
            matchCalender.set(Calendar.MINUTE,Integer.parseInt(minutes));
            match.time = hours+":"+minutes;
            match.date = format.format(unformattedDate);

            Calendar currentCal = Calendar.getInstance();
            if(matchCalender.getTimeInMillis()+54_00_000<currentCal.getTimeInMillis()){
                match.shouldDisplayTime = false;
                return Match.MATCH_TYPE_COMPLETE;
            }else{
                if(matchCalender.getTimeInMillis()<currentCal.getTimeInMillis()){
                    match.status="R";
                    match.shouldDisplayTime = false;
                }else{
                    match.shouldDisplayTime = true;
                }
                return Match.MATCH_TYPE_ONGOING;
            }
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void refreshSelectedMatch(String matchId, final onSelectedMatchRefreshedCallback callback) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MatchRefreshInterface service = retrofit.create(MatchRefreshInterface.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),"get_match=1&id="+matchId);

        Call<ResponseBody> call = service.refreshSelectedMatch(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> mMatches) {
                if(mMatches.isSuccessful()){
                    try {
                        String unformattedResponse = mMatches.body().string();
                        if(unformattedResponse != null && !unformattedResponse.equals("")) {
                            List<Match> selectedMatch = new Gson().fromJson(unformattedResponse, new TypeToken<List<Match>>() {
                            }.getType());
                            validateTime(selectedMatch.get(0));
                            if (callback==null) {
                                return;
                            }
                            callback.onSelectedMatchRefreshed(selectedMatch.get(0));
                            return;
                        }
                        if (callback==null) {
                            return;
                        }
                        callback.onRefreshFailed();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    catch (JsonParseException e){
                        e.printStackTrace();
                        if (callback==null) {
                            return;
                        }
                        callback.onRefreshFailed();
                    }

                }else{
                    if(callback == null){
                        return;
                    }
                    callback.onRefreshFailed();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(callback == null){
                    return;
                }
                callback.onRefreshFailed();
            }
        });

    }

    @Override
    public void refreshChannels(final onChannelRefreshedCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MatchRefreshInterface service = retrofit.create(MatchRefreshInterface.class);
        Call<List<Channel>> call = service.refreshChannels(1);
        call.enqueue(new Callback<List<Channel>>() {
            @Override
            public void onResponse(Call<List<Channel>> call, Response<List<Channel>> response) {
                if(response.isSuccessful()){
                    try {
                        List<Channel> channelList = response.body();
                        if (callback == null) {
                            return;
                        }
                        callback.onChannelRefreshed(channelList);
                    } catch (JsonParseException e){
                        e.printStackTrace();
                        if (callback==null) {
                            return;
                        }
                        callback.onRefreshFailed();
                    }
                }
                else {
                    if (callback==null) {
                        return;
                    }
                    callback.onRefreshFailed();
                }

            }

            @Override
            public void onFailure(Call<List<Channel>> call, Throwable t) {
                if (callback==null) {
                    return;
                }
                callback.onRefreshFailed();
            }
        });
    }


}
