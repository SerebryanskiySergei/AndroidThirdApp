package com.example.violey.thirdapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.violey.thirdapplication.db.DbHelper;
import com.example.violey.thirdapplication.db.ZoneObject;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by VioLeY on 09.12.2014.
 */
public class ZoneAdapter extends ArrayAdapter<ZoneObject> {
    OnCheckedChange obj;

    public ZoneAdapter(Context context, int resource, ArrayList<ZoneObject> zones) {
        super(context, resource,zones);
    }

    public void setObj(OnCheckedChange obj){
        this.obj=obj;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.zone_view, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.populate(getItem(position));
        return convertView;
    }
    private class Holder {
        private TextView zoneName;
        private TextView coord;
        private TextView radius;
        private CheckBox cb;

        public Holder (View view) {
            zoneName = (TextView) view.findViewById(R.id.zoneId);
            coord = (TextView) view.findViewById(R.id.zoneCoord);
            radius = (TextView) view.findViewById(R.id.zoneRadius);
            cb = (CheckBox)view.findViewById(R.id.checkBox);
        }

        public void populate(final ZoneObject zone) {
            cb.setOnCheckedChangeListener(null);
            cb.setChecked(zone.isChecked());
            zoneName.setText("Zone ID :"+zone.getZoneId());
            coord.setText("Coordinates\n-> X:"+zone.getCoordX()+"\n" +"-> Y:"+zone.getCoordY());
            radius.setText("Radius : "+zone.getRadius());
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    zone.setChecked(b);
                    if (obj!=null)
                        try {
                            obj.OnCheck(zone);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                }
            });
        }
    }

    public interface OnCheckedChange{
        public void OnCheck(ZoneObject currZone) throws SQLException;
    }
}
