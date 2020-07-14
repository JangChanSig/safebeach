package intujuku.beachalarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import intujuku.beachalarm.DataManagements.DefaultValue;
import intujuku.beachalarm.DataManagements.HttpConnectionThread;
import intujuku.beachalarm.ListItem.MyAdapter;
import intujuku.beachalarm.ListItem.MyBeachListItem;
import intujuku.beachalarm.ListItem.myD2ListItem;

public class D2Activity extends AppCompatActivity {
    private ImageView back_btn;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<myD2ListItem> mListItems;

    private HttpConnectionThread mAuthTask = null;

    Activity d2Activity = this;
    int beachId;
    public abstract class OnClickListenerRefresh implements View.OnClickListener
    {
        protected ImageView cctv_img;
        protected String cctvid;
        protected int beachstate;
        protected TextView condition_text;
        protected TextView cctv_text;
        public OnClickListenerRefresh(ImageView cctv_img, String cctvid,int beachstate,TextView condition_text,TextView cctv_text)
        {
            this.cctv_img = cctv_img;
            this.cctvid = cctvid;
            this.beachstate = beachstate;
            this.condition_text = condition_text;
            this.cctv_text = cctv_text;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d2);

        Intent intent = getIntent();

        String beachName = intent.getExtras().getString("beachname");
        String cctvid = intent.getExtras().getString("cctvid");
        int beachstate = intent.getExtras().getInt("beachstate");
        beachId = intent.getExtras().getInt("beachid");

        initMyListItems();
        requestMessageProcess();
        mRecyclerView = (RecyclerView) findViewById(R.id.recv_card);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new MyAdapter(mListItems, d2Activity);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.invalidate();

        TextView tb_title_d1 = (TextView) findViewById(R.id.tb_title_d1); // 해수욕장 이름넣기
        TextView cctv_text  = (TextView) findViewById(R.id.cctv_text);     // cctv있으면 해수욕장 CCTV, 없으면 해수욕장 상태
        TextView condition_text = (TextView) findViewById(R.id.condition_text);  // 신호등 상황
        ImageView refresh = (ImageView) findViewById(R.id.refresh);         // 새로고침 버튼
        ImageView cctv_img = (ImageView) findViewById(R.id.cctv_img);        //cctv 있으면 이미지 삽입하고 visible
        back_btn = (ImageView) findViewById(R.id.back_btn);

        tb_title_d1.setText(beachName);

        infoUpdate(cctv_img,cctvid,beachstate,condition_text,cctv_text);

        refresh.setOnClickListener(new OnClickListenerRefresh(cctv_img,cctvid,beachstate,condition_text,cctv_text) {
            @Override
            public void onClick(View view) {

                infoUpdate(cctv_img,cctvid,beachstate,condition_text,cctv_text);
                //Intent intent = new Intent(d2Activity,D2Activity.class);
                // 서버에서 신로등정보 및 cctv정보 다시 가져와서 업데이트.
                // TODO : click event
                //requestMessageProcess();
                //update();
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void infoUpdate(ImageView cctv_img, String cctvid,int beachstate,TextView condition_text,TextView cctv_text)
    {
        if(cctvid!=null && !cctvid.equals("-"))
        {
            cctv_img.setVisibility(View.VISIBLE);
            Glide.with(this).load("http://220.95.232.18/camera/"+cctvid+".jpg?"+System.currentTimeMillis())
                    .into(cctv_img);
            cctv_text.setText("CCTV 정보");
        }
        else
        {
            cctv_img.setVisibility(View.GONE);
            cctv_text.setText("혼잡도 정보");
        }

        switch (beachstate)
        {
            case 0:
                condition_text.setText("정보없음");
                int color4 = ContextCompat.getColor(this,R.color.empty);
                condition_text.setTextColor(color4);
                break;
            case 1 :
                condition_text.setText("여유");
                int color1 = ContextCompat.getColor(this,R.color.good);
                condition_text.setTextColor(color1);
                break;
            case 2:
                condition_text.setText("보통");
                int color2 = ContextCompat.getColor(this,R.color.normal);
                condition_text.setTextColor(color2);
                break;
            case 3:
                condition_text.setText("혼잡");
                int color3 = ContextCompat.getColor(this,R.color.bad);
                condition_text.setTextColor(color3);
                break;
        }

        //.centerCrop().crossFade().placeholder(R.drawable.loading_spinner)
        // 신호등 정보 및 cctv정보 확인하고 업데이트
    }


    public void initMyListItems(){
        mListItems = new ArrayList<myD2ListItem>();
//        mListItems.add(new myD2ListItem("선창 횟집","부산시 대운대구 대운대로 1길","회가 숙성이 잘되어서 엄청 쫄깃하고 매운탕도 깔끔하다.", R.drawable.sushi));
//        mListItems.add(new myD2ListItem("선창 횟집","부산시 대운대구 대운대로 1길","회가 숙성이 잘되어서 엄청 쫄깃하고 매운탕도 깔끔하다.", R.drawable.sushi));
//        mListItems.add(new myD2ListItem("선창 횟집","부산시 대운대구 대운대로 1길","회가 숙성이 잘되어서 엄청 쫄깃하고 매운탕도 깔끔하다.", R.drawable.sushi));
//        mListItems.add(new myD2ListItem("선창 횟집","부산시 대운대구 대운대로 1길","회가 숙성이 잘되어서 엄청 쫄깃하고 매운탕도 깔끔하다.", R.drawable.sushi));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void requestMessageProcess() {
        if (mAuthTask != null) {
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("BEACHID",beachId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String reqMsg =jsonObject.toString();
        mAuthTask = new HttpConnectionThread(d2Activity);
        try {
            String airUrl = getString(R.string.server_url)+"/detail";
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
                Toast.makeText(d2Activity, getString(R.string.error_server_not_working), Toast.LENGTH_SHORT).show();
            } else {
                JSONArray jsonArray = new JSONArray(responseMsg);
                mAuthTask = null;
                listTupleParser(jsonArray);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    public void listTupleParser(JSONArray listData){
        mListItems = new ArrayList<myD2ListItem>();
        try {
            for(int i=0; i < listData.length(); i++){
                JSONObject jsonTuple  = (JSONObject)listData.get(i);
                int storeId = jsonTuple.getInt("STOREID");
                String storeName = jsonTuple.getString("STORENM");
                String storePhone = jsonTuple.getString("STOREPHN");
                String instarUrl = jsonTuple.getString("INSTAURL");
                String storeType = jsonTuple.getString("STORETYPE");
                String storeAdd = jsonTuple.getString("STOREADD");
                String storeContents = jsonTuple.getString("STOREREMARK");
                Double lat = Double.parseDouble(jsonTuple.getString("LAT"));
                Double lng = Double.parseDouble(jsonTuple.getString("LNG"));
                mListItems.add(new myD2ListItem(storeId,storeName,storePhone,instarUrl,storeType,storeAdd,storeContents,lat,lng));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        try {
            mAdapter.notifyDataSetChanged();
        }catch (NullPointerException e){
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }


}