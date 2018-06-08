
package com.traintimes.app.instantdelayrepay.Objects;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RailwayCompany {

    @SerializedName("ATOC_code")
    private String mATOCCode;
    @SerializedName("Image_URL")
    private String mImageURL;
    @SerializedName("Name")
    private String mName;
    @SerializedName("URL")
    private String mURL;

    public String getATOCCode() {
        return mATOCCode;
    }

    public void setATOCCode(String ATOCCode) {
        mATOCCode = ATOCCode;
    }

    public String getImageURL() {
        return mImageURL;
    }

    public void setImageURL(String ImageURL) {
        mImageURL = ImageURL;
    }

    public String getName() {
        return mName;
    }

    public void setName(String Name) {
        mName = Name;
    }

    public String getURL() {
        return mURL;
    }

    public void setURL(String URL) {
        mURL = URL;
    }

}
