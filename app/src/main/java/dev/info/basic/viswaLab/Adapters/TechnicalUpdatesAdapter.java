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
 * Created by ${GIRI} on 07-02-2018.
 */

public class TechnicalUpdatesAdapter  extends RecyclerView.Adapter<TechnicalUpdatesAdapter.viewHolder> {

    private Context context;
    CalculationsListAdapter.clickListener mClickListener;
    List<ScheduleAlertModel> mReportDataModelList = new ArrayList<>();
    boolean from_alert = false;
    String userId;


    public TechnicalUpdatesAdapter(Context context, boolean from_alert, List<ScheduleAlertModel> mReportDataModelList, String userId) {
        this.context = context;
        this.from_alert = from_alert;
        this.mReportDataModelList = mReportDataModelList;
        this.userId = userId;
    }

    @Override
    public TechnicalUpdatesAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.technical_updates_item, null);
        TechnicalUpdatesAdapter.viewHolder viewHolder = new TechnicalUpdatesAdapter.viewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TechnicalUpdatesAdapter.viewHolder holder, int position) {
        final ScheduleAlertModel reportDataModel = mReportDataModelList.get(position);
        holder.tvDate.setText(ConvertJsonDate(reportDataModel.getSheduleDate()));
        holder.tvDesc.setText(reportDataModel.getEquipment());
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

    public static String ConvertJsonDate(String jsondate) {

        jsondate = jsondate.replace("/Date(", "").replace(")/", "");
        long time = Long.parseLong(jsondate);
        Date d = new Date(time);
        return new SimpleDateFormat("dd/MM/yyyy").format(d).toString();
    }

    public interface clickListener {
        void itemClicked(String calcId);
    }
}
