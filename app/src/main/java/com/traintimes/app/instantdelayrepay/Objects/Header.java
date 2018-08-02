package com.traintimes.app.instantdelayrepay.Objects;

import java.util.Date;
import java.util.List;

public class Header {

    String date;
    List<ListDate> listDates;
    Date datepoint;

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

    public Date getDatepoint() {
        return datepoint;
    }

    public void setDatepoint(Date datepoint) {
        this.datepoint = datepoint;
    }
}
