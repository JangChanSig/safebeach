package intujuku.beachalarm.ListItem;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import intujuku.beachalarm.R;

public class MyBeachAdapter extends RecyclerView.Adapter<MyBeachAdapter.MyViewHolder> {
    private List<MyBeachListItem> mList;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtTitle, txtContents, txtSignal;
        public ImageView imgeBeach,imgSignal, imgSginalIcon, imgCctvIcon;
        private CardView mCardView;
        MyViewHolder(CardView v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(pos);
                        }
                    }
                }
            });

            mCardView = (CardView)v.findViewById(R.id.myCardView);
            txtTitle = (TextView) v.findViewById(R.id.card_title);
            txtContents = (TextView) v.findViewById(R.id.card_contents);
            txtSignal = (TextView) v.findViewById(R.id.card_signal);
            imgeBeach = (ImageView) v.findViewById(R.id.card_image_d1);
            imgSignal = (ImageView) v.findViewById(R.id.signal_icon);
            imgSginalIcon = (ImageView) v.findViewById(R.id.card_signal_icon);
            imgCctvIcon = (ImageView) v.findViewById(R.id.card_cctv_icon);

        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyBeachAdapter(List<MyBeachListItem> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    // Create new views (invoked by the custom_marker manager)
    @Override
    public MyBeachAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_list_map_card_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the custom_marker manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final MyBeachListItem myItem = mList.get(position);
        holder.imgeBeach.setImageResource(myItem.getImage());
        holder.txtTitle.setText(myItem.getIndex()+". "+myItem.getBeachName());
        holder.txtContents.setText(myItem.getAddress());

        Glide.with(context).load(context.getString(R.string.server_url)+"/beachImg"+"?"+"BEACHID="+myItem.getBeachId())
                .into(holder.imgeBeach);

        if(myItem.getBeachState() == 0) holder.imgSginalIcon.setVisibility(View.GONE);
        else    holder.imgSginalIcon.setVisibility(View.VISIBLE);
        if(myItem.getCctvId().equals("-")) holder.imgCctvIcon.setVisibility(View.GONE);
        else    holder.imgCctvIcon.setVisibility(View.VISIBLE);
        switch (myItem.getBeachState()){
            case 0:
                holder.txtSignal.setText("정보없음");
                holder.txtSignal.setTextColor(ContextCompat.getColor(context, R.color.empty));
                holder.imgSignal.setImageResource(R.drawable.gray);
                break;
            case 1:
                holder.txtSignal.setText("여유");
                holder.txtSignal.setTextColor(ContextCompat.getColor(context, R.color.good));
                holder.imgSignal.setImageResource(R.drawable.green);
                break;
            case 2:
                holder.txtSignal.setText("보통");
                holder.txtSignal.setTextColor(ContextCompat.getColor(context, R.color.normal));
                holder.imgSignal.setImageResource(R.drawable.yellow);
                break;
            case 3:
                holder.txtSignal.setText("혼잡");
                holder.txtSignal.setTextColor(ContextCompat.getColor(context, R.color.bad));
                holder.imgSignal.setImageResource(R.drawable.red);
                break;

        }
    }

    // Return the size of your dataset (invoked by the custom_marker manager)
    @Override
    public int getItemCount() {
        return this.mList.size();
    }

}