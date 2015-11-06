package com.adaptris.atmlocator;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "atm_place")
public class AtmPlace implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, columnName = Columns.NAME)
    public String name;
    @DatabaseField(unique = true, canBeNull = false)
    public String address;
    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    public Bank bank;
    @DatabaseField(columnName = "latitude", canBeNull = false)
    public double lat;
    @DatabaseField(columnName = "longitude", canBeNull = false)
    public double lng;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class Columns {

        public static final String NAME = "name";
    }

}
