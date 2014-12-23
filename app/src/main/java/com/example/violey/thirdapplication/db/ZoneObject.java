package com.example.violey.thirdapplication.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

/**
 * Created by VioLeY on 09.12.2014.
 */
@DatabaseTable
public class ZoneObject  {
    public static final String FIELD_ZONE_ID = "field_zone_id";
    public static final String FIELD_COORDX = "field_coordx";
    public static final String FIELD_COORDY = "field_coordy";
    public static final String FIELD_RADIUS = "field_radius";
    public static final String FIELD_CHECKED = "field_checked";

    @DatabaseField(columnName = FIELD_ZONE_ID, id=true)
    private int zoneId;
    @DatabaseField(columnName = FIELD_COORDX)
    private int coordX;
    @DatabaseField(columnName = FIELD_COORDY)
    private int coordY;
    @DatabaseField(columnName = FIELD_RADIUS)
    private int radius;
    @DatabaseField(columnName = FIELD_CHECKED)
    private boolean checked;


    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
