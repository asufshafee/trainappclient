package com.traintimes.app.instantdelayrepay.Objects;

public class CancelDetailsObject {
    private String actualServiceUid;

    private String atocCode;

    private String refundAmount;

    private String cancelledServiceUid;

    private String delayed;

    private String cancellationReason;

    private String timeDelayed;

    private String nextDayFlag;

    private Boolean cancelled;

    private String departureTime;

    private String toStation;

    private String atocName;

    private String fromStation;

    public String getActualServiceUid ()
    {
        return actualServiceUid;
    }

    public void setActualServiceUid (String actualServiceUid)
    {
        this.actualServiceUid = actualServiceUid;
    }

    public String getAtocCode ()
    {
        return atocCode;
    }

    public void setAtocCode (String atocCode)
    {
        this.atocCode = atocCode;
    }

    public String getRefundAmount ()
    {
        return refundAmount;
    }

    public void setRefundAmount (String refundAmount)
    {
        this.refundAmount = refundAmount;
    }

    public String getCancelledServiceUid ()
    {
        return cancelledServiceUid;
    }

    public void setCancelledServiceUid (String cancelledServiceUid)
    {
        this.cancelledServiceUid = cancelledServiceUid;
    }

    public String getDelayed ()
    {
        return delayed;
    }

    public void setDelayed (String delayed)
    {
        this.delayed = delayed;
    }

    public String getCancellationReason ()
    {
        return cancellationReason;
    }

    public void setCancellationReason (String cancellationReason)
    {
        this.cancellationReason = cancellationReason;
    }

    public String getTimeDelayed ()
    {
        return timeDelayed;
    }

    public void setTimeDelayed (String timeDelayed)
    {
        this.timeDelayed = timeDelayed;
    }

    public String getNextDayFlag ()
    {
        return nextDayFlag;
    }

    public void setNextDayFlag (String nextDayFlag)
    {
        this.nextDayFlag = nextDayFlag;
    }

    public Boolean getCancelled ()
    {
        return cancelled;
    }

    public void setCancelled (Boolean cancelled)
    {
        this.cancelled = cancelled;
    }

    public String getDepartureTime ()
    {
        return departureTime;
    }

    public void setDepartureTime (String departureTime)
    {
        this.departureTime = departureTime;
    }

    public String getToStation ()
    {
        return toStation;
    }

    public void setToStation (String toStation)
    {
        this.toStation = toStation;
    }

    public String getAtocName ()
    {
        return atocName;
    }

    public void setAtocName (String atocName)
    {
        this.atocName = atocName;
    }

    public String getFromStation ()
    {
        return fromStation;
    }

    public void setFromStation (String fromStation)
    {
        this.fromStation = fromStation;
    }
}
