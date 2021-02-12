package ir.burooj.basij;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import ir.burooj.basij.adapters.ListOfGroupAdapter;
import ir.burooj.basij.adapters.MembersOfGroupAdapter;
import ir.burooj.basij.apiClass.GroupL;
import ir.burooj.basij.groups.ListShowActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ir.burooj.basij.MainActivity.pica;

public class GroupShowActivity extends BAppCompatActivity {
    CardView cardViewImage;
    TextView textViewName, textViewDec;
    TextView buttonListShow;
    ImageView imageView;
    RecyclerView recyclerView;
    CardView cardView;
    String id;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_show);
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            id = extras.getString("id", "-2");
            name = extras.getString("name", getString(R.string.app_name));
        } else {
            finish();
        }
        cardViewImage = findViewById(R.id.cardView2);
        textViewName = findViewById(R.id.textView17);
        textViewDec = findViewById(R.id.textView18);
        buttonListShow = findViewById(R.id.button5);
        imageView = findViewById(R.id.imageView6);
        cardView=findViewById(R.id.cardView3);
        textViewName.setText(name);

        recyclerView = findViewById(R.id.recycler);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        setData();
        textViewName.setTypeface(Typeface.createFromAsset(getAssets(), "lalezar.ttf"));
        textViewDec.setTypeface(Typeface.createFromAsset(getAssets(), "vazir.ttf"));
        buttonListShow.setTypeface(Typeface.createFromAsset(getAssets(), "font.ttf"));

    }

    private void setData() {
        textViewDec.setText("  ...  ");
        buttonListShow.setText("در حال دریافت ...");
        cardView.setOnClickListener(null);
        Call<GroupL> educationalCalendar = apiInterface.getWriter(userId, token, id);
        educationalCalendar.enqueue(new Callback<GroupL>() {
            @Override
            public void onResponse(Call<GroupL> call, Response<GroupL> response) {
                if (response.body() != null) {
                    final GroupL group = response.body();

                    pica.load("https://burooj.ir/" + group.getProfile_image_path())
                            .placeholder(R.drawable.circle_for_image_view_2)
                            .error(R.drawable.errorpic)
                            .into(imageView);
                    textViewDec.setText(group.getDescription());
                    buttonListShow.setText("پست\u200cهای گروه");

                    MembersOfGroupAdapter adapter = new MembersOfGroupAdapter(group.getMembers());
                    recyclerView.setAdapter(
                            adapter
                    );
                    adapter.notifyDataSetChanged();

                    cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), ListShowActivity.class);
                            intent.putExtra("id", group.getId());
                            intent.putExtra("name", group.getGroupName());
                            startActivity(intent);
                        }
                    });
                    if(group.getMembers()!=null&&group.getMembers().size()>0){
                        TextView textView=findViewById(R.id.textView20);
                        textView.setText("اعضا: ");
                    }
                } else {
                    textViewDec.setText("فرمت اشتباه!");
                    cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setData();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<GroupL> call, Throwable t) {
                textViewDec.setText(t.getMessage());
                buttonListShow.setText("تلاش مجدد");
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setData();
                    }
                });


            }
        });
    }
}