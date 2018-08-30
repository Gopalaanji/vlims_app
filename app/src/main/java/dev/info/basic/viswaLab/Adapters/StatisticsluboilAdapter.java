package dev.info.basic.viswaLab.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.models.SampleStatsModel;

/**
 * Created by ${GIRI} on 12-01-2018.
 */

public class StatisticsluboilAdapter extends RecyclerView.Adapter<StatisticsluboilAdapter.viewHolder> {

    private Context context;
    CalculationsListAdapter.clickListener mClickListener;
    List<SampleStatsModel> mReportDataModelList = new ArrayList<>();

    public StatisticsluboilAdapter(Context context, List<SampleStatsModel> mReportDataModelList) {
        this.context = context;
        this.mReportDataModelList = mReportDataModelList;
    }

    @Override
    public StatisticsluboilAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stats_item_layout, null);
        StatisticsluboilAdapter.viewHolder viewHolder = new StatisticsluboilAdapter.viewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StatisticsluboilAdapter.viewHolder holder, int position) {
        final SampleStatsModel reportDataModel = mReportDataModelList.get(position);

        holder.name.setText(reportDataModel.getDataFor());
        holder.curmnt.setText(reportDataModel.getCurrMonthCount().toString());
        holder.premnt.setText(reportDataModel.getPrevMonthCount().toString());
        holder.curyear.setText(reportDataModel.getYTDCount().toString());
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
        private TextView name, curmnt, premnt, curyear;

        public viewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            curmnt = (TextView) itemView.findViewById(R.id.curmnt);
            premnt = (TextView) itemView.findViewById(R.id.premnt);
            curyear = (TextView) itemView.findViewById(R.id.curyear);
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
