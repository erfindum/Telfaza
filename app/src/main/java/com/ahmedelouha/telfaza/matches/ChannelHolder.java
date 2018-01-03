package com.ahmedelouha.telfaza.matches;

import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedelouha.telfaza.data.DataRepositoryModel;
import com.squareup.picasso.Picasso;

/**
 * Created by raaja on 28-12-2017.
 */

public class ChannelHolder implements ChannelHolderContract {

    TextView channelTxt;
    ImageView channelImage;
    TextView streamCountTxt;

    void setChannelTxtView(TextView textView){
        this.channelTxt = textView;
    }

    void setChannelImage(ImageView imageView){
        this.channelImage = imageView;
    }

    void setStreamTextView(TextView streamCount){
        this.streamCountTxt = streamCount;
    }

    @Override
    public void setChannelName(String channelName) {
        channelTxt.setText(channelName);
    }

    @Override
    public void setChannelImage(String channelImage) {
        Picasso.with(channelTxt.getContext()).load(DataRepositoryModel.CHANNEL_IMAGE_URL+channelImage)
                .fit().centerCrop().into(this.channelImage);
    }

    public void setStreamCountTxt(int streamCountTxt) {
        this.streamCountTxt.setText(String.valueOf(streamCountTxt));
    }


}
