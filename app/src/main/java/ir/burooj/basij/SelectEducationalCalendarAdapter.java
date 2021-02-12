package ir.burooj.basij;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import ir.burooj.basij.apiClass.EducationalCalendar;

public class SelectEducationalCalendarAdapter extends RecyclerView.Adapter<SelectEducationalCalendarAdapter.MyViewHolder>  {
    private List<EducationalCalendar> educationalCalendars;
    Activity activity;

    public SelectEducationalCalendarAdapter(List<EducationalCalendar> educationalCalendars, Activity activity) {
        this.educationalCalendars = educationalCalendars;
        this.activity = activity;
    }

    @NonNull
    @Override
    public SelectEducationalCalendarAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_educational_calendar, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectEducationalCalendarAdapter.MyViewHolder holder, int position) {
        final EducationalCalendar educationalCalendar = educationalCalendars.get(position);
        holder.button.setText(educationalCalendar.getResponse());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ImageZoom.class);
                intent.putExtra("ImageForZoom", educationalCalendar.getLink());
                activity.startActivity(intent);
            }
        });
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(!educationalCalendar.getPdfLink().isEmpty()){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(educationalCalendar.getPdfLink()));
                    activity.startActivity(intent);
                }
                return false;
            }
        });
        if (educationalCalendar.isFlag()){
            holder.imageView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return educationalCalendars.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView button;
        ImageView imageView;
        LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            button =  view.findViewById(R.id.button_e_c);
            imageView =  view.findViewById(R.id.imageView5);
            linearLayout=view.findViewById(R.id.line136);
        }
    }
}
