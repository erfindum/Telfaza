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
    List<Boolean> groupExpandedList;

    public ChannelRepositoryPresenter(){
        channelList = new ArrayList<>();
        groupExpandedList = new ArrayList<>();
    }

    @Override
    public void bindChannelHolder(int groupPosition, ChannelHolderContract channelHolder) {
        channelHolder.setChannelName(channelList.get(groupPosition).name);
        channelHolder.changeDropIcon(groupExpandedList.get(groupPosition));
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
            for(Channel channel:channelList){
                groupExpandedList.add(false);
            }
    }

    @Override
    public int getChannelCount() {
        return channelList.size();
    }

    @Override
    public int getStreamsCount(int groupPosition) {
        return channelList.get(groupPosition).streamingLinks.size();
    }

    @Override
    public void setGroupExpanded(boolean isExpanded,int groupPosition) {
        groupExpandedList.add(groupPosition,isExpanded);
    }


}
