
package com.traintimes.app.instantdelayrepay.Objects;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Filter {

    @SerializedName("destination")
    private Destination mDestination;

    public Destination getDestination() {
        return mDestination;
    }

    public void setDestination(Destination destination) {
        mDestination = destination;
    }

}
