
package com.traintimes.app.instantdelayrepay.Objects;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PostObject {

    @SerializedName("date")
    private String mDate;
    @SerializedName("fromStation")
    private String mFromStation;
    @SerializedName("realTimeRequest")
    private Boolean mRealTimeRequest;
    @SerializedName("singleTicketPrice")
    private Long mSingleTicketPrice;
    @SerializedName("toStation")
    private String mToStation;

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getFromStation() {
        return mFromStation;
    }

    public void setFromStation(String fromStation) {
        mFromStation = fromStation;
    }

    public Boolean getRealTimeRequest() {
        return mRealTimeRequest;
    }

    public void setRealTimeRequest(Boolean realTimeRequest) {
        mRealTimeRequest = realTimeRequest;
    }

    public Long getSingleTicketPrice() {
        return mSingleTicketPrice;
    }

    public void setSingleTicketPrice(Long singleTicketPrice) {
        mSingleTicketPrice = singleTicketPrice;
    }

    public String getToStation() {
        return mToStation;
    }

    public void setToStation(String toStation) {
        mToStation = toStation;
    }

}
