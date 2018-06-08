
package com.traintimes.app.instantdelayrepay.Objects;

import java.util.List;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Location {

    @SerializedName("cancelReasonCode")
    private String mCancelReasonCode;
    @SerializedName("cancelReasonLongText")
    private String mCancelReasonLongText;
    @SerializedName("cancelReasonShortText")
    private String mCancelReasonShortText;
    @SerializedName("crs")
    private String mCrs;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("destination")
    private List<Destination> mDestination;
    @SerializedName("displayAs")
    private String mDisplayAs;
    @SerializedName("gbttBookedDeparture")
    private String mGbttBookedDeparture;
    @SerializedName("isCall")
    private Boolean mIsCall;
    @SerializedName("isPublicCall")
    private Boolean mIsPublicCall;
    @SerializedName("line")
    private String mLine;
    @SerializedName("lineConfirmed")
    private Boolean mLineConfirmed;
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
    @SerializedName("realtimeDeparture")
    private String mRealtimeDeparture;
    @SerializedName("realtimeDepartureActual")
    private Boolean mRealtimeDepartureActual;
    @SerializedName("tiploc")
    private String mTiploc;
    @SerializedName("realtimeArrival")
    private String realtimeArrival;

    @SerializedName("realtimeGbttArrivalLateness")
    private String realtimeGbttArrivalLateness=null;

    public String getRealtimeGbttArrivalLateness() {
        return realtimeGbttArrivalLateness;
    }

    public void setRealtimeGbttArrivalLateness(String realtimeGbttArrivalLateness) {
        this.realtimeGbttArrivalLateness = realtimeGbttArrivalLateness;
    }

    public String getCancelReasonCode() {
        return mCancelReasonCode;
    }

    public void setCancelReasonCode(String cancelReasonCode) {
        mCancelReasonCode = cancelReasonCode;
    }

    public String getCancelReasonLongText() {
        return mCancelReasonLongText;
    }

    public void setCancelReasonLongText(String cancelReasonLongText) {
        mCancelReasonLongText = cancelReasonLongText;
    }

    public String getCancelReasonShortText() {
        return mCancelReasonShortText;
    }

    public void setCancelReasonShortText(String cancelReasonShortText) {
        mCancelReasonShortText = cancelReasonShortText;
    }

    public String getRealtimeArrival() {
        return realtimeArrival;
    }

    public void setRealtimeArrival(String realtimeArrival) {
        this.realtimeArrival = realtimeArrival;
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

    public String getGbttBookedDeparture() {
        return mGbttBookedDeparture;
    }

    public void setGbttBookedDeparture(String gbttBookedDeparture) {
        mGbttBookedDeparture = gbttBookedDeparture;
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

    public String getLine() {
        return mLine;
    }

    public void setLine(String line) {
        mLine = line;
    }

    public Boolean getLineConfirmed() {
        return mLineConfirmed;
    }

    public void setLineConfirmed(Boolean lineConfirmed) {
        mLineConfirmed = lineConfirmed;
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

    public String getRealtimeDeparture() {
        return mRealtimeDeparture;
    }

    public void setRealtimeDeparture(String realtimeDeparture) {
        mRealtimeDeparture = realtimeDeparture;
    }

    public Boolean getRealtimeDepartureActual() {
        return mRealtimeDepartureActual;
    }

    public void setRealtimeDepartureActual(Boolean realtimeDepartureActual) {
        mRealtimeDepartureActual = realtimeDepartureActual;
    }

    public String getTiploc() {
        return mTiploc;
    }

    public void setTiploc(String tiploc) {
        mTiploc = tiploc;
    }

}
