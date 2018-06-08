
package com.traintimes.app.instantdelayrepay.Objects;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Stations {

    @SerializedName("Railway companies")
    private List<RailwayCompany> mRailwayCompanies;
    @SerializedName("Train stations and codes")
    private List<TrainStationsAndCode> mTrainStationsAndCodes;

    public List<RailwayCompany> getRailwayCompanies() {
        return mRailwayCompanies;
    }

    public void setRailwayCompanies(List<RailwayCompany> RailwayCompanies) {
        mRailwayCompanies = RailwayCompanies;
    }

    public List<TrainStationsAndCode> getTrainStationsAndCodes() {
        return mTrainStationsAndCodes;
    }

    public void setTrainStationsAndCodes(List<TrainStationsAndCode> TrainStationsAndCodes) {
        mTrainStationsAndCodes = TrainStationsAndCodes;
    }

}
