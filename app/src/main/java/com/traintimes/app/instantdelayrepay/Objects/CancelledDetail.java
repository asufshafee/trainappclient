
package com.traintimes.app.instantdelayrepay.Objects;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CancelledDetail {

    @SerializedName("actualServiceUid")
    private String mActualServiceUid;
    @SerializedName("cancellationReason")
    private String mCancellationReason;
    @SerializedName("cancelled")
    private Boolean mCancelled;
    @SerializedName("cancelledServiceUid")
    private String mCancelledServiceUid;
    @SerializedName("delayed")
    private Boolean mDelayed;
    @SerializedName("departureTime")
    private String mDepartureTime;
    @SerializedName("fromStation")
    private String mFromStation;
    @SerializedName("refundAmount")
    private String mRefundAmount;
    @SerializedName("timeDelayed")
    private Double mTimeDelayed;
    @SerializedName("toStation")
    private String mToStation;
    @SerializedName("atocCode")
    private String mAtocCode;
    @SerializedName("atocName")
    private String mAtocName;

    public String getmActualServiceUid() {
        return mActualServiceUid;
    }

    public void setmActualServiceUid(String mActualServiceUid) {
        this.mActualServiceUid = mActualServiceUid;
    }

    public String getmCancellationReason() {
        return mCancellationReason;
    }

    public void setmCancellationReason(String mCancellationReason) {
        this.mCancellationReason = mCancellationReason;
    }

    public Boolean getmCancelled() {
        return mCancelled;
    }

    public void setmCancelled(Boolean mCancelled) {
        this.mCancelled = mCancelled;
    }

    public String getmCancelledServiceUid() {
        return mCancelledServiceUid;
    }

    public void setmCancelledServiceUid(String mCancelledServiceUid) {
        this.mCancelledServiceUid = mCancelledServiceUid;
    }

    public Boolean getmDelayed() {
        return mDelayed;
    }

    public void setmDelayed(Boolean mDelayed) {
        this.mDelayed = mDelayed;
    }

    public String getmDepartureTime() {
        return mDepartureTime;
    }

    public void setmDepartureTime(String mDepartureTime) {
        this.mDepartureTime = mDepartureTime;
    }

    public String getmFromStation() {
        return mFromStation;
    }

    public void setmFromStation(String mFromStation) {
        this.mFromStation = mFromStation;
    }

    public String getmRefundAmount() {
        return mRefundAmount;
    }

    public void setmRefundAmount(String mRefundAmount) {
        this.mRefundAmount = mRefundAmount;
    }

    public Double getmTimeDelayed() {
        return mTimeDelayed;
    }

    public void setmTimeDelayed(Double mTimeDelayed) {
        this.mTimeDelayed = mTimeDelayed;
    }

    public String getmToStation() {
        return mToStation;
    }

    public void setmToStation(String mToStation) {
        this.mToStation = mToStation;
    }

    public String getmAtocCode() {
        return mAtocCode;
    }

    public void setmAtocCode(String mAtocCode) {
        this.mAtocCode = mAtocCode;
    }

    public String getmAtocName() {
        return mAtocName;
    }

    public void setmAtocName(String mAtocName) {
        this.mAtocName = mAtocName;
    }

    public String getActualServiceUid() {
        return mActualServiceUid;
    }

    public void setActualServiceUid(String actualServiceUid) {
        mActualServiceUid = actualServiceUid;
    }

    public String getCancellationReason() {
        return mCancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        mCancellationReason = cancellationReason;
    }

    public Boolean getCancelled() {
        return mCancelled;
    }

    public void setCancelled(Boolean cancelled) {
        mCancelled = cancelled;
    }

    public String getCancelledServiceUid() {
        return mCancelledServiceUid;
    }

    public void setCancelledServiceUid(String cancelledServiceUid) {
        mCancelledServiceUid = cancelledServiceUid;
    }

    public Boolean getDelayed() {
        return mDelayed;
    }

    public void setDelayed(Boolean delayed) {
        mDelayed = delayed;
    }

    public String getDepartureTime() {
        return mDepartureTime;
    }

    public void setDepartureTime(String departureTime) {
        mDepartureTime = departureTime;
    }

    public String getFromStation() {
        return mFromStation;
    }

    public void setFromStation(String fromStation) {
        mFromStation = fromStation;
    }

    public String getRefundAmount() {
        return mRefundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        mRefundAmount = refundAmount;
    }

    public Double getTimeDelayed() {
        return mTimeDelayed;
    }

    public void setTimeDelayed(Double timeDelayed) {
        mTimeDelayed = timeDelayed;
    }

    public String getToStation() {
        return mToStation;
    }

    public void setToStation(String toStation) {
        mToStation = toStation;
    }

}
