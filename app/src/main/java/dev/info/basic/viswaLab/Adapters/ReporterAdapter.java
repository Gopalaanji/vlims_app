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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.models.ReportDataModel;

/**
 * Created by ${GIRI} on 09-01-2018.
 */

public class ReporterAdapter extends RecyclerView.Adapter<ReporterAdapter.viewHolder> {

    private Context context;
    CalculationsListAdapter.clickListener mClickListener;
    List<ReportDataModel> mReportDataModelList = new ArrayList<>();
    boolean from_alert = false;
    String userId;


    public ReporterAdapter(Context context, boolean from_alert, List<ReportDataModel> mReportDataModelList, String userId) {
        this.context = context;
        this.from_alert = from_alert;
        this.mReportDataModelList = mReportDataModelList;
        this.userId = userId;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reports_single_item, null);
        viewHolder viewHolder = new viewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        final ReportDataModel reportDataModel = mReportDataModelList.get(position);

        if (from_alert) {
            holder.tvSerialNo.setText(reportDataModel.getShipName());
            holder.tvDate.setVisibility(View.VISIBLE);
            String ackwardDate = reportDataModel.getTestDate();
            holder.tvDate.setText(ConvertJsonDate(reportDataModel.getTestDate()));

//Dirty convertion
          /*  Calendar calendar = Calendar.getInstance();
            String ackwardRipOff = ackwardDate.replace("/Date(", "").replace(")/", "");
            Long timeInMillis = Long.valueOf(ackwardRipOff);
            calendar.setTimeInMillis(timeInMillis);*/
//            holder.tvDate.setText(calendar.getD);
        } else {
            holder.tvDate.setVisibility(View.GONE);
            holder.tvSerialNo.setText(reportDataModel.getSerial());
        }

        holder.tvEqname.setText(reportDataModel.getEquipment());

        if (mReportDataModelList.get(position).getOilCondition().equals("1")) {
            holder.tvReult.setImageResource(R.drawable.result_green);
        } else if (mReportDataModelList.get(position).getOilCondition().equals("2")) {
            holder.tvReult.setImageResource(R.drawable.result_orange);
        } else {
            holder.tvReult.setImageResource(R.drawable.result_red);
        }
        holder.tvReult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://173.11.229.171/viswaweb/VLReports/LOReports/" + reportDataModel.getSerial().toString() + ".pdf"));
                context.startActivity(browserIntent);

          /*      Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://173.11.229.171/viswaweb/LO_CLOPreview.aspx?strUserId=" + userId + "&&SampType=3&&strSerial=" + reportDataModel.getSerial().toString()));
                Log.v("xxx","http://173.11.229.171/viswaweb/LO_CLOPreview.aspx?strUserId=" + userId + "&&SampType=3&&strSerial=" + reportDataModel.getSerial().toString());
                context.startActivity(browserIntent);*/
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
        private TextView tvSerialNo, tvEqname, tvDate;
        private ImageView tvReult;

        public viewHolder(View itemView) {
            super(itemView);
            tvSerialNo = (TextView) itemView.findViewById(R.id.tvSerialNo);
            tvEqname = (TextView) itemView.findViewById(R.id.tvEqname);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvReult = (ImageView) itemView.findViewById(R.id.tvReult);
        }

        @Override
        public void onClick(View v) {
            /*if (mList.get(getAdapterPosition()).getApprovedStatus())
                Toast.makeText(context, "Already approved", Toast.LENGTH_SHORT).show();
            else*/
//            mClickListener.itemClicked(mList.get(getAdapterPosition()).getCalcId());
        }
    }

    public static String ConvertJsonDate(String jsondate) {
        try {
            jsondate = jsondate.replace("/Date(", "").replace(")/", "");
            long time = Long.parseLong(jsondate);
            Date d = new Date(time);
            return new SimpleDateFormat("dd/MM/yyyy").format(d).toString();
        } catch (Exception e) {

        }
        return jsondate;
    }

    public interface clickListener {
        void itemClicked(String calcId);
    }
}
