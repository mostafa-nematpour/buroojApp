package ir.burooj.basij;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.burooj.basij.apiClass.EducationalCalendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SelectEducationalCalendarActivity extends BAppCompatActivity {
    private List<EducationalCalendar> educationalCalendars = new ArrayList<>();
    RecyclerView recyclerView;
    SelectEducationalCalendarAdapter mAdapter;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_educational_calendar);
        recyclerView = findViewById(R.id.recycler_view_e_c);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    protected void onStart() {
        super.onStart();
        getC();

    }

    private void getC() {
        textView = findViewById(R.id.textView13);
        textView.setOnClickListener(null);
        textView.setText("درحال دریافت...");
        textView.setVisibility(View.VISIBLE);
        Call<List<EducationalCalendar>> educationalCalendar = apiInterface.getEducationalCalendars();
        educationalCalendar.enqueue(new Callback<List<EducationalCalendar>>() {
            @Override
            public void onResponse(Call<List<EducationalCalendar>> call, Response<List<EducationalCalendar>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    educationalCalendars = response.body();
                    //  Collections.reverse(educationalCalendars);

                    mAdapter = new SelectEducationalCalendarAdapter(educationalCalendars, SelectEducationalCalendarActivity.this);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    textView.setVisibility(View.GONE);
                } else {
                    textView.setText("خطا");
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getC();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<EducationalCalendar>> call, Throwable t) {
                textView.setText("مشکلی در اتصال پیش آمده.");
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getC();
                    }
                });
            }
        });

    }
}
