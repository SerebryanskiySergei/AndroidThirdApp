package com.example.violey.thirdapplication;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.violey.thirdapplication.db.DbHelper;
import com.example.violey.thirdapplication.db.ZoneObject;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.table.TableUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends ActionBarActivity implements ZoneAdapter.OnCheckedChange{

    ListView zoneListView;
    ArrayList<ZoneObject> zones;
    ZoneAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zoneListView = (ListView)findViewById(R.id.zoneListView);
        zones = new ArrayList<ZoneObject>();
        new RequestToServer().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            new RequestToServer().execute();
            zones.clear();
            DbHelper helper = DbHelper.getInstance(this);
            try {
                zones.addAll(helper.getZoneObjectDao().getZonesUnChecked());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnCheck(ZoneObject currZone) throws SQLException {
        DbHelper helper = DbHelper.getInstance(this);
        helper.getZoneObjectDao().update(currZone);
       // zones.clear();
        //zones.addAll(helper.getZoneObjectDao().getZonesUnChecked());
        //adapter.notifyDataSetChanged();
    }

    class RequestToServer extends AsyncTask<Void, Void, String> {
        public RequestToServer() {
            super();
        }
        @Override
        protected String doInBackground(Void... params) {
            String logString = "Starting bg task.";
            System.out.print("\n"+logString+"\n");
            try {
                URL url = new URL("http://10.0.2.2:1337/zone/read/");
                logString += "We are trying to send request for "+url.getPath().toString()+"\n";
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                logString+= "Trying to open connection.\n ";
                connection.connect();
                logString += "Trying to connect.\n";
                int response = connection.getResponseCode();
                logString += "Response taken.Code is : "+ Integer.toString(response)+"\n";
                InputStream inputStream = connection.getInputStream();
                logString += "Getting input stream: ";
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String resultJson = buffer.toString();
                return resultJson;
            } catch (java.io.IOException e) {
                e.printStackTrace();
            } finally {
            }
            return logString + "notWorking";
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String strJson) {
            JSONObject obj = new JSONObject();
            try {
                obj = new JSONObject(strJson);
                JSONArray zonesJson = obj.getJSONArray("zones");
                ZoneObject z = null;
                for (int i =0; i<=zonesJson.length()-1;i++) {
                    JSONObject currentItem = zonesJson.getJSONObject(i);
                    z = new ZoneObject();
                    z.setZoneId(currentItem.getInt("id"));
                    z.setCoordX(currentItem.getInt("locationX"));
                    z.setCoordY(currentItem.getInt("locationY"));
                    z.setRadius(currentItem.getInt("radius"));
                    z.setChecked(currentItem.getBoolean("checked"));
                    zones.add(z);
                }
                try {
                    DbHelper helper = DbHelper.getInstance(MainActivity.this);
                    helper.getZoneObjectDao().createOrUpdate(z);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                adapter = new ZoneAdapter(MainActivity.this,R.layout.zone_view, zones);
                zoneListView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void onCancelled(String str) {
            super.onCancelled(str);
        }
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }


}
