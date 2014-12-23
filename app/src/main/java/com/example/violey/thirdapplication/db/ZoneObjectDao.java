package com.example.violey.thirdapplication.db;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by VioLeY on 09.12.2014.
 */
public class ZoneObjectDao extends BaseDaoImpl<ZoneObject, Integer> {

    protected ZoneObjectDao(Class<ZoneObject> dataClass) throws SQLException {
        super(dataClass);
    }
    public ZoneObjectDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, ZoneObject.class);
    }

    public ZoneObject getZoneById(String name) throws SQLException {
        ZoneObject obj = queryBuilder().where().eq(ZoneObject.FIELD_ZONE_ID, name).queryForFirst();
        return obj;
    }
    public List<ZoneObject> getZonesUnChecked() throws SQLException {
        return queryBuilder().where().eq(ZoneObject.FIELD_CHECKED, false).query();
    }

}
