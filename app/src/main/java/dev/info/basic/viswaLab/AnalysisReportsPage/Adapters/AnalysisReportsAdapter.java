package dev.info.basic.viswaLab.AnalysisReportsPage.Adapters;

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

import dev.info.basic.viswaLab.Adapters.CalculationsListAdapter;
import dev.info.basic.viswaLab.AnalysisReportsPage.models.AnalysisFoModel;
import dev.info.basic.viswaLab.R;

/**
 * Created by Giri Thangellapally on 22-03-2018.
 */

public class AnalysisReportsAdapter extends RecyclerView.Adapter<AnalysisReportsAdapter.viewHolder> {

    private Context context;
    CalculationsListAdapter.clickListener mClickListener;
    List<AnalysisFoModel> mReportDataModelList = new ArrayList<>();
    String from;
    String userId;

    public AnalysisReportsAdapter(Context context, String from, List<AnalysisFoModel> mReportDataModelList, String userId) {
        this.context = context;
        this.from = from;
        this.mReportDataModelList = mReportDataModelList;
        this.userId = userId;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.al_reports_fueloil_reports_item, null);
        viewHolder viewHolder = new viewHolder(view);
        return viewHolder;
    }

    @Override

    public void onBindViewHolder(AnalysisReportsAdapter.viewHolder holder, int position) {
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
        holder.tvReult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = null;
                if (from.equals("CLO")) {
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://173.11.229.171/viswaweb/VLReports/CLOeports/" + analysisFoModel.getSerial().toString() + ".pdf"));
                } else if (from.equals("POMP_AR")) {
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://173.11.229.171/viswaweb/VLReports/POMPReports/" + analysisFoModel.getSerial().toString() + "_POMP_REPORT(FINAL).pdf"));
                } else {
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://173.11.229.171/viswaweb/VLReports/FOReports/" + analysisFoModel.getSerial().toString() + ".pdf"));
                }
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
        private TextView tvShipName, tvBunkerDate;
        private ImageView tvReult;

        public viewHolder(View itemView) {
            super(itemView);
            tvShipName = (TextView) itemView.findViewById(R.id.tvShipName);
            tvBunkerDate = (TextView) itemView.findViewById(R.id.tvBunkerDate);
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
