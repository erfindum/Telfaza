package com.ahmedelouha.telfaza.matchstream;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ahmedelouha.telfaza.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by raaja on 28-12-2017.
 */

public class StreamPlayerActivity extends AppCompatActivity implements Player.EventListener,StreamPlayerContract.View {

    public static final String STREAM_NAME="stream_name";
    public static final String STREAM_LINK = "stream_link";

    private SimpleExoPlayerView mExoPlayerView;
    private SimpleExoPlayer mVideoPlayer;

    private BandwidthMeter mBandwidthMeter;
    private DataSource.Factory mDatasourceDactory;
    private DefaultTrackSelector mTrackSelector;
    private boolean shouldAutoPlay, isPLaying,isAdDisplayed;

    private ProgressBar mProgress;
    private AdView adView;
    private AppCompatImageButton closeAdBtn;
    private TextView titletext;
    private StreamPlayerPresenter presenter;
    private String streamName, streamLink;
    private int resumeWindow;
    private long resumePosition, adRefreshInterval;
    private Runnable updateAdVisibilityTask;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_play);
        shouldAutoPlay = true;
        setUpdateTask();
        mBandwidthMeter = new DefaultBandwidthMeter();
        mDatasourceDactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this,"Telfaza")
                , (TransferListener<? super DataSource>)  mBandwidthMeter);
        mExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.simple_exo_view);
        mProgress = (ProgressBar) findViewById(R.id.progress);
        titletext = (TextView) findViewById(R.id.txt1);
        adView = (AdView) findViewById(R.id.adView);
        closeAdBtn = (AppCompatImageButton) findViewById(R.id.imgBtn);
        presenter = new StreamPlayerPresenter(this);
        streamName = getIntent().getStringExtra(STREAM_NAME);
        streamLink = getIntent().getStringExtra(STREAM_LINK);
        titletext.setText(streamName);
        setListener();
    }

    @Override
     public void initializePlayer(){
        mExoPlayerView.requestFocus();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_FULLSCREEN);
        TrackSelection.Factory trackSelection = new AdaptiveTrackSelection.Factory(mBandwidthMeter);
        mExoPlayerView.setControllerVisibilityListener(new PlaybackControlView.VisibilityListener() {
            @Override
            public void onVisibilityChange(int visibility) {
                if(visibility==View.GONE){
                    titletext.setVisibility(View.INVISIBLE);
                }else{
                    titletext.setVisibility(View.VISIBLE);
                }
            }
        });
        mTrackSelector = new DefaultTrackSelector(trackSelection);
        mVideoPlayer = ExoPlayerFactory.newSimpleInstance(this,mTrackSelector);
        mExoPlayerView.setPlayer(mVideoPlayer);
        mVideoPlayer.addListener(this);
        mVideoPlayer.setPlayWhenReady(shouldAutoPlay);
        int orientation = getResources().getConfiguration().orientation;
        if( orientation== Configuration.ORIENTATION_PORTRAIT){
            mExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        }else
        if(orientation == Configuration.ORIENTATION_LANDSCAPE)  {
            mExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        }
        MediaSource mediaSource = new HlsMediaSource(Uri.parse(streamLink),mDatasourceDactory
                                ,new Handler(getMainLooper()),null);
        mVideoPlayer.seekTo(resumeWindow,resumePosition);
        mVideoPlayer.prepare(mediaSource);

    }

    @Override
    public void releasePlayer(){
        if(mVideoPlayer!=null){
            mVideoPlayer.setPlayWhenReady(false);
            shouldAutoPlay = mVideoPlayer.getPlayWhenReady();
            resumeWindow = mVideoPlayer.getCurrentWindowIndex();
            resumePosition = mVideoPlayer.getContentPosition();
            mVideoPlayer.release();
            mVideoPlayer.stop();
            mVideoPlayer.removeListener(this);
            mVideoPlayer =null;
            mTrackSelector = null;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_FULLSCREEN);
            Log.d(StreamPlayerActivity.class.getName(),"Stream PLayer Released -----------");
        }
        Log.d(StreamPlayerActivity.class.getName(),"Stream PLayer Released -----------");

    }

    void setUpdateTask(){
        updateAdVisibilityTask = new Runnable() {
            @Override
            public void run() {
                if(!isAdDisplayed && adRefreshInterval<System.currentTimeMillis()){
                    presenter.openAdView(View.VISIBLE);
                    loadAd();
                    Log.d(StreamPlayerActivity.class.getName(),"Showing Ad");
                }
                closeAdBtn.postDelayed(updateAdVisibilityTask,2000);
            }
        };

    }

    void setListener(){
        closeAdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.hideAdView(View.INVISIBLE);
                adRefreshInterval = System.currentTimeMillis()+120000;//9_00_000;
                isAdDisplayed = false;
                Log.d(StreamPlayerActivity.class.getName(),"Hiding Ad");
            }
        });
        closeAdBtn.postDelayed(updateAdVisibilityTask,2000);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(Util.SDK_INT>23){
            presenter.initializeStreamPlayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Util.SDK_INT<=23 || mVideoPlayer == null){
            presenter.initializeStreamPlayer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(Util.SDK_INT<=23){
            presenter.releaseStreamPlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(Util.SDK_INT>23){
            presenter.releaseStreamPlayer();
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {


    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        isPLaying = playWhenReady;
        if(playbackState == Player.STATE_BUFFERING){
            presenter.changeProgressState(View.VISIBLE);
            mExoPlayerView.hideController();
        }else {
            presenter.changeProgressState(View.INVISIBLE);
            mExoPlayerView.showController();
        }
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void changeProgressVisibility(int visibility){
        mProgress.setVisibility(visibility);
    }

    @Override
    public void loadAd() {
        AdRequest request = new AdRequest.Builder()
                .build();
        adView.loadAd(request);
        isAdDisplayed =true;
    }

    @Override
    public void displayOrHideAdView(int type) {
        if(type == View.VISIBLE){
            adView.setVisibility(type);
            closeAdBtn.setVisibility(type);
        }else{
            adView.setVisibility(type);
            closeAdBtn.setVisibility(type);
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeAdBtn.removeCallbacks(updateAdVisibilityTask);
    }
}
