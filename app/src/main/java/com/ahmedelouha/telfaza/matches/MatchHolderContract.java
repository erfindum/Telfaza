package com.ahmedelouha.telfaza.matches;

/**
 * Created by raaja on 16-12-2017.
 */

public interface MatchHolderContract extends MatchPresenterContract.View {

    void setTeamNames(String teamOneName, String teamTwoName);

    void setTeamLogos(String tamOneUrl,String teamTwoUrl);

    void setScores(String teamOneScore, String teamTwoScore);

    void setStatus(String status);

    void setMatchId(String matchId);

    void setChannel(String channel);

    void setMatchTime(String date, String time);

    void setShouldDisplayTime(boolean shouldDisplayTime);

    void setPresenter(MatchPresenterContract.Presenter presenter);

}
