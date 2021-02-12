package ir.burooj.basij;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ir.burooj.basij.adapters.ListOfGroupAdapter;
import ir.burooj.basij.apiClass.EducationalCalendar;
import ir.burooj.basij.apiClass.GroupL;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ir.burooj.basij.MainActivity.modeD;

public class ListGroupActivity extends BAppCompatActivity {

    public static List<GroupL> items = new ArrayList<>();
    RecyclerView recyclerView;
    TextView textView, textView2;
    Picasso pica;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_group);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        textView2 = findViewById(R.id.textView16);

        recyclerView = findViewById(R.id.rec_g);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        OkHttp3Downloader okHttp3Downloader = new OkHttp3Downloader(client);
        pica = new Picasso.Builder(getApplicationContext()).downloader(okHttp3Downloader).build();

        getData();
    }

    private void getData() {

        textView = findViewById(R.id.textView13);
        textView.setOnClickListener(null);
        textView.setText("درحال دریافت...");
        textView.setVisibility(View.VISIBLE);


        Call<List<GroupL>> educationalCalendar = MainActivity.apiInterface.getWriters(userId, token);
        educationalCalendar.enqueue(new Callback<List<GroupL>>() {
            @Override
            public void onResponse(Call<List<GroupL>> call, Response<List<GroupL>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    textView2.setText(response.toString());
                    items = response.body();
                    Collections.reverse(items);

                    ListOfGroupAdapter adapter = new ListOfGroupAdapter(items, pica);
                    recyclerView.setAdapter(
                            adapter
                    );
                    adapter.notifyDataSetChanged();


                    textView.setVisibility(View.GONE);
                } else {
                    textView.setText("خطا");
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getData();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<GroupL>> call, Throwable t) {
                textView.setText("مشکلی در اتصال پیش آمده.");
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getData();
                    }
                });


            }
        });
    }
}