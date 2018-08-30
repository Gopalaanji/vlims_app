package dev.info.basic.viswaLab.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.models.ScheduleAlertModel;

/**
 * Created by ${GIRI} on 28-01-2018.
 */

public class ScheduleAlertsAdapter extends RecyclerView.Adapter<ScheduleAlertsAdapter.viewHolder> {

    private Context context;
    CalculationsListAdapter.clickListener mClickListener;
    List<ScheduleAlertModel> mReportDataModelList = new ArrayList<>();
    boolean from_alert = false;
    String userId;


    public ScheduleAlertsAdapter(Context context, boolean from_alert, List<ScheduleAlertModel> mReportDataModelList, String userId) {
        this.context = context;
        this.from_alert = from_alert;
        this.mReportDataModelList = mReportDataModelList;
        this.userId = userId;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.schedule_alert_item, null);
        viewHolder viewHolder = new viewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        try {
            final ScheduleAlertModel reportDataModel = mReportDataModelList.get(position);
            holder.tvDate.setText(ConvertJsonDate(reportDataModel.getSheduleDate()));
            holder.tvShipName.setText(reportDataModel.getShipName());
            holder.tvEqname.setText(reportDataModel.getEquipment());
        } catch (Exception e) {

        }
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
        private TextView tvShipName, tvEqname, tvDate;

        public viewHolder(View itemView) {
            super(itemView);
            tvShipName = (TextView) itemView.findViewById(R.id.tvShipName);
            tvEqname = (TextView) itemView.findViewById(R.id.tvEqname);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public static String ConvertJsonDate(String jsondate) {

        try {
            jsondate = jsondate.replace("/Date(", "").replace(")/", "");
            long time = Long.parseLong(jsondate);
            Date d = new Date(time);
            return new SimpleDateFormat("dd/MMM/yyyy").format(d).toString();
        } catch (Exception e) {

        }
        return jsondate;
    }

    public interface clickListener {
        void itemClicked(String calcId);
    }
}
