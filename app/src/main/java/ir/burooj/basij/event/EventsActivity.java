package ir.burooj.basij.event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.burooj.basij.BAppCompatActivity;
import ir.burooj.basij.MainActivity;
import ir.burooj.basij.R;
import ir.burooj.basij.SplashScreen;
import ir.burooj.basij.apiClass.Event;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsActivity extends BAppCompatActivity {


    AVLoadingIndicatorView avLoadingIndicatorView;
    ProgressBar progressBar;
    TextView textView;
    RecyclerView recyclerView;
    EventsAdapter mAdapter;
    public List<Event> eventList2 = new ArrayList<>();
    private SwipeRefreshLayout pullToRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        avLoadingIndicatorView = findViewById(R.id.progressBar_avi_events);
        pullToRefresh = findViewById(R.id.pullToRefresh_events);
        textView = findViewById(R.id.textView4_events);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_events);
        progressBar = findViewById(R.id.progressBar_events);


        mAdapter = new EventsAdapter(eventList2, getApplicationContext(), this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        Toolbar toolbar=findViewById(R.id.toolbar_events);
        try {
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.notifyDataSetChanged();
        if (eventList2.size() > 0) {
            avLoadingIndicatorView.setVisibility(View.VISIBLE);
        }
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getEvents();
            }
        });
        getEvents();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) &&
                        newState == RecyclerView.SCROLL_STATE_IDLE && eventList2.size() > 0) {
                    progressBar.setVisibility(View.VISIBLE);
                    getEvents2();
                }
            }
        });
    }

    int i = 0;

    private void getEvents2() {
        if (i == 0) {
            progressBar.setVisibility(View.INVISIBLE);
        } else if (i >= 2) {
            Call<List<Event>> events = MainActivity.apiInterface.getEvents(token, userId, i);
            events.enqueue(new Callback<List<Event>>() {
                @Override
                public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                    List<Event> eventList3 = response.body();
                    try {
                        if (eventList3 != null && eventList3.get(0) != null) {
                            if (eventList3.get(0).getResponse() == null) {
                                progressBar.setVisibility(View.INVISIBLE);
                                eventList2.addAll(eventList3);
                                mAdapter.notifyDataSetChanged();
                                textView.setText("");
                                i++;

                            } else {
                                Toast.makeText(getApplicationContext(), eventList3.get(0).getResponse(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<Event>> call, Throwable t) {
                    pullToRefresh.setRefreshing(false);
                    avLoadingIndicatorView.setVisibility(View.INVISIBLE);
                    textView.setText("مشکلی در ارتباط پیش\u200cآمده.\nبرای بارگزاری مجدد صفحه را به پایین بکشید.");
                    mAdapter.notifyDataSetChanged();
                }
            });
        }


    }

    private void getEvents() {
        Call<List<Event>> events = MainActivity.apiInterface.getEvents(token, userId, 1);
        events.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                List<Event> eventList = response.body();
                if (eventList != null && eventList.get(0) != null) {
                    if (eventList.get(0).getResponse() == null) {
                        avLoadingIndicatorView.setVisibility(View.INVISIBLE);
                        eventList2.clear();
                        eventList2.addAll(eventList);
                        mAdapter.notifyDataSetChanged();
                        pullToRefresh.setRefreshing(false);
                        textView.setText("");
                        i = 2;

                    } else {
                        finish();
                        Toast.makeText(getApplicationContext(), "مشکلی پیش آمده", Toast.LENGTH_LONG).show();
                    }
                } else {
                    textView.setText("مشکلی پیش آمده");
                }

            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                pullToRefresh.setRefreshing(false);
                avLoadingIndicatorView.setVisibility(View.INVISIBLE);
                textView.setText("مشکلی در ارتباط پیش\u200cآمده.\nبرای بارگزاری مجدد صفحه را به پایین بکشید.");
                mAdapter.notifyDataSetChanged();

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
