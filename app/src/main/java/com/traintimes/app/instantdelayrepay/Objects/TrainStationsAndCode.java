
package com.traintimes.app.instantdelayrepay.Objects;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class TrainStationsAndCode {

    @SerializedName("CRS_Code")
    private String mCRSCode;
    @SerializedName("Station_Name")
    private String mStationName;

    public String getCRSCode() {
        return mCRSCode;
    }

    public void setCRSCode(String CRSCode) {
        mCRSCode = CRSCode;
    }

    public String getStationName() {
        return mStationName;
    }

    public void setStationName(String StationName) {
        mStationName = StationName;
    }

}
