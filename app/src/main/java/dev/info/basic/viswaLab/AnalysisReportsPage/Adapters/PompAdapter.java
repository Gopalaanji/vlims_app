package dev.info.basic.viswaLab.AnalysisReportsPage.Adapters;

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
import dev.info.basic.viswaLab.AnalysisReportsPage.models.AnalysisFoModel;
import dev.info.basic.viswaLab.R;

public class PompAdapter extends RecyclerView.Adapter<PompAdapter.viewHolder> {

    private Context context;
    PompAdapter.clickListener mClickListener;
    List<AnalysisFoModel> mReportDataModelList = new ArrayList<>();
    private AnalysisReportsAdapter.ListenerInterface listenerInterface;
    String from;


    public PompAdapter(Context context, String from, List<AnalysisFoModel> mReportDataModelList,AnalysisReportsAdapter.ListenerInterface listenerInterface) {
        this.context = context;
        this.from = from;
        this.mReportDataModelList = mReportDataModelList;
        this.listenerInterface = listenerInterface;


    }

    @Override
    public PompAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.al_reports_fueloil_reports_item, null);
        PompAdapter.viewHolder viewHolder = new PompAdapter.viewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PompAdapter.viewHolder holder, int position) {
        final AnalysisFoModel analysisFoModel = mReportDataModelList.get(position);
        holder.tvShipName.setText(analysisFoModel.getShipName());
        if (analysisFoModel.getBunkerDate() != null) {
            holder.tvBunkerDate.setText("   "+analysisFoModel.getBunkerDate() + "\n" + analysisFoModel.getGrade());
        } else if (analysisFoModel.getReportDate() != null) {
            holder.tvBunkerDate.setText("    "+analysisFoModel.getReportDate() + "\n" + analysisFoModel.getGrade());
        }


        if (mReportDataModelList.get(position).getvlims_overall_fuel_condition() > 0) {
            if (mReportDataModelList.get(position).getvlims_overall_fuel_condition()==1){
                holder.tvReult.setImageResource(R.drawable.result_green);
            }
            else if(mReportDataModelList.get(position).getvlims_overall_fuel_condition()==2||mReportDataModelList.get(position).getvlims_overall_fuel_condition()==3||mReportDataModelList.get(position).getvlims_overall_fuel_condition()==4){
                holder.tvReult.setImageResource(R.drawable.result_orange);
            }else{
                holder.tvReult.setImageResource(R.drawable.result_red);
            }
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
            return new SimpleDateFormat("dd/MMM/yyyy").format(d).toString();
        } catch (Exception e) {

        }
        return jsondate;
    }

    public interface clickListener {
        void itemClicked(String sr);
    }

}


