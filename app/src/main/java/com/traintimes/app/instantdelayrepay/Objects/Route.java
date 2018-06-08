package com.traintimes.app.instantdelayrepay.Objects;

/**
 * Created by GeeksEra on 5/4/2018.
 */

public class Route {


    private String StartDate, EndDate, FromStation, ToStation, FromStationCode, ToStationCode;

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getFromStation() {
        return FromStation;
    }

    public void setFromStation(String fromStation) {
        FromStation = fromStation;
    }

    public String getToStation() {
        return ToStation;
    }

    public void setToStation(String toStation) {
        ToStation = toStation;
    }

    public String getFromStationCode() {
        return FromStationCode;
    }

    public void setFromStationCode(String fromStationCode) {
        FromStationCode = fromStationCode;
    }

    public String getToStationCode() {
        return ToStationCode;
    }

    public void setToStationCode(String toStationCode) {
        ToStationCode = toStationCode;
    }
}
