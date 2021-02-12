package ir.burooj.basij.event;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.burooj.basij.MainActivity;
import ir.burooj.basij.R;
import ir.burooj.basij.apiClass.Event;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

    private final List<Event> eventList;
    public static Event event;


    public static float convertDpToPixel(float dp, Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView imageView;
        CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.text_item_event);
            imageView = view.findViewById(R.id.imageView_item_events);
            cardView = view.findViewById(R.id.card_view_item_events);
            ViewGroup.LayoutParams params1 = imageView.getLayoutParams();
//            params1.height = (w - ((int) convertDpToPixel(30, context))) / 2;
           // imageView.setLayoutParams(params1);
            Typeface tf = Typeface.createFromAsset(view.getContext().getAssets(), "light.ttf");
            title.setTypeface(tf);
        }
    }


    Context context;
    EventsActivity activity;

    public EventsAdapter(List<Event> eventList, Context context, EventsActivity activity) {
        this.eventList = eventList;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Event event = eventList.get(position);
        holder.title.setText(event.getTitle());
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                EventsAdapter.event = event;
                EventsShowSmallFragment bottomFragment = new EventsShowSmallFragment(event);
                bottomFragment.show(activity.getSupportFragmentManager(), bottomFragment.getTag());
                return true;
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventsAdapter.event = event;
                Intent intent = new Intent(activity, EventMainActivity.class);
                activity.startActivity(intent);
            }
        });
        if (MainActivity.modeD.equals("black")) {
            MainActivity.pica.load(event.getImageUrl())
                    .placeholder(R.drawable.custom_back_8)
                    .error(R.drawable.errorpic)
                    .into(holder.imageView);
        }else if(MainActivity.modeD.equals("white")){
            MainActivity.pica.load(event.getImageUrl())
                    .placeholder(R.drawable.event_default_loading)
                    .error(R.drawable.errorpic)
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        if (eventList == null) {
            return 0;
        }
        return eventList.size();
    }
}
