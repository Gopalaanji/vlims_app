package dev.info.basic.viswaLab.AnalysisReportsPage.Adapters;

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

import dev.info.basic.viswaLab.Adapters.CalculationsListAdapter;
import dev.info.basic.viswaLab.AnalysisReportsPage.models.AnalysisDensityModel;
import dev.info.basic.viswaLab.R;

/**
 * Created by Giri Thangellapally on 23-04-2018.
 */
public class AnalysisDensitytAdapter extends RecyclerView.Adapter<AnalysisDensitytAdapter.viewHolder> {
    private Context context;
    CalculationsListAdapter.clickListener mClickListener;
    List<AnalysisDensityModel> mReportDataModelList = new ArrayList<>();
    String userId;

    public AnalysisDensitytAdapter(Context context, List<AnalysisDensityModel> mReportDataModelList, String userId) {
        this.context = context;
        this.mReportDataModelList = mReportDataModelList;
        this.userId = userId;
    }

    @Override
    public AnalysisDensitytAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.al_reports_density_item, null);
        AnalysisDensitytAdapter.viewHolder viewHolder = new AnalysisDensitytAdapter.viewHolder(view);
        return viewHolder;
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
        private TextView tvShipName, tvBunkerDate, tvDiffTonnes, tvQtyRecieved;

        public viewHolder(View itemView) {
            super(itemView);
            tvShipName = (TextView) itemView.findViewById(R.id.tvShipName);
            tvBunkerDate = (TextView) itemView.findViewById(R.id.tvBunkerDate);
            tvDiffTonnes = (TextView) itemView.findViewById(R.id.tvDiffTonnes);
            tvQtyRecieved = (TextView) itemView.findViewById(R.id.tvQtyRecieved);
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
            return new SimpleDateFormat("dd/MM/yyyy").format(d).toString();
        } catch (Exception e) {

        }
        return jsondate;
    }

    public interface clickListener {
        void itemClicked(String calcId);
    }

    @Override
    public void onBindViewHolder(AnalysisDensitytAdapter.viewHolder holder, int position) {
        final AnalysisDensityModel analysisFoModel = mReportDataModelList.get(position);
        holder.tvShipName.setText(analysisFoModel.getShipName());
        holder.tvDiffTonnes.setText(analysisFoModel.getDifferenceTonnes());
        holder.tvQtyRecieved.setText(analysisFoModel.getQtyReceived());
        if (analysisFoModel.getBunkerDate() != null) {
            holder.tvBunkerDate.setText(ConvertJsonDate(analysisFoModel.getBunkerDate()) + "\n" + analysisFoModel.getBunkerPort() + "\n" + analysisFoModel.getGrade() + "-" + analysisFoModel.getGradeMatrix());
        }

    }
}
