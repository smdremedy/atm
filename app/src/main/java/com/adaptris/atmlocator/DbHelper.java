package com.adaptris.atmlocator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Sylwester on 2015-11-06.
 */
public class DbHelper extends OrmLiteSqliteOpenHelper {

    public DbHelper(Context context) {
        super(context, "atm.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, Bank.class);
            TableUtils.createTable(connectionSource, AtmPlace.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Dao<Bank, String> bankDao = getDao(Bank.class);
            bankDao.create(new Bank("ING Bank", "500600700"));
            bankDao.create(new Bank("Milenium", "500600701"));
            bankDao.create(new Bank("mBank", "500600702"));
            bankDao.create(new Bank("BGŻ", "500600703"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
