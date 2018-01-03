package com.ahmedelouha.telfaza.matchstream;

/**
 * Created by raaja on 29-12-2017.
 */

public class StreamPlayerPresenter implements StreamPlayerContract.Presenter {

    StreamPlayerContract.View view;

    StreamPlayerPresenter(StreamPlayerContract.View view){
        this.view = view;
        view.loadAd();
        view.loadInterstitial();
    }

    @Override
    public void initializeStreamPlayer() {
        view.initializePlayer();
    }

    @Override
    public void releaseStreamPlayer() {
        view.releasePlayer();
    }

    @Override
    public void changeProgressState(int visibility) {
        view.changeProgressVisibility(visibility);
    }

    @Override
    public void openAdView(int type) {
        view.displayOrHideAdView(type);
    }

    @Override
    public void hideAdView(int type) {
        view.displayOrHideAdView(type);
    }
}
