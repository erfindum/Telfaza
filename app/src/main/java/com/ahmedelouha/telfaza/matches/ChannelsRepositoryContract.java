package com.ahmedelouha.telfaza.matches;

import com.ahmedelouha.telfaza.data.Channel;

import java.util.List;

/**
 * Created by raaja on 28-12-2017.
 */

public interface ChannelsRepositoryContract {

    void bindChannelHolder(int groupPosition, ChannelHolderContract channelHolder);

    void bindStreamHolder(int groupPosition, int childPosition, StreamLinkHolderContract streamingLinkHolder);

    void updateChannelAndStreams(List<Channel> channelList);

    int getChannelCount();

    int getStreamsCount(int groupPosition);

    void setGroupExpanded(boolean isExpanded,int groupPosition);

}
