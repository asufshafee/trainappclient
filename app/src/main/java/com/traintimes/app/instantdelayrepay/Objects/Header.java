package com.traintimes.app.instantdelayrepay.Objects;

import java.util.List;

public class Header {

    String date;
    List<ListDate> listDates;

    public List<ListDate> getListDates() {
        return listDates;
    }

    public void setListDates(List<ListDate> listDates) {
        this.listDates = listDates;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

}
