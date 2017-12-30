package com.ahmedelouha.telfaza.channeldetail;

import com.ahmedelouha.telfaza.data.StreamingLink;

import java.util.List;

/**
 * Created by raaja on 30-12-2017.
 */

public interface ChannelListContract {

    interface View{
        void setPresenter(ChannelListContract.Presenter presenter);
        void setStreamList(List<StreamingLink> streamList);
    }

    interface Presenter{
        void getStreamList();
        void updateStreamList(List<StreamingLink> streamingLinkList);
    }
}
