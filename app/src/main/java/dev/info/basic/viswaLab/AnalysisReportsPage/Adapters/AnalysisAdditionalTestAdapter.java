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
import dev.info.basic.viswaLab.AnalysisReportsPage.models.PurifierEffyResponseModel;
import dev.info.basic.viswaLab.R;

/**
 * Created by Giri Thangellapally on 23-04-2018.
 */
public class AnalysisAdditionalTestAdapter extends RecyclerView.Adapter<AnalysisAdditionalTestAdapter.viewHolder> {
    private Context context;
    CalculationsListAdapter.clickListener mClickListener;
    List<PurifierEffyResponseModel> mReportDataModelList = new ArrayList<>();
    String userId;

    public AnalysisAdditionalTestAdapter(Context context, List<PurifierEffyResponseModel> mReportDataModelList, String userId) {
        this.context = context;
        this.mReportDataModelList = mReportDataModelList;
        this.userId = userId;
    }

    @Override
    public AnalysisAdditionalTestAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.al_reports_additional_test_item, null);
        AnalysisAdditionalTestAdapter.viewHolder viewHolder = new AnalysisAdditionalTestAdapter.viewHolder(view);
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
        private TextView tvShipName, tvBunkerDate, tvTestResult;
        private ImageView tvReult;

        public viewHolder(View itemView) {
            super(itemView);
            tvShipName = (TextView) itemView.findViewById(R.id.tvShipName);
            tvBunkerDate = (TextView) itemView.findViewById(R.id.tvBunkerDate);
            tvTestResult = (TextView) itemView.findViewById(R.id.tvTestResult);
            tvReult = (ImageView) itemView.findViewById(R.id.tvReult);
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
    public void onBindViewHolder(AnalysisAdditionalTestAdapter.viewHolder holder, int position) {
        final PurifierEffyResponseModel analysisFoModel = mReportDataModelList.get(position);
        holder.tvShipName.setText(analysisFoModel.getShipName());
        holder.tvTestResult.setText(analysisFoModel.getResult());
        if (analysisFoModel.getBunkerDate() != null) {
            holder.tvBunkerDate.setText(ConvertJsonDate(analysisFoModel.getBunkerDate()) + "\n" + analysisFoModel.getBunkerPortName() + "\n" + analysisFoModel.getGrade() + "-" + analysisFoModel.getMatrix());
        }
        holder.tvReult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://173.11.229.171/viswaweb/VLReports/ADDLReports/" + analysisFoModel.getSerial().toString()+"_"+analysisFoModel.getAdditionalTest() + ".pdf"));
                context.startActivity(browserIntent);
            }
        });
    }
}
