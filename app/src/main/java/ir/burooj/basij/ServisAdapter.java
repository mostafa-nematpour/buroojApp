package ir.burooj.basij;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.burooj.basij.apiClass.Servis2;

public class ServisAdapter extends RecyclerView.Adapter<ServisAdapter.MyViewHolder> {

    private List<Servis2> servis2s;
    private int width, height;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView first, second;

        public MyViewHolder(View view) {
            super(view);
            first = (TextView) view.findViewById(R.id.textView10);
            second = (TextView) view.findViewById(R.id.textView11);

            ViewGroup.LayoutParams params = first.getLayoutParams();

            params.width = width / 2;
            first.setLayoutParams(params);
            ViewGroup.LayoutParams params1 = second.getLayoutParams();

            params1.width = width / 2;
            second.setLayoutParams(params1);
        }
    }


    public ServisAdapter(List<Servis2> servis2s, int width, int height) {
        this.servis2s = servis2s;
        this.width = width;
        this.height = height;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_servis, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Servis2 servis2 = servis2s.get(position);
        if (position == 0) {
            holder.first.setTextSize(12);
            holder.second.setTextSize(12);
        }
        if (servis2.getFirst() != null && !servis2.getFirst().equals("null"))
            holder.first.setText(servis2.getFirst());
        else
            holder.first.setText("");

        if (servis2.getSecond() != null && !servis2.getSecond().equals("null"))
            holder.second.setText(servis2.getSecond());
        else
            holder.second.setText("");


    }

    @Override
    public int getItemCount() {
        if (servis2s == null) {
            return 0;
        }
        return servis2s.size();
    }
}
