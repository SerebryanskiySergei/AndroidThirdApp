package com.example.violey.thirdapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by VioLeY on 09.12.2014.
 */
public class DbHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "forest.db";
    private static final int DATABASE_VERSION = 3;

    private ZoneObjectDao zoneObjectDao;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, ZoneObject.class);
        } catch (SQLException e) {
            Log.d("tag", "Can't create database " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            TableUtils.dropTable(connectionSource, ZoneObject.class, true);
        } catch (SQLException e) {
            Log.d("tag","Can't drop databases "+ e);
            throw new RuntimeException(e);
        }
        onCreate(sqLiteDatabase, connectionSource);
    }

    public ZoneObjectDao getZoneObjectDao() throws SQLException {
        if (zoneObjectDao == null) {
            zoneObjectDao = new ZoneObjectDao(getConnectionSource());
        }
        return zoneObjectDao;
    }
    public static DbHelper getInstance(Context context) {
        return OpenHelperManager.getHelper(context, DbHelper.class);
    }
}
