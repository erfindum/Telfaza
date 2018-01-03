package com.ahmedelouha.telfaza.matches;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedelouha.telfaza.R;
import com.ahmedelouha.telfaza.data.Channel;

import java.util.List;

/**
 * Created by raaja on 28-12-2017.
 */

public class ChannelAdapter extends BaseExpandableListAdapter {

    LayoutInflater inflater;
    Context context;
    ChannelRepositoryPresenter presenter;

    ChannelAdapter(Context context){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        presenter = new ChannelRepositoryPresenter();
    }

    void updateChannels(List<Channel> channelList){
        presenter.updateChannelAndStreams(channelList);
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return presenter.getChannelCount();
    }

    @Override
    public int getChildrenCount(int i) {
        return presenter.getStreamsCount(i);
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        ChannelHolder viewHolder;
        if(view == null){
            viewHolder = new ChannelHolder();
            view= inflater.inflate(R.layout.item_channel_name,viewGroup,false);
            viewHolder.setChannelTxtView((TextView) view.findViewById(R.id.txt1));
            viewHolder.setStreamTextView((TextView) view.findViewById(R.id.txt2));
            viewHolder.setChannelImage((ImageView) view.findViewById(R.id.img1));
            view.setTag(viewHolder);
        }
        viewHolder = (ChannelHolder) view.getTag();
        presenter.bindChannelHolder(i,viewHolder);
        return view;

    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        StreamLinkHolder viewHolder;
        if(view==null){
            viewHolder = new StreamLinkHolder();
            view = inflater.inflate(R.layout.item_stream_link,viewGroup,false);
            viewHolder.setStreamNameTxtView((TextView) view.findViewById(R.id.txt1));
            viewHolder.setClickParent(view.findViewById(R.id.view2));
            view.setTag(viewHolder);
        }
        viewHolder = (StreamLinkHolder) view.getTag();
        presenter.bindStreamHolder(i,i1,viewHolder);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
