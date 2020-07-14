package intujuku.beachalarm.ListItem;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import intujuku.beachalarm.R;

public class referenceAdapter extends RecyclerView.Adapter<referenceAdapter.MyViewHolder> {
    private String[] mDataset;
    private List<referenceItem> mList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;
        public ImageView mImageView;

        public MyViewHolder(CardView v) {
            super(v);
            mTextView1 = (TextView) v.findViewById(R.id.ref_card_text1);
            mTextView2 = (TextView) v.findViewById(R.id.ref_card_text2);
            mTextView3 = (TextView) v.findViewById(R.id.ref_card_text3);

            mImageView = (ImageView) v.findViewById(R.id.ref_card_image);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public referenceAdapter(List<referenceItem> mList) {
        this.mList = mList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reference_card_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final referenceItem myItem = mList.get(position);
        holder.mImageView.setImageResource(myItem.getImage());
        holder.mTextView1.setText(myItem.getTitle());
        holder.mTextView2.setText(myItem.getContents());
        holder.mTextView3.setText(myItem.getRef());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.mList.size();
    }
}