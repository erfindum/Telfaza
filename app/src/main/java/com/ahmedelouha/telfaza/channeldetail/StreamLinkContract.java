package com.ahmedelouha.telfaza.channeldetail;

import com.ahmedelouha.telfaza.data.StreamingLink;

/**
 * Created by raaja on 30-12-2017.
 */

public interface StreamLinkContract {

    interface Presenter{
        void onStreamClicked(StreamingLink link,StreamLinkContract.View view);
        void bindHolder(StreamLinkContract.View view, StreamingLink streamingLink);
    }

    interface View{
        void setStreamLink(StreamingLink link);
        void openStreamLink(String type);
        void bindView(StreamingLink link);
        void setPresenter(StreamLinkContract.Presenter presenter);
    }
}
