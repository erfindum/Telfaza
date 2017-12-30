package com.ahmedelouha.telfaza.channeldetail;

import com.ahmedelouha.telfaza.data.StreamingLink;

/**
 * Created by raaja on 30-12-2017.
 */

public class StreamLinkPresenter implements StreamLinkContract.Presenter {



    @Override
    public void onStreamClicked(StreamingLink link,StreamLinkContract.View view) {
        view.openStreamLink(link.type);
    }

    @Override
    public void bindHolder(StreamLinkContract.View view, StreamingLink streamingLink) {
        view.setPresenter(this);
        view.setStreamLink(streamingLink);
        view.bindView(streamingLink);
    }
}
