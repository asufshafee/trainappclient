
package com.traintimes.app.instantdelayrepay.Objects;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Service {

    @SerializedName("atocCode")
    private String mAtocCode;
    @SerializedName("atocName")
    private String mAtocName;
    @SerializedName("isPassenger")
    private Boolean mIsPassenger;
    @SerializedName("locationDetail")
    private LocationDetail mLocationDetail;
    @SerializedName("runDate")
    private String mRunDate;
    @SerializedName("runningIdentity")
    private String mRunningIdentity;
    @SerializedName("serviceType")
    private String mServiceType;
    @SerializedName("serviceUid")
    private String mServiceUid;
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

    public Boolean getIsPassenger() {
        return mIsPassenger;
    }

    public void setIsPassenger(Boolean isPassenger) {
        mIsPassenger = isPassenger;
    }

    public LocationDetail getLocationDetail() {
        return mLocationDetail;
    }

    public void setLocationDetail(LocationDetail locationDetail) {
        mLocationDetail = locationDetail;
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

    public String getTrainIdentity() {
        return mTrainIdentity;
    }

    public void setTrainIdentity(String trainIdentity) {
        mTrainIdentity = trainIdentity;
    }

}
