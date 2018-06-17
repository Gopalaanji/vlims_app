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

/**
 * Created by Giri Thangellapally on 22-03-2018.
 */

public class AnalysisReportsAdapter extends RecyclerView.Adapter<AnalysisReportsAdapter.viewHolder> {

    private Context context;
    CalculationsListAdapter.clickListener mClickListener;
    List<AnalysisFoModel> mReportDataModelList = new ArrayList<>();
    private ListenerInterface listenerInterface;
    String from;


    public AnalysisReportsAdapter(Context context, String from, List<AnalysisFoModel> mReportDataModelList,ListenerInterface listenerInterface) {
        this.context = context;
        this.from = from;
        this.mReportDataModelList = mReportDataModelList;
        this.listenerInterface = listenerInterface;


    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.al_reports_fueloil_reports_item, null);
        viewHolder viewHolder = new viewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
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
/*
        holder.tvReult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = null;
                if (from.equals("CLO")&& analysisFoModel.getSerial().toString().equalsIgnoreCase("")) {
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://173.11.229.171/viswaweb/VLReports/CLOeports/" + analysisFoModel.getSerial().toString() + "_POMP_REPORT(FINAL).pdf"));
                } else if (from.equals("POMP_AR")) {
                    Intent intent=new Intent(context, PdfViewActivity.class);
                    intent.putExtra("pdf_name",analysisFoModel.getSerial().toString());
                    intent.putExtra("module_type","POMP_AR");
                    context.startActivity(intent);
//                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://74.208.185.23/VLIMSAPP/VL_POMPReports_Download/" +username+"/"+ pwd+"/"+ analysisFoModel.getSerial().toString() + "_POMP_REPORT(FINAL).pdf"));
//                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://74.208.185.23/VLIMSAPP/VL_POMPReports_Download/" +username+"/"+ pwd+"/"+ analysisFoModel.getSerial().toString() +".pdf"));
                } else {
                    if (from.equalsIgnoreCase("FO") && analysisFoModel.getSerial().toString().equalsIgnoreCase("")) {
                        if (analysisFoModel.getBunkerPort().equalsIgnoreCase("FO_AR")) {
                            browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://173.11.229.171/viswaweb/VLReports/SampleReports/FO.PDF"));
                        } else if (analysisFoModel.getBunkerPort().equalsIgnoreCase("LO_AR")) {
                            browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://173.11.229.171/viswaweb/VLReports/SampleReports/LO.PDF"));
                        } else if (analysisFoModel.getBunkerPort().equalsIgnoreCase("CLO_AR")) {
                            browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://173.11.229.171/viswaweb/VLReports/SampleReports/CLO.PDF"));
                        } else if (analysisFoModel.getBunkerPort().equalsIgnoreCase("POMP_AR")) {
                            browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://173.11.229.171/viswaweb/VLReports/SampleReports/POMP.PDF"));
                        }else if(analysisFoModel.getBunkerPort().equalsIgnoreCase("FO_CA")){
                            browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://173.11.229.171/viswaweb/VLReports/SampleReports/FO_C.PDF"));
                        }else if(analysisFoModel.getBunkerPort().equalsIgnoreCase("CLO_CO")){
                            browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(":  http://173.11.229.171/viswaweb/VLReports/SampleReports/CLO_C.PDF"));
                        }
                    } else {
                        if(from.equalsIgnoreCase("FO") ){

*/
/*
                            Intent intent=new Intent(context, PdfViewActivity.class);
                            intent.putExtra("pdf_name",analysisFoModel.getSerial());
                            intent.putExtra("module_type","FO");
                            context.startActivity(intent);*//*


//                            browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://74.208.185.23/VLIMSAPP/VL_FOReports_Download/" +username+"/"+ pwd+"/"+analysisFoModel.getSerial().toString() + ".pdf"));
                        }
                    }
                }
//                context.startActivity(browserIntent);

          */
/*      Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://173.11.229.171/viswaweb/LO_CLOPreview.aspx?strUserId=" + userId + "&&SampType=3&&strSerial=" + reportDataModel.getSerial().toString()));
                Log.v("xxx","http://173.11.229.171/viswaweb/LO_CLOPreview.aspx?strUserId=" + userId + "&&SampType=3&&strSerial=" + reportDataModel.getSerial().toString());
                context.startActivity(browserIntent);*//*

            }
        });
*/
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

    public interface ListenerInterface {
        void itemClicked(String sr);
    }

}

