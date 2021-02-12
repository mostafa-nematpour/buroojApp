package ir.burooj.basij;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import ir.burooj.basij.event.EventsActivity;
import ir.burooj.basij.video.VideoActivity;


public class ToolsFragment extends Fragment implements View.OnClickListener {


    View view;
    private TextView textViewZojVaFard;
    private CardView cardSrevice, cardMarasem, cardCalendar, cardTaghvim, cardVame, cardTaze, cardGroups;
    private String weekState = "", weekString = "";
    private ImageView imageViewMarasem, imageViewTaghvim, imageViewServise, imageViewTaghvimAmozesh, imageViewVame, uni;


    public ToolsFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tools, container, false);
        cardSrevice = view.findViewById(R.id.ba1);
        cardMarasem = view.findViewById(R.id.ba2);
        textViewZojVaFard = view.findViewById(R.id.textView9);
        cardCalendar = view.findViewById(R.id.ba5);
        cardTaghvim = view.findViewById(R.id.ba4);
        cardVame = view.findViewById(R.id.ba3);
        cardTaze = view.findViewById(R.id.ba8);
        cardGroups = view.findViewById(R.id.ba7);
        imageViewMarasem = view.findViewById(R.id.image1);
        imageViewTaghvim = view.findViewById(R.id.image2);
        imageViewServise = view.findViewById(R.id.image3);
        imageViewTaghvimAmozesh = view.findViewById(R.id.image4);
        imageViewVame = view.findViewById(R.id.image5);
        uni = view.findViewById(R.id.imageView7);
        ImageView groups = view.findViewById(R.id.image7);
        if (MainActivity.modeD.equals("black")) {
            imageViewMarasem.setImageDrawable(getResources().getDrawable(R.drawable.ic_calendar_dark));
            imageViewTaghvim.setImageDrawable(getResources().getDrawable(R.drawable.ic_calendar_0_dark));
            imageViewServise.setImageDrawable(getResources().getDrawable(R.drawable.ic_minibus_dark));
            imageViewTaghvimAmozesh.setImageDrawable(getResources().getDrawable(R.drawable.ic_checklist_dark));
            imageViewVame.setImageDrawable(getResources().getDrawable(R.drawable.ic_contract_dark));
            uni.setImageDrawable(getResources().getDrawable(R.drawable.ic_uni_screen_d));
            groups.setImageDrawable(getResources().getDrawable(R.drawable.ic_network_dark));
        }

        textViewZojVaFard.setTypeface(Typeface.createFromAsset(Objects.requireNonNull(getActivity()).getAssets(), "lalezar.ttf"));

        weekState = MainActivity.weekState;
        weekString = MainActivity.weekString;


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        cardSrevice.setOnClickListener(this);
        cardMarasem.setOnClickListener(this);
        cardCalendar.setOnClickListener(this);
        cardTaghvim.setOnClickListener(this);
        cardVame.setOnClickListener(this);
        cardTaze.setOnClickListener(this);
        cardGroups.setOnClickListener(this);
//
        final AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0.1f);
//        alphaAnimation.setDuration(800);
//        alphaAnimation.setRepeatCount(2);
//        alphaAnimation.setFillAfter(false);
//        alphaAnimation.setRepeatMode(Animation.REVERSE);

        switch (weekState) {
            case "1":
                textViewZojVaFard.setText("هفته جاری فرد می\u200cباشد.");
                break;
            case "2":
                textViewZojVaFard.setText("هفته جاری زوج می\u200cباشد.");

                break;
            case "3":
                textViewZojVaFard.setText(weekString);
                textViewZojVaFard.startAnimation(alphaAnimation);
                break;
            default:
                textViewZojVaFard.setText("وقت بخیر :)");
                break;
        }
    }


    @Override
    public void onClick(View v) {
        //Toast.makeText(getContext(), "" + v.getId(), Toast.LENGTH_LONG).show();
        switch (v.getId()) {
            case R.id.ba1:
                Intent intent = new Intent(getActivity(), SelectServisActivity.class);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), imageViewServise, ViewCompat.getTransitionName(imageViewServise));
                startActivity(intent, compat.toBundle());
                break;
            case R.id.ba2:
                intent(EventsActivity.class);
                break;
            case R.id.ba5:
                //  intent(CalendarActivity.class);
                break;
            case R.id.ba3:
                Intent intent3 = new Intent(getActivity(), VameActivity.class);
                startActivity(intent3);
                break;
            case R.id.ba4:
                Intent intent4 = new Intent(getActivity(), SelectEducationalCalendarActivity.class);
                startActivity(intent4);
                break;
            case R.id.ba8:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://gu.ac.ir/Context/21891"));
                startActivity(i);
                break;
            case R.id.ba7:
                intent(ListGroupActivity.class);
                break;

        }
    }

    private void intent(Class<?> aClass) {
        final Class<?> aClass1 = aClass;
        new Handler().postDelayed(new Runnable() {
            public void run() {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getActivity(), aClass1);
                        startActivity(intent);
                    }
                });

            }
        }, 100);


    }
}
