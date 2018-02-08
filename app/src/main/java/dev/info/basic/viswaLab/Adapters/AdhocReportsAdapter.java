package dev.info.basic.viswaLab.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.models.AdhocReportsModel;

/**
 * Created by ${GIRI} on 08-02-2018.
 */

public class AdhocReportsAdapter extends RecyclerView.Adapter<AdhocReportsAdapter.viewHolder> {

    private Context context;
    List<AdhocReportsModel> mReportDataModelList = new ArrayList<>();


    public AdhocReportsAdapter(Context context, List<AdhocReportsModel> mReportDataModelList) {
        this.context = context;
        this.mReportDataModelList = mReportDataModelList;
    }


    @Override
    public AdhocReportsAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adhoc_reports_item, null);
        AdhocReportsAdapter.viewHolder viewHolder = new AdhocReportsAdapter.viewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdhocReportsAdapter.viewHolder holder, int position) {
        final AdhocReportsModel reportDataModel = mReportDataModelList.get(position);
        holder.tvDate.setText("Test Date   : " + reportDataModel.getTEST_DATE());
        holder.tvShipNameTitle.setText(reportDataModel.getSHIP_NAME());
        holder.tvTestName.setText("Test Name : " + reportDataModel.getTEST_NAME());
        holder.tvSerialNo.setText("Serial No    : " + reportDataModel.getSERIAL_NO());


        if (mReportDataModelList.get(position).getTEST_STATUS().equals("1")) {
            holder.statusImg.setImageResource(R.drawable.result_green);
        } else if (mReportDataModelList.get(position).getTEST_STATUS().equals("2")) {
            holder.statusImg.setImageResource(R.drawable.result_orange);
        } else {
            holder.statusImg.setImageResource(R.drawable.result_red);
        }

        holder.statusImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(reportDataModel.getLINK()));
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
        TextView tvShipNameTitle, tvTestName, tvDate, tvSerialNo;
        ImageView statusImg;

        public viewHolder(View itemView) {
            super(itemView);
            tvShipNameTitle = (TextView) itemView.findViewById(R.id.tvShipNameTitle);
            tvTestName = (TextView) itemView.findViewById(R.id.tvTestName);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvSerialNo = (TextView) itemView.findViewById(R.id.tvSerialNo);
            statusImg = (ImageView) itemView.findViewById(R.id.statusImg);
        }

        @Override
        public void onClick(View v) {

        }
    }


    public interface clickListener {
        void itemClicked(String calcId);
    }
}
