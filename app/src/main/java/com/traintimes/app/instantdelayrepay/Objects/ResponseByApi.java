
package com.traintimes.app.instantdelayrepay.Objects;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ResponseByApi {

    @SerializedName("cancelledDetails")
    private List<CancelledDetail> mCancelledDetails;
    @SerializedName("errorCode")
    private String mErrorCode;
    @SerializedName("success")
    private Boolean mSuccess;

    public List<CancelledDetail> getCancelledDetails() {
        return mCancelledDetails;
    }

    public void setCancelledDetails(List<CancelledDetail> cancelledDetails) {
        mCancelledDetails = cancelledDetails;
    }

    public String getErrorCode() {
        return mErrorCode;
    }

    public void setErrorCode(String errorCode) {
        mErrorCode = errorCode;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

}
