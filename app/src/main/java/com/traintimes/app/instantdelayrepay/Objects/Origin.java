
package com.traintimes.app.instantdelayrepay.Objects;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Origin {

    @SerializedName("description")
    private String mDescription;
    @SerializedName("publicTime")
    private String mPublicTime;
    @SerializedName("tiploc")
    private String mTiploc;
    @SerializedName("workingTime")
    private String mWorkingTime;

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getPublicTime() {
        return mPublicTime;
    }

    public void setPublicTime(String publicTime) {
        mPublicTime = publicTime;
    }

    public String getTiploc() {
        return mTiploc;
    }

    public void setTiploc(String tiploc) {
        mTiploc = tiploc;
    }

    public String getWorkingTime() {
        return mWorkingTime;
    }

    public void setWorkingTime(String workingTime) {
        mWorkingTime = workingTime;
    }

}
