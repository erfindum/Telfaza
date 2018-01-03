package com.ahmedelouha.telfaza.channeldetail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmedelouha.telfaza.R;
import com.ahmedelouha.telfaza.data.StreamingLink;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raaja on 30-12-2017.
 */

public class ChannelListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<StreamingLink> streamingLinkList;
    StreamLinkContract.Presenter presenter;
    LayoutInflater inflater;

    ChannelListAdapter( LayoutInflater inflater){
        streamingLinkList = new ArrayList<>();
        this.presenter = new StreamLinkPresenter();
        this.inflater = inflater;
    }

    void updateStreamList(List<StreamingLink> streamingLinkList){
        this.streamingLinkList = streamingLinkList;
        notifyDataSetChanged();

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item  = inflater.inflate(R.layout.item_stream_list,parent,false);
        return  new StreamLinkHolder(item);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        presenter.bindHolder((StreamLinkContract.View) holder,streamingLinkList.get(position));
    }

    @Override
    public int getItemCount() {
        return streamingLinkList.size();
    }
}
