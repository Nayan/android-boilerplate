package com.conflux.finflux.infrastructure.activation.data;

import android.database.Cursor;

import com.conflux.finflux.util.Logger;

/**
 * Created by Praveen J U on 10/6/2016.
 */
public class Actication {

    private Long id;
    private boolean is_activated;
    private String activation_date;
    private String end_date;
    private String activation_key;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean is_activated() {
        return is_activated;
    }

    public void setIs_activated(boolean is_activated) {
        this.is_activated = is_activated;
    }

    public String getActivation_date() {
        return activation_date;
    }

    public void setActivation_date(String activation_date) {
        this.activation_date = activation_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getActivation_key() {
        return activation_key;
    }

    public void setActivation_key(String activation_key) {
        this.activation_key = activation_key;
    }

    public static Actication builder(final Cursor cursor){
               Actication actication =  new Actication();
        Logger.d("act",String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("identifier"))));
                actication.setId(cursor.getLong(cursor.getColumnIndexOrThrow("identifier")));
                actication.setActivation_date(cursor.getString(cursor.getColumnIndexOrThrow("activation_date")));
                actication.setEnd_date(cursor.getString(cursor.getColumnIndexOrThrow("end_date")));
                actication.setIs_activated(cursor.getInt(cursor.getColumnIndexOrThrow("is_activated"))==0?false:true);
                actication.setActivation_key(cursor.getString(cursor.getColumnIndexOrThrow("activation_key")));
           return actication;
    }
}
