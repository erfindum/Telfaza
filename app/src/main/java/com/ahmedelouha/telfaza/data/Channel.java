package com.ahmedelouha.telfaza.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by raaja on 28-12-2017.
 */

public class Channel {
    public String name;
    @SerializedName("links")
    public ArrayList<StreamingLink> streamingLinks;
}
