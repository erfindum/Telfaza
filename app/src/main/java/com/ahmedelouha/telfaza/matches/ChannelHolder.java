package com.ahmedelouha.telfaza.matches;

import android.support.v7.widget.AppCompatImageView;
import android.widget.TextView;

import com.ahmedelouha.telfaza.R;

/**
 * Created by raaja on 28-12-2017.
 */

public class ChannelHolder implements ChannelHolderContract {

    TextView channelTxt;
    AppCompatImageView imageView;

    void setChannelTxtView(TextView textView){
        this.channelTxt = textView;
    }

    void setChannelDropImage(AppCompatImageView imageView){
        this.imageView = imageView;
    }

    @Override
    public void setChannelName(String channelName) {
        channelTxt.setText(channelName);
    }

    @Override
    public void changeDropIcon(boolean isEXpanded) {
        if(isEXpanded){
            imageView.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
        }else{
            imageView.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);

        }
    }


}
