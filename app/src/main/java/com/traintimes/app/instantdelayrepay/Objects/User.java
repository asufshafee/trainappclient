package com.traintimes.app.instantdelayrepay.Objects;

import java.io.Serializable;

/**
 * Created by GeeksEra on 4/26/2018.
 */

public class User implements Serializable {

    private String Name, Email, Price;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
