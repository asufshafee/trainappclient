package com.traintimes.app.instantdelayrepay.Objects;

/**
 * Created by GeeksEra on 4/28/2018.
 */

public class ListDate {

    String origin;
    String Destination;
    String Amount;
    String Delay;
    String Time;
    String AtocCode;

    public String getAtocCode() {
        return AtocCode;
    }

    public void setAtocCode(String atocCode) {
        AtocCode = atocCode;
    }

    public String getAtocName() {
        return AtocName;
    }

    public void setAtocName(String atocName) {
        AtocName = atocName;
    }

    String AtocName;

    public String getType() {
        return "Item";
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    String Status;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getDelay() {
        return Delay;
    }

    public void setDelay(String delay) {
        Delay = delay;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
