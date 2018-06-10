package dev.info.basic.viswaLab.CautionAlertsReportsPage.Adapters;

import android.content.Context;
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

import dev.info.basic.viswaLab.Adapters.CalculationsListAdapter;
import dev.info.basic.viswaLab.AnalysisReportsPage.Adapters.AnalysisCylinderOilAdapter;
import dev.info.basic.viswaLab.AnalysisReportsPage.models.AnalysisFoModel;
import dev.info.basic.viswaLab.R;

public class CautionAlertsCylinderOilAdapter extends RecyclerView.Adapter<CautionAlertsCylinderOilAdapter.viewHolder> {

    private Context context;
    List<AnalysisFoModel> mReportDataModelList = new ArrayList<>();
    String from;
    CautionAlertsCylinderOilAdapter.CautionAlertsCloListner listenerInterface;

    public  CautionAlertsCylinderOilAdapter(Context context, String from, List<AnalysisFoModel> mReportDataModelList,CautionAlertsCylinderOilAdapter.CautionAlertsCloListner listenerInterface) {
        this.context = context;
        this.from = from;
        this.mReportDataModelList = mReportDataModelList;
        this.listenerInterface = listenerInterface;

    }

    @Override
    public CautionAlertsCylinderOilAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.al_reports_fueloil_cylinder_oil_reports_item, null);
        CautionAlertsCylinderOilAdapter.viewHolder viewHolder = new CautionAlertsCylinderOilAdapter.viewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CautionAlertsCylinderOilAdapter.viewHolder holder, int position) {
        final AnalysisFoModel analysisFoModel = mReportDataModelList.get(position);
        holder.tvShipName.setText(analysisFoModel.getShipName());
        if (analysisFoModel.getBunkerDate() != null) {
            holder.tvBunkerDate.setText(ConvertJsonDate(analysisFoModel.getBunkerDate()) + "\n" + analysisFoModel.getGrade());
        } else if (analysisFoModel.getReportDate() != null) {
            holder.tvBunkerDate.setText(ConvertJsonDate(analysisFoModel.getReportDate()) + "\n" + analysisFoModel.getGrade());
        }

        if (mReportDataModelList.get(position).getOilCondition() != null && mReportDataModelList.get(position).getOilCondition().equals("1")) {
            holder.tvReult.setImageResource(R.drawable.result_green);
        } else if (mReportDataModelList.get(position).getOilCondition() != null && mReportDataModelList.get(position).getOilCondition().equals("2")) {
            holder.tvReult.setImageResource(R.drawable.result_orange);
        } else {
            if (mReportDataModelList.get(position).getOilCondition() != null) {
                holder.tvReult.setImageResource(R.drawable.result_red);
            }
        }


        if (mReportDataModelList.get(position).getSepc() != null && mReportDataModelList.get(position).getSepc().equals("On")) {
            holder.tvReult.setImageResource(R.drawable.result_green);
        } /*else if (mReportDataModelList.get(position).getOilCondition().equals("2")) {
            holder.tvReult.setImageResource(R.drawable.result_orange);*/ else if (mReportDataModelList.get(position).getSepc() != null) {
            holder.tvReult.setImageResource(R.drawable.result_red);
        }

    }

    @Override
    public int getItemCount() {
        return mReportDataModelList.size();
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
        private TextView tvShipName, tvBunkerDate;
        private ImageView tvReult;

        public viewHolder(View itemView) {
            super(itemView);
            tvShipName = (TextView) itemView.findViewById(R.id.tvShipName);
            tvBunkerDate = (TextView) itemView.findViewById(R.id.tvBunkerDate);
            tvReult = (ImageView) itemView.findViewById(R.id.tvReult);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listenerInterface.itemClicked(mReportDataModelList.get(getAdapterPosition()).getSerial());
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

    public interface CautionAlertsCloListner {
        void itemClicked(String pdfFileName);
    }
}

