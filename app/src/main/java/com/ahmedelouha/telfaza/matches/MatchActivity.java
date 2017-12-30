package com.ahmedelouha.telfaza.matches;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedelouha.telfaza.R;
import com.ahmedelouha.telfaza.data.Channel;
import com.ahmedelouha.telfaza.data.DataRepositoryModel;
import com.ahmedelouha.telfaza.data.Match;
import com.ahmedelouha.telfaza.matchdetail.MatchDetailActivity;
import com.ahmedelouha.telfaza.utils.FontCache;
import com.google.android.gms.ads.MobileAds;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import static com.ahmedelouha.telfaza.matches.services.MatchNotificationService.TYPE_NOTIF_BROWSER;
import static com.ahmedelouha.telfaza.matches.services.MatchNotificationService.TYPE_NOTIF_MARKET;
import static com.ahmedelouha.telfaza.matches.services.MatchNotificationService.TYPE_NOTIF_MATCH;

/**
 * Created by raaja on 16-12-2017.
 */

public class MatchActivity extends AppCompatActivity implements HomePresenterContract.View{

    private HomePresenterContract.Presenter presenter;
    ViewPager pager;
    MatchPagerAdapter adapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        pager = (ViewPager) findViewById(R.id.pager1);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(queryIncommingIntent(getIntent())){
            finishAffinity();
        }
        adapter = new MatchPagerAdapter(getSupportFragmentManager(),getString(R.string.currentMatch),
                getString(R.string.completedMatch),getString(R.string.channels));
        pager.setAdapter(adapter);
        pager.setCurrentItem(1);
        pager.setOffscreenPageLimit(3);
        new HomePresenter(DataRepositoryModel.getInstance(),this);

        MobileAds.initialize(this,"ca-app-pub-6875249461085305~1346594703");
    }



    @Override
    public void setPresenter(HomePresenterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showMatchSwipeRefresh() {
        if(getPager().getCurrentItem() == 0){
            adapter.updateSwipteState(true);
        }else{
            adapter.updateSwipteState(true);
        }
    }

    @Override
    public void hideMatchSwipeRefresh() {
        adapter.updateSwipteState(false);
    }

    @Override
    public void showChannelSwipeRefresh() {
            adapter.updateChannelSwipeState(true);
    }

    @Override
    public void hideChannelSwipeRefresh() {
        adapter.updateChannelSwipeState(false);
    }

    void refreshMatches(){
        presenter.refreshMatches();
    }

    void refreshChannels(){presenter.refreshChannels();}

    @Override
    public void updateNewMatches(List<List<Match>> completedMatches, List<List<Match>> onGoingMatches) {
        adapter = (MatchPagerAdapter) getPager().getAdapter();
        adapter.updateMatches(completedMatches,onGoingMatches);
    }

    @Override
    public void updateMatchFailedRefresh() {
        Toast.makeText(this,getString(R.string.refreshFailed),Toast.LENGTH_LONG)
        .show();
    }

    @Override
    public void updateNewChannels(List<Channel> channelList) {
        adapter = (MatchPagerAdapter) getPager().getAdapter();
        adapter.updateChannels(channelList);
    }

    @Override
    public void updateChannelFailedRefresh() {
        Toast.makeText(this,getString(R.string.refreshChannelFailed),Toast.LENGTH_LONG)
                .show();
    }

    private ViewPager getPager(){
        return this.pager;
    }

    boolean queryIncommingIntent(Intent intent){
        if(intent.getExtras()!=null){
            for(String key:intent.getExtras().keySet()){
                if(isNotifType(key)) {
                    Log.d(MatchActivity.class.getName(), "  ------ " + intent.getExtras().getString(key));
                    String value = (String) intent.getExtras().get(key);
                    Intent pushIntent = getPushIntent(key, value);
                    if (pushIntent == null) {
                        return false;
                    }
                    startActivity(pushIntent);
                    return true;
                }
            }
        }
        return false;
    }

    boolean isNotifType(String key){
        switch(key){
            case TYPE_NOTIF_BROWSER:
            case TYPE_NOTIF_MARKET:
            case TYPE_NOTIF_MATCH:
                return true;
            default:
                return false;
        }
    }

    Intent getPushIntent(String key, String value){
        Intent notifIntent;
        if(key.equals(TYPE_NOTIF_BROWSER)){
            notifIntent = new Intent(Intent.ACTION_VIEW);
            try {
                notifIntent.setData(Uri.parse(URLDecoder.decode(value, "UTF-8")));
                List<ResolveInfo> queryList = queryIntent(notifIntent);
                if (queryList.isEmpty()) {
                    notifIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=browser"));
                    List<ResolveInfo> querMarketList = queryIntent(notifIntent);
                    Toast.makeText(this, "Web browser not available, download web browser.", Toast.LENGTH_LONG)
                            .show();
                    if (querMarketList.isEmpty()) {
                        Toast.makeText(this, "Web browser not available, download web browser.", Toast.LENGTH_LONG)
                                .show();
                        return null;
                    }
                }
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
            return notifIntent;
        }else
        if(key.equals(TYPE_NOTIF_MARKET)){
            notifIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("market://search?q="+value));
            List<ResolveInfo> queryList = queryIntent(notifIntent);
            if(queryList.isEmpty()){
                notifIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://play.google.com/store/search?q="+value));
                List<ResolveInfo> queryBrowserlist = queryIntent(notifIntent);
                if(queryBrowserlist.isEmpty()){
                    Toast.makeText(this,"PlayStore not available, install PlayStore.",Toast.LENGTH_LONG)
                            .show();
                    return null;
                }
            }
            return notifIntent;
        }else
        if(key.equals(TYPE_NOTIF_MATCH)) {
            notifIntent = new Intent(getBaseContext(), MatchDetailActivity.class);
            notifIntent.putExtra(MatchDetailActivity.MATCH_ID, value);
            notifIntent.putExtra(MatchDetailActivity.INTENT_TYPE, MatchDetailActivity.INTENT_TYPE_NOTIFICATION);
            notifIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            return notifIntent;
        }
        return null;
    }

    List<ResolveInfo> queryIntent(Intent intent){
        return getPackageManager().queryIntentActivities(intent, PackageManager.GET_META_DATA);
    }

}
