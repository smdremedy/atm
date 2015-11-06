package com.adaptris.atmlocator;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "bank", daoClass = BankDao.class)
public class Bank implements Serializable {

    public Bank() {
    }

    public Bank(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    @DatabaseField(id = true)
    public String name;

    @DatabaseField(canBeNull = false)
    public String phone;

    @Override
    public String toString() {
        return name;
    }
}
