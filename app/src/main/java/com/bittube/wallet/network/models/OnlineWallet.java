package com.bittube.wallet.network.models;

import org.json.JSONException;
import org.json.JSONObject;

public class OnlineWallet {

    private String name;

    private int creation_date;

    private String address;

    private String seed;

    private String viewKey;

    private String SpendKey;


    public OnlineWallet(String name, JSONObject json) throws JSONException {
        this.name = name;
        this.address = json.getString("public_addr");
        this.seed = json.getString("seed");
        this.viewKey = json.getJSONObject("view").getString("sec");
        this.SpendKey = json.getJSONObject("spend").getString("sec");
        this.creation_date = json.optInt("creation_date", 0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getViewKey() {
        return viewKey;
    }

    public void setViewKey(String viewKey) {
        this.viewKey = viewKey;
    }

    public String getSpendKey() {
        return SpendKey;
    }

    public void setSpendKey(String spendKey) {
        SpendKey = spendKey;
    }

    public int getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(int creation_date) {
        this.creation_date = creation_date;
    }
}
