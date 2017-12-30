package com.ahmedelouha.telfaza.channeldetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ahmedelouha.telfaza.R;
import com.ahmedelouha.telfaza.data.StreamingLink;

import java.util.List;

/**
 * Created by raaja on 30-12-2017.
 */

public class ChannelListActivity extends AppCompatActivity implements ChannelListContract.View {

    public static final String STREAM_LINK_LIST="stream_link_list";
    public static final String CHANNEL_NAME = "channelName";

    ChannelListContract.Presenter presenter;
    Toolbar toolbar;
    RecyclerView recyclerView;
    ChannelListAdapter channelListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler1);
        channelListAdapter = new ChannelListAdapter(getLayoutInflater());
        recyclerView.setAdapter(channelListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this
                ,linearLayoutManager.getOrientation());
        itemDecoration.setDrawable(ContextCompat.getDrawable(this,R.drawable.img_list_divider));
        recyclerView.addItemDecoration(itemDecoration);
        new ChannelListPresenter(this).updateStreamList((List<StreamingLink>) getIntent().getSerializableExtra(STREAM_LINK_LIST));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra(CHANNEL_NAME));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setPresenter(ChannelListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setStreamList(List<StreamingLink> streamList) {
        channelListAdapter.updateStreamList(streamList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getStreamList();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
