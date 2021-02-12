package ir.burooj.basij;

import android.app.Activity;
import android.graphics.Color;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ir.burooj.basij.more.LoanModel;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.MyViewHolder> {
    private AppCompatActivity activity;
    List<LoanModel> loanModels;


    public ViewPagerAdapter(AppCompatActivity activity, List<LoanModel> loanModels) {
        this.activity = activity;
        this.loanModels = loanModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vame, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LoanModel loanModel = loanModels.get(position);
        holder.title.setText(loanModel.getTitle());
        holder.text.setText(loanModel.getText());
        holder.text.setMovementMethod(LinkMovementMethod.getInstance());
        holder.downTitle.setText(loanModel.getDownTitle());
        try {
            holder.dowmLinear.setBackgroundColor(Color.parseColor(loanModel.getDownColor()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    View view0;

    @Override
    public int getItemCount() {
        if (loanModels == null) {
            return 0;
        }
        return loanModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView text;
        public TextView downTitle;
        public LinearLayout dowmLinear;

        MyViewHolder(View view) {
            super(view);
            view0 = view;
            title = view.findViewById(R.id.tvTitle);
            text = view.findViewById(R.id.text_item_vame);
            downTitle = view.findViewById(R.id.down_title);
            dowmLinear = view.findViewById(R.id.Liner_down_title);

        }
    }
}
