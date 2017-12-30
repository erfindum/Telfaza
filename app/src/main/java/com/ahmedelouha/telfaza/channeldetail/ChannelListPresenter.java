package com.ahmedelouha.telfaza.channeldetail;

import com.ahmedelouha.telfaza.data.StreamingLink;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raaja on 30-12-2017.
 */

public class ChannelListPresenter implements ChannelListContract.Presenter {

    List<StreamingLink> streamingLinkList;
    ChannelListContract.View view;

    ChannelListPresenter(ChannelListContract.View view){
        streamingLinkList = new ArrayList<>();
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void getStreamList() {
        view.setStreamList(streamingLinkList);
    }

    @Override
    public void updateStreamList(List<StreamingLink> streamingLinkList) {
        this.streamingLinkList = streamingLinkList;
    }
}
