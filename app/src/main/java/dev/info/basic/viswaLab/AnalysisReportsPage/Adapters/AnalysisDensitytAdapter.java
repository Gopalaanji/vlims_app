package dev.info.basic.viswaLab.AnalysisReportsPage.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dev.info.basic.viswaLab.Adapters.CalculationsListAdapter;
import dev.info.basic.viswaLab.AnalysisReportsPage.models.AnalysisReportDensityDataModel;
import dev.info.basic.viswaLab.R;

/**
 * Created by Giri Thangellapally on 23-04-2018.
 */
public class AnalysisDensitytAdapter extends RecyclerView.Adapter<AnalysisDensitytAdapter.viewHolder> {
    private Context context;
    CalculationsListAdapter.clickListener mClickListener;
    List<AnalysisReportDensityDataModel> mReportDataModelList = new ArrayList<>();
    String userId;

    public AnalysisDensitytAdapter(Context context, List<AnalysisReportDensityDataModel> mReportDataModelList, String userId) {
        this.context = context;
        this.mReportDataModelList = mReportDataModelList;
        this.userId = userId;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_anlysis_density_item, null);
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
        private TextView tvShipName, tvBunkerDetails, tvSerialNo, tvFuelGrade, tvbdnDensity, tvTestedDensity, tvDiffTonnes, tvQtyRecieved;

        public viewHolder(View itemView) {
            super(itemView);
            tvShipName = (TextView) itemView.findViewById(R.id.tvShipName);
            tvSerialNo = (TextView) itemView.findViewById(R.id.tvSerialNo);
            tvFuelGrade = (TextView) itemView.findViewById(R.id.tvFuelGrade);
            tvBunkerDetails = (TextView) itemView.findViewById(R.id.tvBunkerDetails);
            tvbdnDensity = (TextView) itemView.findViewById(R.id.tvbdnDensity);
            tvTestedDensity = (TextView) itemView.findViewById(R.id.tvTestedDensity);
            tvDiffTonnes = (TextView) itemView.findViewById(R.id.tvDiffTonnes);
            tvQtyRecieved = (TextView) itemView.findViewById(R.id.tvQtyRecieved);
            itemView.setOnClickListener(this);
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
    public void onBindViewHolder(viewHolder holder, int position) {
        final AnalysisReportDensityDataModel analysisFoModel = mReportDataModelList.get(position);





        holder.tvbdnDensity.setText("BDN Density: "+analysisFoModel.getRecDen());
        holder.tvTestedDensity.setText("Tested Density: "+analysisFoModel.getTestedDen());
        holder.tvQtyRecieved.setText("Recieved Quantity: " +analysisFoModel.getQtyReceived());

        holder.tvShipName.setText(analysisFoModel.getShipName());
        holder.tvSerialNo.setText("SerialNo: "+analysisFoModel.getSerial());
        holder.tvFuelGrade.setText("Fuel Grade: " +analysisFoModel.getGrade() + "-" + analysisFoModel.getGradeMatrix());
        try {
            if (analysisFoModel.getDifferenceTonnes().contains("-")) {

//                holder.tvDiffTonnes.setTextColor(context.getResources().getColor(R.color.red_btn_bg_color));
                String finalString="Diff MT: "+analysisFoModel.getDifferenceTonnes();
                SpannableStringBuilder ssBuilder = new SpannableStringBuilder(finalString);
                ssBuilder.setSpan(new StyleSpan(Typeface.BOLD), String.valueOf("Diff MT: ").length(), finalString.indexOf(analysisFoModel.getDifferenceTonnes())+analysisFoModel.getDifferenceTonnes().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssBuilder.setSpan(new ForegroundColorSpan(Color.RED),
                        String.valueOf("Diff MT: ").length(),
                        finalString.indexOf(analysisFoModel.getDifferenceTonnes())+ analysisFoModel.getDifferenceTonnes().length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.tvDiffTonnes.setText(ssBuilder);

            } else {
//                holder.tvDiffTonnes.setTextColor(context.getResources().getColor(R.color.meterialgreen));
//                holder.tvDiffTonnes.setTextColor(context.getResources().getColor(R.color.red_btn_bg_color));
                String finalString="Diff MT: "+analysisFoModel.getDifferenceTonnes();
                SpannableStringBuilder ssBuilder = new SpannableStringBuilder(finalString);
                ssBuilder.setSpan(new StyleSpan(Typeface.BOLD), String.valueOf("Diff MT: ").length(), finalString.indexOf(analysisFoModel.getDifferenceTonnes())+analysisFoModel.getDifferenceTonnes().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssBuilder.setSpan(new ForegroundColorSpan(Color.GREEN),
                        String.valueOf("Diff MT: ").length(),
                        finalString.indexOf(analysisFoModel.getDifferenceTonnes())+ analysisFoModel.getDifferenceTonnes().length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.tvDiffTonnes.setText(ssBuilder);

            }
        } catch (Exception e) {
//            holder.tvDiffTonnes.setTextColor(context.getResources().getColor(R.color.red_btn_bg_color));
            String finalString="Diff MT: "+analysisFoModel.getDifferenceTonnes();
            SpannableStringBuilder ssBuilder = new SpannableStringBuilder(finalString);
            ssBuilder.setSpan(new StyleSpan(Typeface.BOLD), String.valueOf("Diff MT: ").length(), finalString.indexOf(analysisFoModel.getDifferenceTonnes())+analysisFoModel.getDifferenceTonnes().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssBuilder.setSpan(new ForegroundColorSpan(Color.RED),
                    String.valueOf("Diff MT: ").length(),
                    finalString.indexOf(analysisFoModel.getDifferenceTonnes())+ analysisFoModel.getDifferenceTonnes().length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tvDiffTonnes.setText(ssBuilder);

//            holder.tvDiffTonnes.setTextColor(context.getResources().getColor(R.color.red_btn_bg_color));
        }
//        holder.tvDiffTonnes.setText("Diff MT: " +analysisFoModel.getDifferenceTonnes());
        try {
            holder.tvBunkerDetails.setText("Bunker Details: " +analysisFoModel.getBunkerPort() + "," + ConvertJsonDate(analysisFoModel.getBunkerDate()));
        } catch (Exception e) {

        }


    }

    private SpannableStringBuilder getColorBoldString(String s, String data) {
        String finalString=s+data;
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(finalString);
        ssBuilder.setSpan(new StyleSpan(Typeface.BOLD), String.valueOf(s).length(), finalString.indexOf(data)+data.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssBuilder.setSpan(new ForegroundColorSpan(Color.BLACK),
                String.valueOf(s).length(),
                finalString.indexOf(data)+ data.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssBuilder;
    }
}
