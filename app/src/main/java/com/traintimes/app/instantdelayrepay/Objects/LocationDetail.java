
package com.traintimes.app.instantdelayrepay.Objects;

import java.util.List;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class LocationDetail {

    @SerializedName("crs")
    private String mCrs;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("destination")
    private List<Destination> mDestination;
    @SerializedName("displayAs")
    private String mDisplayAs;
    @SerializedName("gbttBookedArrival")
    private String mGbttBookedArrival;
    @SerializedName("gbttBookedArrivalNextDay")
    private Boolean mGbttBookedArrivalNextDay;
    @SerializedName("isCall")
    private Boolean mIsCall;
    @SerializedName("isPublicCall")
    private Boolean mIsPublicCall;
    @SerializedName("origin")
    private List<Origin> mOrigin;
    @SerializedName("platform")
    private String mPlatform;
    @SerializedName("platformChanged")
    private Boolean mPlatformChanged;
    @SerializedName("platformConfirmed")
    private Boolean mPlatformConfirmed;
    @SerializedName("realtimeActivated")
    private Boolean mRealtimeActivated;
    @SerializedName("realtimeArrival")
    private String mRealtimeArrival=null;
    @SerializedName("realtimeArrivalActual")
    private Boolean mRealtimeArrivalActual;
    @SerializedName("realtimeArrivalNextDay")
    private Boolean mRealtimeArrivalNextDay;
    @SerializedName("tiploc")
    private String mTiploc;

    @SerializedName("cancelReasonCode")
    private String cancelReasonCode = null;
    @SerializedName("cancelReasonShortText")
    private String cancelReasonShortText;
    @SerializedName("cancelReasonLongText")
    private String cancelReasonLongText;

    public String getmDisplayAs() {
        return mDisplayAs;
    }

    public void setmDisplayAs(String mDisplayAs) {
        this.mDisplayAs = mDisplayAs;
    }

    public String getCancelReasonCode() {
        return cancelReasonCode;
    }

    public void setCancelReasonCode(String cancelReasonCode) {
        this.cancelReasonCode = cancelReasonCode;
    }

    public String getCancelReasonShortText() {
        return cancelReasonShortText;
    }

    public void setCancelReasonShortText(String cancelReasonShortText) {
        this.cancelReasonShortText = cancelReasonShortText;
    }

    public String getCancelReasonLongText() {
        return cancelReasonLongText;
    }

    public void setCancelReasonLongText(String cancelReasonLongText) {
        this.cancelReasonLongText = cancelReasonLongText;
    }

    public String getCrs() {
        return mCrs;
    }

    public void setCrs(String crs) {
        mCrs = crs;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public List<Destination> getDestination() {
        return mDestination;
    }

    public void setDestination(List<Destination> destination) {
        mDestination = destination;
    }

    public String getDisplayAs() {
        return mDisplayAs;
    }

    public void setDisplayAs(String displayAs) {
        mDisplayAs = displayAs;
    }

    public String getGbttBookedArrival() {
        return mGbttBookedArrival;
    }

    public void setGbttBookedArrival(String gbttBookedArrival) {
        mGbttBookedArrival = gbttBookedArrival;
    }

    public Boolean getGbttBookedArrivalNextDay() {
        return mGbttBookedArrivalNextDay;
    }

    public void setGbttBookedArrivalNextDay(Boolean gbttBookedArrivalNextDay) {
        mGbttBookedArrivalNextDay = gbttBookedArrivalNextDay;
    }

    public Boolean getIsCall() {
        return mIsCall;
    }

    public void setIsCall(Boolean isCall) {
        mIsCall = isCall;
    }

    public Boolean getIsPublicCall() {
        return mIsPublicCall;
    }

    public void setIsPublicCall(Boolean isPublicCall) {
        mIsPublicCall = isPublicCall;
    }

    public List<Origin> getOrigin() {
        return mOrigin;
    }

    public void setOrigin(List<Origin> origin) {
        mOrigin = origin;
    }

    public String getPlatform() {
        return mPlatform;
    }

    public void setPlatform(String platform) {
        mPlatform = platform;
    }

    public Boolean getPlatformChanged() {
        return mPlatformChanged;
    }

    public void setPlatformChanged(Boolean platformChanged) {
        mPlatformChanged = platformChanged;
    }

    public Boolean getPlatformConfirmed() {
        return mPlatformConfirmed;
    }

    public void setPlatformConfirmed(Boolean platformConfirmed) {
        mPlatformConfirmed = platformConfirmed;
    }

    public Boolean getRealtimeActivated() {
        return mRealtimeActivated;
    }

    public void setRealtimeActivated(Boolean realtimeActivated) {
        mRealtimeActivated = realtimeActivated;
    }

    public String getRealtimeArrival() {
        return mRealtimeArrival;
    }

    public void setRealtimeArrival(String realtimeArrival) {
        mRealtimeArrival = realtimeArrival;
    }

    public Boolean getRealtimeArrivalActual() {
        return mRealtimeArrivalActual;
    }

    public void setRealtimeArrivalActual(Boolean realtimeArrivalActual) {
        mRealtimeArrivalActual = realtimeArrivalActual;
    }

    public Boolean getRealtimeArrivalNextDay() {
        return mRealtimeArrivalNextDay;
    }

    public void setRealtimeArrivalNextDay(Boolean realtimeArrivalNextDay) {
        mRealtimeArrivalNextDay = realtimeArrivalNextDay;
    }

    public String getTiploc() {
        return mTiploc;
    }

    public void setTiploc(String tiploc) {
        mTiploc = tiploc;
    }

}
