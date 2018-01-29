package dev.info.basic.viswaLab.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dev.info.basic.viswaLab.R;

/**
 * Created by acer on 17-11-2017.
 */

public class CalculationsListAdapter extends RecyclerView.Adapter<CalculationsListAdapter.viewHolder> {

    private Context context;
    clickListener mClickListener;
    List<Integer> images = new ArrayList<>();
    List<String> titles = new ArrayList<>();

    public CalculationsListAdapter(Context context, List<Integer> images, List<String> titles) {
        this.context = context;
        this.titles = titles;
        this.images = images;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_equipment_info, null);
        viewHolder viewHolder = new viewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        int msmeList = images.get(position);
        String title = titles.get(position);
        holder.imageView.setImageResource(msmeList);
        holder.txtName.setText(title);
//        holder.txtName.setText(msmeList.getCalcSaveTime());
//        if (mList.get(position).getApprovedStatus()) {
//            holder.txtMonth.setVisibility(View.VISIBLE);
//        } else {
//            holder.txtMonth.setVisibility(View.GONE);
//        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtName;
        private ImageView imageView;

        public viewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            imageView = (ImageView) itemView.findViewById(R.id.imgEquip);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            /*if (mList.get(getAdapterPosition()).getApprovedStatus())
                Toast.makeText(context, "Already approved", Toast.LENGTH_SHORT).show();
            else*/
//            mClickListener.itemClicked(mList.get(getAdapterPosition()).getCalcId());
        }
    }

    public interface clickListener {
        void itemClicked(String calcId);
    }
}
