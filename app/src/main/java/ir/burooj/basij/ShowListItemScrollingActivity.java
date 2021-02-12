package ir.burooj.basij;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.rhexgomez.typer.roboto.TyperRoboto;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.transition.Fade;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.Objects;

import ir.burooj.basij.apiClass.Like;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ir.burooj.basij.MainActivity.pica;

public class ShowListItemScrollingActivity extends BAppCompatActivity {

    public static ApiInterface apiInterface;

    boolean flag = false;
    FloatingActionButton fab;
    TextView textView,title;
    String[] SData;
    ImageView imageView;


    @SuppressLint({"ClickableViewAccessibility", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_item_scrolling);
        apiInterface = Api.getAPI().create(ApiInterface.class);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        init();
        fab = (FloatingActionButton) findViewById(R.id.fab);

        ///
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            SData = extras.getStringArray("SData");
        } else {
            Toast.makeText(getApplicationContext(), "مشکلی در بار گزاری پیش آمده", Toast.LENGTH_LONG).show();
            finish();
        }
        ///
        try {
            title.setText(SData[1]);

            title.setSelected(true);

            textView.setText(SData[2]);
            if (SData[7].equals("1")) {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_f));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                like(fab);
            }
        });


//        collapsingToolbar.setCollapsedTitleTypeface(TyperRoboto.ROBOTO_REGULAR());
//        collapsingToolbar.setExpandedTitleTypeface(TyperRoboto.ROBOTO_REGULAR());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Fade fade = new Fade();
            View decor = getWindow().getDecorView();
            fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);

            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);

        }
        Animation in = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
        in.setDuration(400);
        in.setStartOffset(400);
        fab.startAnimation(in);
    }


    // عملیات لایک
    private void like(final FloatingActionButton floatingActionButton) {
        Call<Like> like = ShowListItemScrollingActivity.apiInterface.like(userId, SData[6], token);
        like.enqueue(new Callback<Like>() {
            @Override
            public void onResponse(Call<Like> call, Response<Like> response) {
                Like like1 = response.body();
                if (like1 != null) {
                    if (like1.getResponse().equals("1")) {
                        if (like1.getAction().equals("like")) {


                            // لایک شده
//                            Snackbar.make(textView, "لایک شد.", Snackbar.LENGTH_LONG)
//                                    .setAction("Action", null).show();
                            floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_f));
                        } else if (like1.getAction().equals("unlike")) {
                            //از لایک در اومد
//                            Snackbar.make(textView, "از لایک در اومد.", Snackbar.LENGTH_LONG)
//                                    .setAction("Action", null).show();
                            floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_ff));

                        } else {
                            Snackbar.make(textView, "مشکلی پیش آمده  (like.getAction() != (unlike) or (like", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    } else {
                        Snackbar.make(textView, "مشکلی پیش آمده (like1.getResponse() != (1", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                } else {
                    Snackbar.make(textView, "مشکلی پیش آمده like = null", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

            @Override
            public void onFailure(Call<Like> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    protected void onResume() {
        try {
            //  new DownloadImageTask(collapsingToolbarLayout).execute(SData[0]);

            //added
            OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();
            OkHttp3Downloader okHttp3Downloader = new OkHttp3Downloader(client);

            //added - hamun method ha ruye object p
            pica.load(SData[0]).placeholder(R.drawable.defaultpic).error(R.drawable.errorpic).into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ImageZoom.class);
                    intent.putExtra("ImageForZoom", SData[0]);
                    startActivity(intent);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onResume();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void init() {
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            setSupportActionBar(toolbar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textView = findViewById(R.id.main_text);
        imageView = findViewById(R.id.image_item_scrolling);


        Typeface tf = Typeface.createFromAsset(getAssets(), "vazir.ttf");
        textView.setTypeface(tf);
        textView.setTextIsSelectable(true);


        Typeface tf1 = Typeface.createFromAsset(getAssets(), "font.ttf");

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.toolbar_layout);

        collapsingToolbar.setCollapsedTitleTypeface(tf1);
        collapsingToolbar.setExpandedTitleTypeface(tf1);

        title=findViewById(R.id.text_title);

    }

    public void imageShow(View view) {
        Intent intent = new Intent(getApplicationContext(), ImageZoom.class);
        intent.putExtra("ImageForZoom", SData[0]);
        startActivity(intent);

    }

    public void share(View view) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, SData[1] + "\n" + "\n" + SData[2] + "\n" + SData[4]);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "The title");
        startActivity(Intent.createChooser(shareIntent, "اشتراک با:"));
    }

    public void detail(View view) {
        new AlertDialog.Builder(this, R.style.DialogTheme)
                .setTitle("اطلاعات پست:")
                .setMessage("تیتر: " + SData[1] + "\n" + "ساعت: " + SData[5] + "\n" +
                        "تاریخ: " + SData[4] + "\n" + "شناسه:" + SData[6] + "\n" +
                        "نویسنده: " + SData[3] + "\n" + "شناسه نویسنده: " + SData[10] + "\n" + "تعداد لایک: " + SData[8])
                .setNeutralButton("بستن", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    }


