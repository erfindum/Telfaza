package com.ahmedelouha.telfaza.matchstream;

/**
 * Created by raaja on 29-12-2017.
 */

public interface StreamPlayerContract {

    interface View{
        void initializePlayer();
        void releasePlayer();
        void changeProgressVisibility(int visibility);
        void loadAd();
        void displayOrHideAdView(int type);
    }

    interface Presenter{
        void initializeStreamPlayer();
        void releaseStreamPlayer();
        void changeProgressState(int visibility);
        void openAdView(int type);
        void hideAdView(int type);
    }
}
