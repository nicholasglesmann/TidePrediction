package com.glesmannn.tideprediction_nicholasglesmann;

import java.util.ArrayList;

public class TideItems extends ArrayList<TideItem> {
    private static final long serialVersionUID = 1L;

    private String city = "";

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }
}
