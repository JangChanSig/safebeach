package intujuku.beachalarm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import intujuku.beachalarm.ListItem.myD2ListItem;
import intujuku.beachalarm.ListItem.referenceAdapter;
import intujuku.beachalarm.ListItem.referenceItem;
import intujuku.beachalarm.BuildConfig;

public class InfoActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<referenceItem> reListItems;
    private TextView versionCode;

    ImageView img_intusser, img_norajuku;
    private String linkUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        img_intusser = findViewById(R.id.img_intuseer);
        img_intusser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linkConnect(getString(R.string.intuseer_url), getString(R.string.intuseer_url_text));
            }
        });
        img_norajuku = findViewById(R.id.img_norajuku);
        img_norajuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linkConnect(getString(R.string.norajuku_url), getString(R.string.norajuku_url_text));
            }
        });
        versionCode = findViewById(R.id.versionCode);
        versionCode.setText(BuildConfig.VERSION_NAME);

        initMyListItems();
        mRecyclerView = (RecyclerView) findViewById(R.id.ref_rec);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new referenceAdapter(reListItems);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.invalidate();


    }
    public void linkConnect(String url, String title){
        linkUrl = url;
        AlertDialog.Builder builder = new AlertDialog.Builder(InfoActivity.this);
        builder.setTitle(title);
        builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl));
                startActivity(intent);
            }
        });
        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }
    public void initMyListItems(){
        reListItems = new ArrayList<referenceItem>();
        reListItems.add(new referenceItem("해양 수산부","http://www.mof.go.kr", "(신호등 데이터, CCTV 데이터)",R.drawable.sea_gov));
        reListItems.add(new referenceItem("바다여행","https://www.seantour.com", "(신호등 데이터)", R.drawable.sea_travel));
        reListItems.add(new referenceItem("연안포털","http://coast.mof.go.kr", "(CCTV 데이터)", R.drawable.sample));
    }
}