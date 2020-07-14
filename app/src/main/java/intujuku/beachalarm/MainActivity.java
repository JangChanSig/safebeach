package intujuku.beachalarm;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import intujuku.beachalarm.DataManagements.DefaultValue;
import intujuku.beachalarm.DataManagements.HttpConnectionThread;
import intujuku.beachalarm.ListItem.MyBeachListItem;
import intujuku.beachalarm.MapManagements.GpsInfo;

public class MainActivity extends AppCompatActivity {

    Activity mainActivity = this;
    LatLng location = null;
    public static List<MyBeachListItem> mBeachList;
    private HttpConnectionThread mAuthTask = null;
    TextView txt_signal, txt_cctv, txt_total;
    ImageButton ibtn_dev;
    int signal_count=0;
    int sig_cnt_ic=0, sig_cnt_gw=0, sig_cnt_gg=0, sig_cnt_cb=0, sig_cnt_cn=0, sig_cnt_gb=0,
            sig_cnt_gn=0, sig_cnt_jb=0, sig_cnt_jn =0, sig_cnt_bs=0, sig_cnt_us=0;
    int cctv_cnt_ic=0, cctv_cnt_gw=0, cctv_cnt_gg=0, cctv_cnt_cb=0, cctv_cnt_cn=0, cctv_cnt_gb=0,
            cctv_cnt_gn=0, cctv_cnt_jb=0, cctv_cnt_jn =0, cctv_cnt_bs=0, cctv_cnt_us=0;

    Button incheon_btn, gangwon_btn, kunggi_btn, choongbook_btn, choongnam_btn,
            kungbook_btn, junbook_btn, ulsan_btn, kungnam_btn, busan_btn, junnam_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView search_btn = (ImageView) findViewById(R.id.search_btn);
        incheon_btn = (Button) findViewById(R.id.incheon_btn) ;
        gangwon_btn = (Button) findViewById(R.id.gangwon_btn) ;
        kunggi_btn = (Button) findViewById(R.id.kunggi_btn) ;
        choongbook_btn = (Button) findViewById(R.id.choongbook_btn) ;
        choongnam_btn = (Button) findViewById(R.id.choongnam_btn) ;
        kungbook_btn = (Button) findViewById(R.id.kungbook_btn) ;
        junbook_btn = (Button) findViewById(R.id.junbook_btn) ;
        ulsan_btn = (Button) findViewById(R.id.pohang_btn) ;
        kungnam_btn = (Button) findViewById(R.id.kungnam_btn) ;
        busan_btn = (Button) findViewById(R.id.busan_btn) ;
        junnam_btn = (Button) findViewById(R.id.junnam_btn) ;

        txt_total = findViewById(R.id.txt_total);
        txt_cctv = findViewById(R.id.txt_cctv);
        txt_signal = findViewById(R.id.txt_signal);
        ibtn_dev = findViewById(R.id.dev_btn);

        ibtn_dev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });

        search_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationPermissionCheck();
                GpsInfo gpsInfo = new GpsInfo(MainActivity.this);
                location = new LatLng(gpsInfo.getLatitude(), gpsInfo.getLongitude());
                Intent intent = new Intent(MainActivity.this, MapListActivity.class);
                intent.putExtra("lat", location.latitude);
                intent.putExtra("lng", location.longitude);
                intent.putExtra("name", "위치");
                startActivity(intent);
                // TODO : click event
            }
        });

        incheon_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, MapListActivity.class);
                intent.putExtra("lat", DefaultValue.INCHEON.latitude);
                intent.putExtra("lng",DefaultValue.INCHEON.longitude);
                intent.putExtra("name", DefaultValue.strIC);
                startActivity(intent);
                // TODO : click event
            }
        });

        gangwon_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, MapListActivity.class);
                intent.putExtra("lat", DefaultValue.GANGWON.latitude);
                intent.putExtra("lng", DefaultValue.GANGWON.longitude);
                intent.putExtra("name", DefaultValue.strGW);
                startActivity(intent);
                // TODO : click event
            }
        });
        kunggi_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, MapListActivity.class);
                intent.putExtra("lat", DefaultValue.GYUNGGI.latitude);
                intent.putExtra("lng", DefaultValue.GYUNGGI.longitude);
                intent.putExtra("name", DefaultValue.strGG);
                startActivity(intent);
                // TODO : click event
            }
        });
        choongbook_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, MapListActivity.class);
                intent.putExtra("lat", DefaultValue.CHUNGBUK.latitude);
                intent.putExtra("lng", DefaultValue.CHUNGBUK.longitude);
                intent.putExtra("name", DefaultValue.strCB);
                startActivity(intent);
                // TODO : click event
            }
        });
        choongnam_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, MapListActivity.class);
                intent.putExtra("lat", DefaultValue.CHUNGNAM.latitude);
                intent.putExtra("lng", DefaultValue.CHUNGNAM.longitude);
                intent.putExtra("name", DefaultValue.strCN);
                startActivity(intent);
                // TODO : click event
            }
        });
        kungbook_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, MapListActivity.class);
                intent.putExtra("lat", DefaultValue.GYUNGBUK.latitude);
                intent.putExtra("lng", DefaultValue.GYUNGBUK.longitude);
                intent.putExtra("name", DefaultValue.strGB);
                startActivity(intent);
                // TODO : click event
            }
        });
        junbook_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, MapListActivity.class);
                intent.putExtra("lat", DefaultValue.JEONBUK.latitude);
                intent.putExtra("lng", DefaultValue.JEONBUK.longitude);
                intent.putExtra("name", DefaultValue.strJB);
                startActivity(intent);
                // TODO : click event
            }
        });
        ulsan_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, MapListActivity.class);
                intent.putExtra("lat", DefaultValue.ULSAN.latitude);
                intent.putExtra("lng", DefaultValue.ULSAN.longitude);
                intent.putExtra("name", DefaultValue.strUS);
                startActivity(intent);
                // TODO : click event
            }
        });
        kungnam_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, MapListActivity.class);
                intent.putExtra("lat", DefaultValue.GYUNGNAM.latitude);
                intent.putExtra("lng", DefaultValue.GYUNGNAM.longitude);
                intent.putExtra("name", DefaultValue.strGN);
                startActivity(intent);
                // TODO : click event
            }
        });
        busan_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, MapListActivity.class);
                intent.putExtra("lat", DefaultValue.BUSAN.latitude);
                intent.putExtra("lng", DefaultValue.BUSAN.longitude);
                intent.putExtra("name", DefaultValue.strBS);
                startActivity(intent);
                // TODO : click event
            }
        });
        junnam_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, MapListActivity.class);
                intent.putExtra("lat", DefaultValue.JEONNAM.latitude);
                intent.putExtra("lng", DefaultValue.JEONNAM.longitude);
                intent.putExtra("name", DefaultValue.strJN);
                startActivity(intent);
                // TODO : click event
            }
        });
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        requestMessageProcess();
        requestRunMessageProcess();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void locationPermissionCheck() {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        }
    }

    private void requestRunMessageProcess() {
        if (mAuthTask != null) {
            return;
        }
        String reqMsg = "{}";
        mAuthTask = new HttpConnectionThread(MainActivity.this);
        try {
            String airUrl = getString(R.string.server_url)+"/run";
            runMessageResultProcess(mAuthTask.execute(airUrl, reqMsg).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void runMessageResultProcess(String responseMsg) {
        try {
            if(responseMsg.equals(DefaultValue.CONNECTION_FAIL)){
                Toast.makeText(MainActivity.this, getString(R.string.error_server_not_working), Toast.LENGTH_SHORT).show();
            } else {
                JSONArray jsonArray = new JSONArray(responseMsg);
                JSONObject jsonObject = (JSONObject)jsonArray.get(0);
                txt_total.setText(String.valueOf(jsonObject.getInt("VIEWCNT")));
                txt_cctv.setText(String.valueOf(jsonObject.getInt("CCTVCNT")));
                mAuthTask = null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }


    private void requestMessageProcess() {
        if (mAuthTask != null) {
            return;
        }
        String reqMsg = "{}";
        mAuthTask = new HttpConnectionThread(MainActivity.this);
        try {
            String airUrl = getString(R.string.server_url)+"/header";
            messageResultProcess(mAuthTask.execute(airUrl, reqMsg).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void messageResultProcess(String responseMsg) {
        try {
            if(responseMsg.equals(DefaultValue.CONNECTION_FAIL)){
                Toast.makeText(MainActivity.this, getString(R.string.error_server_not_working), Toast.LENGTH_SHORT).show();
            } else {
//                JSONObject jsonResponse = new JSONObject(responseMsg);
                JSONArray jsonArray = new JSONArray(responseMsg);
                mAuthTask = null;
                listTupleParser(jsonArray);
                txt_signal.setText(String.valueOf(signal_count));
                setToolTip();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    public void listTupleParser(JSONArray listData){
        mBeachList = new ArrayList<MyBeachListItem>();
        try {
            for(int i=0; i < listData.length(); i++){
                JSONObject jsonTuple  = (JSONObject)listData.get(i);
                String cityName = jsonTuple.getString("CITYNM");
                String address = jsonTuple.getString("ADDRESS");
                int beachId = jsonTuple.getInt("BEACHID");
                String beachName = jsonTuple.getString("BEACHNM");
                int beachState = jsonTuple.getInt("BEACHSTATE");
                String cctvId = jsonTuple.getString("CCTVID");
                String lastUpdate = jsonTuple.getString("LASTUPDATE");
                Double lat = Double.parseDouble(jsonTuple.getString("LAT"));
                Double lng = Double.parseDouble(jsonTuple.getString("LNG"));
                String tell = jsonTuple.getString("LINKTEL");
                valueCount(cityName, cctvId, beachState);
                mBeachList.add(new MyBeachListItem(cityName,address,beachId,beachName,beachState,cctvId,lastUpdate,tell,lat,lng));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    public void valueCount(String cityName, String cctvId, int beachState){
        if(beachState != 0){
            signal_count++;
            if(cityName.equals(DefaultValue.strBS)) sig_cnt_bs++;
            else if(cityName.equals(DefaultValue.strIC)) sig_cnt_ic++;
            else if(cityName.equals(DefaultValue.strCB)) sig_cnt_cb++;
            else if(cityName.equals(DefaultValue.strCN)) sig_cnt_cn++;
            else if(cityName.equals(DefaultValue.strGB)) sig_cnt_gb++;
            else if(cityName.equals(DefaultValue.strGN)) sig_cnt_gn++;
            else if(cityName.equals(DefaultValue.strGG)) sig_cnt_gg++;
            else if(cityName.equals(DefaultValue.strGW)) sig_cnt_gw++;
            else if(cityName.equals(DefaultValue.strJB)) sig_cnt_jb++;
            else if(cityName.equals(DefaultValue.strJN)) sig_cnt_jn++;
            else if(cityName.equals(DefaultValue.strUS)) sig_cnt_us++;
        }
        if(!cctvId.equals("-")){
            if(cityName.equals(DefaultValue.strBS)) cctv_cnt_bs++;
            else if(cityName.equals(DefaultValue.strIC)) cctv_cnt_ic++;
            else if(cityName.equals(DefaultValue.strCB)) cctv_cnt_cb++;
            else if(cityName.equals(DefaultValue.strCN)) cctv_cnt_cn++;
            else if(cityName.equals(DefaultValue.strGB)) cctv_cnt_gb++;
            else if(cityName.equals(DefaultValue.strGN)) cctv_cnt_gn++;
            else if(cityName.equals(DefaultValue.strGG)) cctv_cnt_gg++;
            else if(cityName.equals(DefaultValue.strGW)) cctv_cnt_gw++;
            else if(cityName.equals(DefaultValue.strJB)) cctv_cnt_jb++;
            else if(cityName.equals(DefaultValue.strJN)) cctv_cnt_jn++;
            else if(cityName.equals(DefaultValue.strUS)) cctv_cnt_us++;
        }
    }
    public void setToolTip(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            incheon_btn.setTooltipText("신호등 해수욕장: "+sig_cnt_ic+"\n"+"CCTV 해수욕장: "+cctv_cnt_ic);
            gangwon_btn.setTooltipText("신호등 해수욕장: "+sig_cnt_gw+"\n"+"CCTV 해수욕장: "+cctv_cnt_gw);
            kunggi_btn.setTooltipText("신호등 해수욕장: "+sig_cnt_gg+"\n"+"CCTV 해수욕장: "+cctv_cnt_gg);
            choongbook_btn.setTooltipText("신호등 해수욕장: "+sig_cnt_cb+"\n"+"CCTV 해수욕장: "+cctv_cnt_cb);
            choongnam_btn.setTooltipText("신호등 해수욕장: "+sig_cnt_cn+"\n"+"CCTV 해수욕장: "+cctv_cnt_cb);
            kungbook_btn.setTooltipText("신호등 해수욕장: "+sig_cnt_gb+"\n"+"CCTV 해수욕장: "+cctv_cnt_gb);
            junbook_btn.setTooltipText("신호등 해수욕장: "+sig_cnt_jb+"\n"+"CCTV 해수욕장: "+cctv_cnt_jb);
            ulsan_btn.setTooltipText("신호등 해수욕장: "+sig_cnt_us+"\n"+"CCTV 해수욕장: "+cctv_cnt_us);
            kungnam_btn.setTooltipText("신호등 해수욕장: "+sig_cnt_gn+"\n"+"CCTV 해수욕장: "+cctv_cnt_gn);
            busan_btn.setTooltipText("신호등 해수욕장: "+sig_cnt_bs+"\n"+"CCTV 해수욕장: "+cctv_cnt_bs);
            junnam_btn.setTooltipText("신호등 해수욕장: "+sig_cnt_jn+"\n"+"CCTV 해수욕장: "+cctv_cnt_jn);
        }
    }

}