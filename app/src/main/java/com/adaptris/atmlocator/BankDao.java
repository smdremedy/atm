package com.adaptris.atmlocator;

import com.google.android.gms.maps.model.LatLng;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import static com.adaptris.atmlocator.AtmPlace.*;
import static com.adaptris.atmlocator.AtmPlace.Columns.*;

public class BankDao extends BaseDaoImpl<Bank, String> {

    protected BankDao() throws SQLException {
        super(Bank.class);
    }

}
