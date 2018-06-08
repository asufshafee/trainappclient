
package com.traintimes.app.instantdelayrepay.Objects;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DelayedServiceDuaTo {

    @SerializedName("atocCode")
    private String mAtocCode;
    @SerializedName("atocName")
    private String mAtocName;
    @SerializedName("destination")
    private List<Destination> mDestination;
    @SerializedName("isPassenger")
    private Boolean mIsPassenger;
    @SerializedName("locations")
    private List<Location> mLocations;
    @SerializedName("origin")
    private List<Origin> mOrigin;
    @SerializedName("performanceMonitored")
    private Boolean mPerformanceMonitored;
    @SerializedName("powerType")
    private String mPowerType;
    @SerializedName("realtimeActivated")
    private Boolean mRealtimeActivated;
    @SerializedName("runDate")
    private String mRunDate;
    @SerializedName("runningIdentity")
    private String mRunningIdentity;
    @SerializedName("serviceType")
    private String mServiceType;
    @SerializedName("serviceUid")
    private String mServiceUid;
    @SerializedName("trainClass")
    private String mTrainClass;
    @SerializedName("trainIdentity")
    private String mTrainIdentity;

    public String getAtocCode() {
        return mAtocCode;
    }

    public void setAtocCode(String atocCode) {
        mAtocCode = atocCode;
    }

    public String getAtocName() {
        return mAtocName;
    }

    public void setAtocName(String atocName) {
        mAtocName = atocName;
    }

    public List<Destination> getDestination() {
        return mDestination;
    }

    public void setDestination(List<Destination> destination) {
        mDestination = destination;
    }

    public Boolean getIsPassenger() {
        return mIsPassenger;
    }

    public void setIsPassenger(Boolean isPassenger) {
        mIsPassenger = isPassenger;
    }

    public List<Location> getLocations() {
        return mLocations;
    }

    public void setLocations(List<Location> locations) {
        mLocations = locations;
    }

    public List<Origin> getOrigin() {
        return mOrigin;
    }

    public void setOrigin(List<Origin> origin) {
        mOrigin = origin;
    }

    public Boolean getPerformanceMonitored() {
        return mPerformanceMonitored;
    }

    public void setPerformanceMonitored(Boolean performanceMonitored) {
        mPerformanceMonitored = performanceMonitored;
    }

    public String getPowerType() {
        return mPowerType;
    }

    public void setPowerType(String powerType) {
        mPowerType = powerType;
    }

    public Boolean getRealtimeActivated() {
        return mRealtimeActivated;
    }

    public void setRealtimeActivated(Boolean realtimeActivated) {
        mRealtimeActivated = realtimeActivated;
    }

    public String getRunDate() {
        return mRunDate;
    }

    public void setRunDate(String runDate) {
        mRunDate = runDate;
    }

    public String getRunningIdentity() {
        return mRunningIdentity;
    }

    public void setRunningIdentity(String runningIdentity) {
        mRunningIdentity = runningIdentity;
    }

    public String getServiceType() {
        return mServiceType;
    }

    public void setServiceType(String serviceType) {
        mServiceType = serviceType;
    }

    public String getServiceUid() {
        return mServiceUid;
    }

    public void setServiceUid(String serviceUid) {
        mServiceUid = serviceUid;
    }

    public String getTrainClass() {
        return mTrainClass;
    }

    public void setTrainClass(String trainClass) {
        mTrainClass = trainClass;
    }

    public String getTrainIdentity() {
        return mTrainIdentity;
    }

    public void setTrainIdentity(String trainIdentity) {
        mTrainIdentity = trainIdentity;
    }

}
