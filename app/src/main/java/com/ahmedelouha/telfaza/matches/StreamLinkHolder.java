package com.ahmedelouha.telfaza.matches;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedelouha.telfaza.data.StreamingLink;
import com.ahmedelouha.telfaza.matchstream.StreamPlayerActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * Created by raaja on 28-12-2017.
 */

public class StreamLinkHolder implements StreamLinkHolderContract {

    TextView streamNameTxt;
    StreamingLink streamingLink;
    View clickView;

    void setStreamNameTxtView(TextView textView){
        this.streamNameTxt = textView;
    }

    @Override
    public void setStreamLink(StreamingLink streamingLink) {
        this.streamingLink = streamingLink;
    }

    @Override
    public void setStreamName(String streamName) {
        streamNameTxt.setText(streamName);
    }

    public void setClickParent(View parent){
        this.clickView = parent;
        setListener();
    }

    void setListener(){
        Log.d(StreamLinkHolder.class.getName(),clickView.getHeight() + "   " + clickView.getWidth());
        clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStreamLink(streamingLink.type);
            }
        });
    }

    public void openStreamLink(String type) {
        switch (type){
            case "P":
                clickView.getContext().startActivity(new Intent(clickView.getContext()
                        , StreamPlayerActivity.class)
                        .putExtra(StreamPlayerActivity.STREAM_NAME,streamingLink.sname)
                        .putExtra(StreamPlayerActivity.STREAM_LINK,streamingLink.links));
                break;
            case "B":
                Intent queryIntent = new Intent(Intent.ACTION_VIEW);
                try {
                    Uri uri = Uri.parse(URLDecoder.decode(streamingLink.links,"UTF-8"));
                    queryIntent.setData(uri);
                    List<ResolveInfo> browserList = queryIntent(queryIntent);
                    if(browserList.isEmpty()){
                        queryIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=browser"));
                        List<ResolveInfo> querMarketList = queryIntent(queryIntent);
                        if (querMarketList.isEmpty()) {
                            Toast.makeText(clickView.getContext()
                                    , "Web browser not available, download web browser.", Toast.LENGTH_LONG)
                                    .show();
                            break;
                        }
                    }
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
                clickView.getContext().startActivity(queryIntent);
                break;
            case "M":
                Intent querIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("market://search?q="+streamingLink.links));
                List<ResolveInfo> queryList = queryIntent(querIntent);
                if(queryList.isEmpty()){
                    querIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://play.google.com/store/search?q="+streamingLink.links));
                    List<ResolveInfo> queryBrowserlist = queryIntent(querIntent);
                    if(queryBrowserlist.isEmpty()){
                        Toast.makeText(clickView.getContext(),"PlayStore not available, install PlayStore.",Toast.LENGTH_LONG)
                                .show();
                        break;
                    }
                }
                clickView.getContext().startActivity(querIntent);
        }

    }

    List<ResolveInfo> queryIntent(Intent intent){
        return clickView.getContext().getPackageManager()
                .queryIntentActivities(intent, PackageManager.GET_META_DATA);
    }
}
