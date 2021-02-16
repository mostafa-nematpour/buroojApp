package ir.burooj.basij.event;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import ir.burooj.basij.R;
import ir.burooj.basij.apiClass.Event;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventsShowSmallFragment extends BottomSheetDialogFragment {

    View view;
    private Event event;

    public EventsShowSmallFragment() {
        // Required empty public constructor
    }

    EventsShowSmallFragment(Event event) {
        this.event = event;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_events_show_small, container, false);
        TextView textViewTitle = view.findViewById(R.id.title_alert_e);
        TextView textViewDescription = view.findViewById(R.id.text_alert_event_d);
        TextView textViewStartDate = view.findViewById(R.id.s_date);
        TextView textViewEndDate = view.findViewById(R.id.e_date);
        Button button = view.findViewById(R.id.button_alert_events);
        textViewTitle.setText(event.getTitle());
        textViewDescription.setText(event.getDescription());
        textViewStartDate.setText("تاریخ شروع: " + event.getStart_date());
        textViewEndDate.setText("تاریخ پایان: " + event.getEnd_date());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EventMainActivity.class);
                startActivity(intent);
                dismiss();
            }
        });
        return view;
    }
}
