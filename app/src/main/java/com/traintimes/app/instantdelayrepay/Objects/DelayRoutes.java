package com.traintimes.app.instantdelayrepay.Objects;

public class DelayRoutes  extends ListItem{

    ListDate listDate;
    String Date;

    public ListDate getListDate() {
        return listDate;
    }

    public void setListDate(ListDate listDate) {
        this.listDate = listDate;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    @Override
    public int getType() {
        return 1;
    }
}
