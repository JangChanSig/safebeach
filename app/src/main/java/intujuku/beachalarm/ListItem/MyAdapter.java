package intujuku.beachalarm.ListItem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

import intujuku.beachalarm.DataManagements.HttpConnectionThread;
import intujuku.beachalarm.InfoActivity;
import intujuku.beachalarm.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private String[] mDataset;
    private List<myD2ListItem> mList;
    private Context context;
    private String link_url;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private MyAdapter.OnItemClickListener mListener = null;

    public void setOnItemClickListener(MyAdapter.OnItemClickListener listener) {
        this.mListener = listener ;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;
        public TextView mTextView4;
        public ImageView mImageView;

        public MyViewHolder(CardView v) {
            super(v);
            mTextView1 = (TextView) v.findViewById(R.id.ref_card_text1);
            mTextView2 = (TextView) v.findViewById(R.id.ref_card_text2);
            mTextView3 = (TextView) v.findViewById(R.id.ref_card_text3);
            mTextView4 = (TextView) v.findViewById(R.id.card_text4);

            mImageView = (ImageView) v.findViewById(R.id.card_image_d2);


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<myD2ListItem> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_card_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final myD2ListItem myItem = mList.get(position);

        holder.mTextView1.setText(myItem.getStoreAddress());
        holder.mTextView2.setText(myItem.getStoreName());
        holder.mTextView3.setText(myItem.getContents());
        holder.mTextView4.setText("✆ "+myItem.getStorePhone());

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instarConnect(myItem.getInstarUrl(), myItem.getStoreName());

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("STOREID",myItem.getStoreId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String reqMsg =jsonObject.toString();
                HttpConnectionThread mAuthTask = new HttpConnectionThread(context);
                try {
                    String airUrl = context.getString(R.string.server_url)+"/storeview";
                    mAuthTask.execute(airUrl, reqMsg).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        Glide.with(context).load(context.getString(R.string.server_url)+"/instaImg"+"?"+"STOREID="+myItem.getStoreId())
                .into(holder.mImageView);

        holder.mTextView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneConnect(myItem.getStorePhone());
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("STOREID",myItem.getStoreId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String reqMsg =jsonObject.toString();
                HttpConnectionThread mAuthTask = new HttpConnectionThread(context);
                try {
                    String airUrl = context.getString(R.string.server_url)+"/storeview";
                    mAuthTask.execute(airUrl, reqMsg).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void phoneConnect(String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        context.startActivity(intent);
    }
    public void instarConnect(String instar, String title_str){

        link_url = instar;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title_str+"로 이동합니다.");
        builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link_url));
                context.startActivity(intent);
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

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.mList.size();
    }
}