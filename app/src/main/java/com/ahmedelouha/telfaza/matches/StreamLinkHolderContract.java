package com.ahmedelouha.telfaza.matches;

import com.ahmedelouha.telfaza.data.StreamingLink;

/**
 * Created by raaja on 28-12-2017.
 */

public interface StreamLinkHolderContract {

    void setStreamLink(StreamingLink streamingLink);

    void setStreamName(String streamName);
}
