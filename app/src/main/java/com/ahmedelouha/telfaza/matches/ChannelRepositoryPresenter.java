package com.ahmedelouha.telfaza.matches;

import com.ahmedelouha.telfaza.data.Channel;
import com.ahmedelouha.telfaza.data.StreamingLink;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raaja on 28-12-2017.
 */

public class ChannelRepositoryPresenter implements ChannelsRepositoryContract {

    List<Channel> channelList;

    public ChannelRepositoryPresenter(){
        channelList = new ArrayList<>();
    }

    @Override
    public void bindChannelHolder(int groupPosition, ChannelHolderContract channelHolder) {
        channelHolder.setChannelName(channelList.get(groupPosition).name);
        channelHolder.setChannelImage(channelList.get(groupPosition).image);
        channelHolder.setStreamCountTxt(channelList.get(groupPosition).streamingLinks.size());
    }

    @Override
    public void bindStreamHolder(int groupPosition, int childPosition, StreamLinkHolderContract streamingLinkHolder) {
        StreamingLink streamingLink = channelList.get(groupPosition).streamingLinks.get(childPosition);
        streamingLinkHolder.setStreamLink(streamingLink);
        streamingLinkHolder.setStreamName(streamingLink.sname);
    }

    @Override
    public void updateChannelAndStreams(List<Channel> channelList) {
            this.channelList = channelList;

    }

    @Override
    public int getChannelCount() {
        return channelList.size();
    }

    @Override
    public int getStreamsCount(int groupPosition) {
        return channelList.get(groupPosition).streamingLinks.size();
    }


}
