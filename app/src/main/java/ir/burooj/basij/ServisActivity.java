package ir.burooj.basij;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.easing.linear.Linear;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.burooj.basij.apiClass.Servis1;
import ir.burooj.basij.apiClass.Servis2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServisActivity extends BAppCompatActivity {

    TextView textView;
    public static ApiInterface apiInterface;
    List<Servis2> servis2s = new ArrayList<>();
    List<Servis2> serviss = new ArrayList<>();
    Servis1 servis1;
    private ServisAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servis);
        textView = findViewById(R.id.ser_text);
        apiInterface = Api.getAPI().create(ApiInterface.class);
        try {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        try {
            Objects.requireNonNull(getSupportActionBar()).setTitle("زمان سرویس");
        } catch (Exception e) {
            e.printStackTrace();
        }


        if(modeD.equals("black")){
            Button button=findViewById(R.id.b_servis);
            button.setTextColor(getResources().getColor(R.color.colorPrimaryDark1));
        }
  /*
        اندازه گیری صفحه
         */
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        /*
        اندازه گیری صفحه
         */
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new ServisAdapter(serviss, width, height);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int i = extras.getInt("i");
            if (Tools.getSharedPreferenceServis(getApplicationContext(), i) != null) {
                try {
                    String s = Tools.getSharedPreferenceServis(getApplicationContext(), i);
                    List<Servis2> servis2s = Tools.stringToServisList(s, "mazmazes");
                    if (servis2s != null && servis2s.size() > 0) {
                        serviss.clear();
                        serviss.addAll(servis2s);
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (i == 1) {
                /*

                 */

                try {
                    Objects.requireNonNull(getSupportActionBar()).setTitle("شهیدبهشتی - الغدیر");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Call<Servis1> servis = apiInterface.alghadirServis();
                servis.enqueue(new Callback<Servis1>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<Servis1> call, Response<Servis1> response) {
                        servis1 = response.body();

                        if (servis1 != null) {
                            servis1 = response.body();
                            textView.setText("تاریخ بروزرسانی: " + servis1.getLastUpdate());

                        } else {
                            textView.setText("null");
                        }
                    }

                    @Override
                    public void onFailure(Call<Servis1> call, Throwable t) {
                        textView.setText("مشکلی در ارتباط پیش آمده");
                    }
                });


                Call<List<Servis2>> servis2 = apiInterface.alghadirServis2();
                servis2.enqueue(new Callback<List<Servis2>>() {
                    @Override
                    public void onResponse(Call<List<Servis2>> call, Response<List<Servis2>> response) {
                        if (response.body() != null) {
                            servis2s = response.body();
                            serviss.clear();
                            serviss.addAll(servis2s);
                            mAdapter.notifyDataSetChanged();
                            Tools.sharedPreferenceServis(getApplicationContext(), 1,
                                    Tools.servisLiostToString(servis2s, "mazmazes"));
                        } else {
                            textView.setText("مشکلی پیش آمده");
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Servis2>> call, Throwable t) {
                    }
                });


            } else if (i == 2) {


                /*

                سرخنکلا


                 */
                try {
                    getSupportActionBar().setTitle("شهدبهشتی - سرخنکلا");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                final Call<Servis1> servis = apiInterface.sorkhankolateServis();
                servis.enqueue(new Callback<Servis1>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<Servis1> call, Response<Servis1> response) {
                        servis1 = response.body();

                        if (servis1 != null) {
                            servis1 = response.body();
                            textView.setText("تاریخ بروزرسانی: " + servis1.getLastUpdate());
                        } else {
                            textView.setText("null");
                        }
                    }

                    @Override
                    public void onFailure(Call<Servis1> call, Throwable t) {
                        textView.setText("مشکلی در ارتباط پیش آمده");
                    }
                });


                Call<List<Servis2>> servis2 = apiInterface.sorkhankolateServis2();
                servis2.enqueue(new Callback<List<Servis2>>() {
                    @Override
                    public void onResponse(Call<List<Servis2>> call, Response<List<Servis2>> response) {
                        if (response.body() != null) {
                            servis2s = response.body();
                            serviss.clear();
                            serviss.addAll(servis2s);
                            Tools.sharedPreferenceServis(getApplicationContext(), 2,
                                    Tools.servisLiostToString(servis2s, "mazmazes"));
                            mAdapter.notifyDataSetChanged();
                        } else {
                            textView.setText("مشکلی پیش آمده");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Servis2>> call, Throwable t) {
                    }
                });
            }
        } else {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }

    public void showServis(View view) {
        if (servis1 != null) {
            Intent intent = new Intent(getApplicationContext(), ImageZoom.class);
            intent.putExtra("ImageForZoom", servis1.getImageUrl());
            startActivity(intent);
        } else {
            Snackbar.make(view, "در حال حاضر مقدور نیست.", Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        serviss.clear();
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
