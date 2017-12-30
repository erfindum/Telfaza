package com.ahmedelouha.telfaza.channeldetail;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedelouha.telfaza.R;
import com.ahmedelouha.telfaza.data.StreamingLink;
import com.ahmedelouha.telfaza.matchstream.StreamPlayerActivity;
import com.ahmedelouha.telfaza.matchstream.StreamPlayerContract;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by raaja on 30-12-2017.
 */

public class StreamLinkHolder extends RecyclerView.ViewHolder implements StreamLinkContract.View {

    StreamLinkContract.Presenter presenter;
    TextView textView;
    StreamingLink link;

    StreamLinkHolder(View itemView){
        super(itemView);
        textView = itemView.findViewById(R.id.txt1);
        setListener();
    }

    void setListener(){
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onStreamClicked(link,StreamLinkHolder.this);
            }
        });
    }

    @Override
    public void setStreamLink(StreamingLink link) {
        this.link = link;
    }

    @Override
    public void openStreamLink(String type) {
        switch (type){
            case "P":
                itemView.getContext().startActivity(new Intent(itemView.getContext()
                            , StreamPlayerActivity.class)
                        .putExtra(StreamPlayerActivity.STREAM_NAME,link.sname)
                        .putExtra(StreamPlayerActivity.STREAM_LINK,link.links));
                break;
            case "B":
                Intent queryIntent = new Intent(Intent.ACTION_VIEW);
                try {
                    Uri uri = Uri.parse(URLDecoder.decode(link.links,"UTF-8"));
                    queryIntent.setData(uri);
                    List<ResolveInfo> browserList = queryIntent(queryIntent);
                    if(browserList.isEmpty()){
                        queryIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=browser"));
                        List<ResolveInfo> querMarketList = queryIntent(queryIntent);
                        if (querMarketList.isEmpty()) {
                            Toast.makeText(itemView.getContext()
                                    , "Web browser not available, download web browser.", Toast.LENGTH_LONG)
                                    .show();
                            break;
                        }
                    }
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
                itemView.getContext().startActivity(queryIntent);
                break;
            case "M":
                Intent querIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("market://search?q="+link.links));
                List<ResolveInfo> queryList = queryIntent(querIntent);
                if(queryList.isEmpty()){
                    querIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://play.google.com/store/search?q="+link.links));
                    List<ResolveInfo> queryBrowserlist = queryIntent(querIntent);
                    if(queryBrowserlist.isEmpty()){
                        Toast.makeText(itemView.getContext(),"PlayStore not available, install PlayStore.",Toast.LENGTH_LONG)
                                .show();
                        break;
                    }
                }
                itemView.getContext().startActivity(querIntent);
        }

    }

    @Override
    public void bindView(StreamingLink link) {

        textView.setText(link.sname);
    }

    @Override
    public void setPresenter(StreamLinkContract.Presenter presenter) {
        this.presenter = presenter;
    }

    List<ResolveInfo> queryIntent(Intent intent){
        return itemView.getContext().getPackageManager()
                .queryIntentActivities(intent, PackageManager.GET_META_DATA);
    }
}
