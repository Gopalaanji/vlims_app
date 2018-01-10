package rsr.info.basic.viswaLab.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import rsr.info.basic.viswaLab.R;
import rsr.info.basic.viswaLab.models.NewProjectDto;
import rsr.info.basic.viswaLab.views.TextViewPlus;

/**
 * Created by RSR on 07-04-2017.
 */

public class ProjectsAdapter extends BaseAdapter {
    Context context;
    List<NewProjectDto> projectsDtoList;
    NewProjectDto projectsDto;

    public ProjectsAdapter(Context context, List<NewProjectDto> projectsDtoList) {
        this.context = context;
        this.projectsDtoList = projectsDtoList;
    }

    @Override
    public int getCount() {
        return projectsDtoList.size();
    }

    @Override
    public Object getItem(int position) {
        return projectsDtoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.thumbnail_row, parent, false);
        }
        TextViewPlus project_name = (TextViewPlus) row.findViewById(R.id.project_name);
        TextViewPlus total_plots = (TextViewPlus) row.findViewById(R.id.total_plots);
        TextViewPlus plots_allowed = (TextViewPlus) row.findViewById(R.id.plots_allowed);
        TextViewPlus plots_available = (TextViewPlus) row.findViewById(R.id.plots_available);
        ImageView image = (ImageView) row.findViewById(R.id.image);
        projectsDto = projectsDtoList.get(position);
        project_name.setText(projectsDto.getProject_name().toString());
        total_plots.setText(projectsDto.getTotal() + "");
        plots_allowed.setText(projectsDto.getAlloted() + "");
        plots_available.setText(projectsDto.getAvailable() + "");
        return row;
    }
}
