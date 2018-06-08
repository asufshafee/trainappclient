
package com.traintimes.app.instantdelayrepay.Objects;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ServicesDelays {

    @SerializedName("filter")
    private Filter mFilter;
    @SerializedName("location")
    private Location mLocation;
    @SerializedName("services")
    private List<Service> mServices;

    public Filter getFilter() {
        return mFilter;
    }

    public void setFilter(Filter filter) {
        mFilter = filter;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    public List<Service> getServices() {
        return mServices;
    }

    public void setServices(List<Service> services) {
        mServices = services;
    }

}
