package ir.burooj.basij.event;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import ir.burooj.basij.BAppCompatActivity;
import ir.burooj.basij.CompleteProfileActivity;
import ir.burooj.basij.ImageZoom;
import ir.burooj.basij.MainActivity;
import ir.burooj.basij.R;
import ir.burooj.basij.SplashScreen;
import ir.burooj.basij.adapters.MessageAdapter;
import ir.burooj.basij.apiClass.Event;
import ir.burooj.basij.apiClass.GetUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventMainActivity extends BAppCompatActivity {

    private Event event;
    private TextView textViewGName;
    private TextView textViewSDate;
    private TextView textViewEDate;
    private TextView textViewDec;
    private TextView textViewStartTime;
    private TextView textViewEndTime;
    private Toolbar toolbar;
    private Button button;
    private ImageView imageView;

    TextView textView3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_main);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        if (EventsAdapter.event != null) {
            event = EventsAdapter.event;
        } else {
            finish();
        }
        TextView textView = findViewById(R.id.text_234);
        textViewGName = findViewById(R.id.text_event_main_g_name);
        toolbar = findViewById(R.id.toolbar_events_main);
        imageView = findViewById(R.id.image_view_event_main);
        textViewSDate = findViewById(R.id.text_main_event_s);
        textViewEDate = findViewById(R.id.text_main_event_e);
        textViewDec = findViewById(R.id.text_main_dec_event);
        textViewEndTime = findViewById(R.id.text_main_end_time);
        textViewStartTime = findViewById(R.id.text_main_start_time);
        button = findViewById(R.id.btn_main_event);
        textView3 = findViewById(R.id.text22);
        textView3.setSelected(true);

        Typeface tf = Typeface.createFromAsset(getAssets(), "vazir.ttf");
        textViewDec.setTypeface(tf);
        textViewDec.setTextIsSelectable(true);
        textViewDec.setTypeface(tf);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            View decor = getWindow().getDecorView();
            fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);
            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);
        }
        init();

        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onStart() {
        super.onStart();
        ScaleAnimation scaleAnimation =
                new ScaleAnimation(1.3f, 1, 1.3f, 1,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(400);
        imageView.startAnimation(scaleAnimation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        button.setText("...");
        getEvent();

    }

    private void getEvent() {
        button.setOnClickListener(null);
        button.setText("...");
        final Call<Event> event1 = apiInterface.
                getEvent(userId, token, this.event.getId());
        event1.enqueue(new Callback<Event>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<Event> call,
                                   @NotNull Response<Event> response) {
                if (response.body() != null && response.body().getResponse() != null &&
                        response.body().getResponse().equals("1")) {

                    EventMainActivity.this.event = response.body();

                    //Toast.makeText(getApplicationContext(), ""+ EventMainActivity.this.event.getResponse(),Toast.LENGTH_LONG).show();
                    init();
                    if (EventMainActivity.this.event.getRegistered().equals("1")) {
                        button.setText("دریافت بلیت");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), ImageZoom.class);
                                intent.putExtra("qrToken", EventMainActivity.this.event.getTicketToken());
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    ImageView imageView1 = findViewById(R.id.l1221);
                                    ActivityOptionsCompat options =
                                            ActivityOptionsCompat.makeSceneTransitionAnimation(
                                            EventMainActivity.this,
                                            imageView1,
                                            Objects.requireNonNull(
                                                    ViewCompat.getTransitionName(imageView1)
                                            )
                                    );
                                    startActivity(intent, options.toBundle());
                                } else {
                                    startActivity(intent);
                                }
                            }
                        });
                        if (!Objects.requireNonNull(event.getMessages()).isEmpty()) {

                            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.messag_list);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            MessageAdapter messageAdapter = new MessageAdapter(EventMainActivity.this.event.getMessages());
                            recyclerView.setAdapter(messageAdapter);
                            messageAdapter.notifyDataSetChanged();
                        }

                        //   Toast.makeText(getApplicationContext(),events.getTicketToken(),Toast.LENGTH_LONG).show();
                    } else if (EventMainActivity.this.event.getRegistered().equals("0")) {
                        if (event.getPrice().equals("0")) {
                            buttonOnClick0("ثبت\u200cنام");
                        } else {
                            buttonOnClick0(event.getPrice() + " تومان ");
                        }
                    }

                } else {
                    button.setText("مشکلی در صحت اطلاعات پیش آمده.");
                    button.setOnClickListener(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Event> call,
                                  @NotNull Throwable t) {
                button.setText("مشکلی در اتصال پیش آمده");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getEvent();
                    }
                });
            }
        });

    }

    private void buttonOnClick0(String s) {
        button.setText(s);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOnClick();

            }

        });
    }

    private void buttonOnClick() {
        button.setOnClickListener(null);
        button.setText("صبر کنید ...");

        Call<GetUser> getUserCall = MainActivity.apiInterface.getUser(userId, token);
        getUserCall.enqueue(new Callback<GetUser>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<GetUser> call, @NotNull Response<GetUser> response) {
                GetUser user = response.body();
                if (user != null && user.getResponse() == 1) {
                    if (user.isComplete()) {
                        //اکانت کامله
                        Call<ir.burooj.basij.apiClass.Response> call1 = MainActivity.apiInterface.
                                registerEvent(token, userId, event.getId());
                        call1.enqueue(new Callback<ir.burooj.basij.apiClass.Response>() {
                            @Override
                            public void onResponse(@NotNull Call<ir.burooj.basij.apiClass.Response> call,
                                                   @NotNull Response<ir.burooj.basij.apiClass.Response> response) {
                                if (response.body() != null) {
                                    ir.burooj.basij.apiClass.Response response1 = response.body();
                                    //  Toast.makeText(getApplicationContext(), "" + response1.getResponse(), Toast.LENGTH_LONG).show();
                                    EventResult(event, response1);
                                } else {
                                    Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_LONG).show();

                                }
                                getEvent();
                            }

                            @Override
                            public void onFailure(@NotNull Call<ir.burooj.basij.apiClass.Response> call,
                                                  @NotNull Throwable t) {
                                Toast.makeText(getApplicationContext(), "onFailure", Toast.LENGTH_LONG).show();

                                getEvent();
                            }
                        });
                    } else {
                        // باید اکانتش رو کامل کنه
                        alertBuilder();
                        getEvent();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "مشکلی پیش آمده", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(@NotNull Call<GetUser> call, @NotNull Throwable t) {
                buttonOnClick0("مشکلی در اتصال پیش آمده");
            }

        });
    }

    private void EventResult(Event event, ir.burooj.basij.apiClass.Response response1) {
        EventResultFragment eventResultFragment = new EventResultFragment(event, response1);
        eventResultFragment.show(getSupportFragmentManager(), eventResultFragment.getTag());
    }

    private void alertBuilder() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.DialogTheme);
        alertBuilder.setMessage("برای ادامه باید اکانت خود را تکمیل نمایید.");
        alertBuilder.setTitle("خطای تکمیل\u200cحساب");
        alertBuilder.setPositiveButton("موافقم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getBaseContext(), CompleteProfileActivity.class);
                startActivity(intent);
                buttonOnClick0("ثبت\u200cنام");
            }
        });
        alertBuilder.setNegativeButton("لغو", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                buttonOnClick0("ثبت\u200cنام");

            }
        });
        alertBuilder.create();
        alertBuilder.show();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        try {
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setTitle(event.getTitle());
            textView3.setText(event.getTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        textViewGName.setText("برگزارکننده:  " + event.getGroupName());
        textViewDec.setText(event.getDescription());
        if (event.getStart_date().equals(event.getEnd_date())) {
            textViewEDate.setVisibility(View.GONE);
            textViewSDate.setText("تاریخ برگزاری رویداد: " + event.getStart_date());
        } else {
            textViewSDate.setText("تاریخ شروع رویداد: " + event.getStart_date());
            textViewEDate.setText("تاریخ پایان رویداد: " + event.getEnd_date());
        }
        if (event.getEnd_time().equals(event.getStart_time())) {
            textViewEndTime.setVisibility(View.GONE);
            textViewStartTime.setText("زمان برگزاری رویداد: " + event.getStart_time());
        } else {
            textViewEndTime.setText("ساعت خاتمه رویداد: " + event.getEnd_time());
            textViewStartTime.setText("ساعت شروع رویداد: " + event.getStart_time());
        }
        MainActivity.pica.load(event.getImageUrl())
                .placeholder(R.drawable.event_default_loading)
                .error(R.drawable.errorpic)
                .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventMainActivity.this, ImageZoom.class);
                intent.putExtra("ImageForZoom", event.getImageUrl());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            EventMainActivity.this, imageView,
                            Objects.requireNonNull(ViewCompat.getTransitionName(imageView)));
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// app icon in action bar clicked; goto parent activity.
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
