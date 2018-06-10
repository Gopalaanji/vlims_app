package dev.info.basic.viswaLab.AnalysisReportsPage.Adapters;

import android.content.Context;
import android.content.Intent;
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
    AdditionalTestListner listenerInterface;

    public AnalysisAdditionalTestAdapter(Context context, List<PurifierEffyResponseModel> mReportDataModelList,AdditionalTestListner listenerInterface) {
        this.context = context;
        this.mReportDataModelList = mReportDataModelList;
      this.listenerInterface=listenerInterface;

    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.al_reports_additional_test_item, null);
        viewHolder viewHolder = new viewHolder(view);
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
            listenerInterface.itemClicked(mReportDataModelList.get(getAdapterPosition()).getSerial(),mReportDataModelList.get(getAdapterPosition()).getAdditionalTest());
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

   public interface  AdditionalTestListner{

       void itemClicked(String pdfFileName,String testName);

   }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        final PurifierEffyResponseModel analysisFoModel = mReportDataModelList.get(position);
        holder.tvShipName.setText(analysisFoModel.getShipName());
        if (analysisFoModel.getBunkerDate() != null) {
            holder.tvBunkerDate.setText(analysisFoModel.getAdditionalTest()+"("+analysisFoModel.getResult()+")\n"+ConvertJsonDate(analysisFoModel.getBunkerDate()) + "\n" + analysisFoModel.getBunkerPortName() + "\n" +  analysisFoModel.getMatrix());
        }
/*
        holder.tvReult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PdfViewActivity.class);
                intent.putExtra("pdf_name",analysisFoModel.getSerial());
                intent.putE xtra("module_type","ADD");
                intent.putExtra("test_type",analysisFoModel.getAdditionalTest());
                context.startActivity(intent);


//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://74.208.185.23/VLIMSAPP/VL_ADDLReports_Download/" + username+"/"+pwd+"/"+analysisFoModel.getSerial().toString()+"_"+analysisFoModel.getAdditionalTest() + ".pdf"));
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://74.208.185.23/VLIMSAPP/VL_ADDLReports_Download/" + username+"/"+pwd+"/"+analysisFoModel.getSerial().toString()+".pdf"));
//                context.startActivity(browserIntent);
            }
        });
*/
    }
}
