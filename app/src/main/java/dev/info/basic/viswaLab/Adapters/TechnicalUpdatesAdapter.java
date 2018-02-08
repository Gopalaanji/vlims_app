package dev.info.basic.viswaLab.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.models.TechnicalUpdatesModel;

/**
 * Created by ${GIRI} on 07-02-2018.
 */

public class TechnicalUpdatesAdapter extends RecyclerView.Adapter<TechnicalUpdatesAdapter.viewHolder> {

    private Context context;
    List<TechnicalUpdatesModel> mReportDataModelList = new ArrayList<>();


    public TechnicalUpdatesAdapter(Context context, List<TechnicalUpdatesModel> mReportDataModelList) {
        this.context = context;
        this.mReportDataModelList = mReportDataModelList;
    }


    @Override
    public TechnicalUpdatesAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.technical_updates_item, null);
        TechnicalUpdatesAdapter.viewHolder viewHolder = new TechnicalUpdatesAdapter.viewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TechnicalUpdatesAdapter.viewHolder holder, int position) {
        final TechnicalUpdatesModel reportDataModel = mReportDataModelList.get(position);
        holder.tvDate.setText(reportDataModel.getDate());
        holder.tvDesc.setText(reportDataModel.getHeading());
        holder.tvDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(reportDataModel.getLink()));
                context.startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return
                mReportDataModelList.size();
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
        private TextView tvDate, tvDesc;

        public viewHolder(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvDesc = (TextView) itemView.findViewById(R.id.tvDesc);
        }

        @Override
        public void onClick(View v) {

        }
    }


    public interface clickListener {
        void itemClicked(String calcId);
    }
}
