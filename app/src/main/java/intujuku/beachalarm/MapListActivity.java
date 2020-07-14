package intujuku.beachalarm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;


import java.util.ArrayList;
import java.util.List;
import intujuku.beachalarm.ListItem.MyBeachAdapter;
import intujuku.beachalarm.ListItem.MyBeachListItem;
import intujuku.beachalarm.MapManagements.MarkerItems;

public class MapListActivity extends AppCompatActivity implements OnInfoWindowClickListener, OnMapReadyCallback {
    private GoogleMap mMap;

    private ImageView back_btn;
    private RecyclerView mRecyclerView;
    private MyBeachAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<MyBeachListItem> mListItems;
    private List<MarkerItems> mMarkerItems;
    private String stateName = "";
    private LatLng location = new LatLng(0,0);

    private View marker_root_view;
    private TextView txt_marker;
    private Marker clicked_marker = null;
    private List<Marker> markers = new ArrayList<Marker>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_list);


        final MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mMap);
        mapFragment.getMapAsync(this);

        marker_root_view = LayoutInflater.from(this).inflate(R.layout.custom_marker, null);
        txt_marker = (TextView) marker_root_view.findViewById(R.id.marker_number);
        back_btn = (ImageView) findViewById(R.id.back_btn);

        Intent intent = getIntent();
        try {
            stateName = intent.getExtras().getString("name");
            location = new LatLng(intent.getExtras().getDouble("lat"), intent.getExtras().getDouble("lng"));
        }catch (NullPointerException e){}
        initMyListItems();

        mRecyclerView = (RecyclerView) findViewById(R.id.recv_card);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyBeachAdapter(mListItems, MapListActivity.this);
        mAdapter.setOnItemClickListener(new MyBeachAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos)
            {
                markers.get(pos).showInfoWindow();
                if(clicked_marker != null)  clicked_marker.hideInfoWindow();
                LatLng newCenter = new LatLng(mMarkerItems.get(pos).getLat(), mMarkerItems.get(pos).getLng());
                CameraPosition cameraPosition = new CameraPosition.Builder().target(newCenter).zoom(9).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.invalidate();

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initMyListItems(){
        int indexNum = 1;
        mListItems = new ArrayList<MyBeachListItem>();
        try {
            for(MyBeachListItem listItem : MainActivity.mBeachList) {
                if (listItem.getCityName().equals(stateName)) {
                    listItem.setIndex(indexNum++);
                    mListItems.add(listItem);
                }
                if(stateName.equals("위치")){
                    listItem.setIndex(indexNum++);
                    mListItems.add(listItem);
                }
            }
        }catch (NullPointerException e){

        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        markerInitialize();
//        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_retro));
        mMap.setOnInfoWindowClickListener(this);
    }
    @Override
    public void onInfoWindowClick(Marker marker){
        String subStr[] = marker.getTitle().split(". ");
        int index = Integer.parseInt(subStr[0]) - 1;

        String cctvId = mListItems.get(index).getCctvId();
        int beachState = mListItems.get(index).getBeachState();
        String beachName = mListItems.get(index).getBeachName();
        int beachId = mListItems.get(index).getBeachId();

        Intent intent = new Intent(MapListActivity.this, D2Activity.class);
        intent.putExtra("cctvid", cctvId);
        intent.putExtra("beachstate", beachState);
        intent.putExtra("beachname", beachName);
        intent.putExtra("beachid", beachId);

        startActivity(intent);
    }
    public void markerInitialize(){
        mMap.clear();
//        CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(10).build();
//        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,9));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                clicked_marker = marker;
                if(marker.getTitle().equals("내 위치")){
//                    mRecyclerView.scrollToPosition(Integer.parseInt(marker.getTitle()));
                }else {
                    mRecyclerView.scrollToPosition(Integer.parseInt(marker.getTitle().substring(0,1)) - 1);
                }
                return false;
            }
        });
        addMarkerData();
    }
    public void addMarkerData(){
        mMarkerItems = new ArrayList<MarkerItems>();

        for(int i = 0; i < mListItems.size(); i++){
            mMarkerItems.add(new MarkerItems(mListItems.get(i).getLat(), mListItems.get(i).getLng(), mListItems.get(i).getBeachState(), i+1));
        }
        for (MarkerItems markerItem : mMarkerItems) {
            addCustomMarker(markerItem);
        }
        ////        or
//        if(mapMarker != null)        mapMarker.remove();
//        mapMarker = mMap.addMarker(new MarkerOptions().position(location).title(locationName));
    }

    public void addCustomMarker(MarkerItems markerItems){
        LatLng position = new LatLng(markerItems.getLat(),markerItems.getLng());
        MarkerOptions markerOptions = new MarkerOptions();

        int index = markerItems.getIndex();
        int signal = markerItems.getSignal();
        try {
            txt_marker.setText(String.valueOf(index));
            switch(signal){
                case 0:
                    txt_marker.setBackgroundResource(R.drawable.marker_empty);
                    break;
                case 1:
                    txt_marker.setBackgroundResource(R.drawable.marker_green);
                    break;
                case 2:
                    txt_marker.setBackgroundResource(R.drawable.marker_yellow);
                    break;
                case 3:
                    txt_marker.setBackgroundResource(R.drawable.marker_red);
                    break;
            }
        }catch (NullPointerException e){
        }
        markerOptions.title(index + ". " +mListItems.get(index-1).getBeachName());
        markerOptions.snippet("  해수욕장 상세보기");
        markerOptions.position(position);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker_root_view)));

       markers.add(mMap.addMarker(markerOptions));

    }

    private Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }




}