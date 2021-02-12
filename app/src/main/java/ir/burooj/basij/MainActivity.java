package ir.burooj.basij;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;


public class MainActivity extends AppCompatActivity {

    public static ApiInterface apiInterface;

    public static Picasso pica;

    RecyclerView recyclerView;
    SwipeRefreshLayout pullToRefresh;
    public static List<Item> mItem = new ArrayList<>();
    public static String[] appDetails;
    String[] inData;
    BottomNavigationView bottomNavigationView;

    SharedPreferences shPref;
    public static final String MyPref = "BuroojPrefers1";
    private static final String DarkMode = "DarkMode";
    public static final String tokenName = "tokenNameKey";
    public static final String userIdName = "userIdNameKey";
    public static final String haveAccountName = "haveAccount";
    String token = "", userId = "", haveAccount = "";

    AlertDialog.Builder alertBuilder;
    String appLink = "";
    static String weekState = "", weekString = "";
    static MenuItem menuItem1;
    public static String modeD = "white";

    NotificationManager notifManager;
    String offerChannelId = "Offers";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTheme0();
        setContentView(R.layout.activity_main);
        menuItem1 = null;
        init();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            inData = extras.getStringArray("SData");
        }

        /*
        SharedPreferences
        */
        laodshar();
        /*
        SharedPreferences
        */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Fade fade = new Fade();
            View decor = getWindow().getDecorView();
            fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);

            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);

        }
    }

    private void laodshar() {
        shPref = getSharedPreferences(MyPref, Context.MODE_PRIVATE);

        if (shPref.contains(tokenName)) {
            token = shPref.getString(tokenName, null);
        } else {
            logOut(getApplicationContext());
            finish();

            Toast.makeText(getApplicationContext(), "مشکل بزرگی پیش اومده!", Toast.LENGTH_LONG).show();
        }
        if (shPref.contains(userIdName)) {
            userId = shPref.getString(userIdName, null);
        } else {
            logOut(getApplicationContext());
            finish();
            Toast.makeText(getApplicationContext(), "مشکل بزرگی پیش اومده!", Toast.LENGTH_LONG).show();
        }
        if (shPref.contains(haveAccountName)) {
            haveAccount = shPref.getString(haveAccountName, null);
        } else {
            logOut(getApplicationContext());
            finish();
            Toast.makeText(getApplicationContext(), "مشکل بزرگی پیش اومده!", Toast.LENGTH_LONG).show();
        }

    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        pullToRefresh = findViewById(R.id.pullToRefresh);
        // progressBarB = (ProgressBar) findViewById(R.id.progressBar4);
        bottomNavigationView = findViewById(R.id.b_nav);

        apiInterface = Api.getAPI().create(ApiInterface.class);

        OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        OkHttp3Downloader okHttp3Downloader = new OkHttp3Downloader(client);

        MainActivity.pica = new Picasso.Builder(getApplicationContext()).downloader(okHttp3Downloader).build();

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getStringArray("newDetails") != null) {
            appDetails = extras.getStringArray("newDetails");
            showAlertDialog(1);
        }
        if (extras != null && extras.getString("newVersion") != null) {
            appLink = extras.getString("newVersion");
            showAlertDialog(2);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        if (menuItem1 == null) {
            PostFragment postFragment = new PostFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frg_holder, postFragment);
            ft.commit();
            bottomNavigationView.setSelectedItemId(R.id.item_1);

        }


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                menuItem1 = item;
                return fFragment(item);
            }
        });


    }

    int i = 0;

    private boolean fFragment(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item_1:
                if (i != 1) {
                    PostFragment postFragment = new PostFragment();
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.frg_holder, postFragment);
                    ft.commit();
                    i = 1;
                }
                return true;

            case R.id.item_2:
                if (i != 2) {
                    try {
                        weekState = appDetails[7];
                        weekString = appDetails[8];
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ToolsFragment toolsFragment = new ToolsFragment();
                    FragmentManager fm0 = getSupportFragmentManager();
                    FragmentTransaction ft0 = fm0.beginTransaction();
                    ft0.replace(R.id.frg_holder, toolsFragment);
                    ft0.commit();
                    i = 2;
                }
                return true;

            case R.id.item_3:
                if (i != 3) {
                    AccountFragment accountFragment = new AccountFragment();
                    FragmentManager fm1 = getSupportFragmentManager();
                    FragmentTransaction ft1 = fm1.beginTransaction();
                    ft1.replace(R.id.frg_holder, accountFragment);
                    ft1.commit();
                    i = 3;
                }
                return true;
        }
        return false;

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    //برای پاپ آپ
    private void showAlertDialog(int i) {
        if (i == 1) {
            if (appDetails[0].equals("1")) {
                int h = R.style.DialogTheme;
                if (modeD.equals("black")) {
                    h = R.style.DialogThemeDark;
                }
                final AlertDialog.Builder dl = new AlertDialog.Builder(MainActivity.this, h);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.popup_dialog, null);
                dl.setView(dialogView);
                //dl.setCancelable(false);
                final ImageView imageView = dialogView.findViewById(R.id.imageView);
                final TextView textView = dialogView.findViewById(R.id.textView2);
                final TextView textView3 = dialogView.findViewById(R.id.textView3);
                Picasso.get().load(appDetails[1]).placeholder(R.drawable.defaultpic)
                        .error(R.drawable.errorpic).into(imageView);
                textView.setText(appDetails[2]);
                textView3.setText(appDetails[3]);
                dl.setNeutralButton("بستن",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                //
                final int[] finalHeight = new int[1];
                final int[] finalWidth = new int[1];
                ViewTreeObserver vto = imageView.getViewTreeObserver();
                vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                        finalHeight[0] = imageView.getMeasuredHeight();
                        finalWidth[0] = imageView.getMeasuredWidth();
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        params.height = (finalWidth[0] / 2);
                        params.width = finalWidth[0];
                        imageView.setLayoutParams(params);
                        return true;
                    }
                });
                //
                dl.show();
            }
        } else if (i == 2) {

            createNotifChannel();
            simpleNotification();


            int h = R.style.DialogTheme;
            if (modeD.equals("black")) {
                h = R.style.DialogThemeDark;
            }
            new AlertDialog.Builder(this, h)
                    .setTitle("نسخه جدید:")
                    .setMessage("نسخه ای جدید از بروج آماده دانلود می باشد؛" + "\n" + "جهت دانلود این نسخه اقدام کنید.")
                    .setPositiveButton("دانلود", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(getApplicationContext(), DownloadNewVersion.class);
                            startActivity(i);
                            dialog.dismiss();

                        }
                    })
                    .setNeutralButton("بستن", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    void logOut(Context context) {
        Tools.sharedPreference(context, "", "", "");
        Intent intent = new Intent(context, SplashScreen.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "sdsd", Toast.LENGTH_LONG).show();
    }

    private void setTheme0() {
        final String MyPref1 = "BuroojSettingPrefersDarkMode";
        SharedPreferences shPref1 = getSharedPreferences(MyPref1, Context.MODE_PRIVATE);
        if (shPref1.contains(DarkMode)) {
            modeD = shPref1.getString(DarkMode, "white");
            if (modeD.equals("black")) {
                modeD = "black";
            } else {
                modeD = "white";
            }
        } else {
            modeD = "white";
        }
        setTheme(getTheme(modeD));
    }

    private int getTheme(String theme) {
        if (theme.equals("black")) return R.style.AppTheme3;
        return R.style.AppTheme2; // stub
    }

    private void createNotifChannel() {
        notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String offerChannelName = "new version";
            String offerChannelDescription = "Burooj new version when available";
            int offerChannelImportance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notifChannel = new NotificationChannel(offerChannelId, offerChannelName, offerChannelImportance);
            notifChannel.setDescription(offerChannelDescription);
            //notifChannel.enableVibration(true);
            notifChannel.enableLights(true);
            notifChannel.setLightColor(Color.GREEN);

            notifManager.createNotificationChannel(notifChannel);

        }

    }

    public void simpleNotification() {

        NotificationCompat.Builder sNotifBuilder = new NotificationCompat.Builder(this, offerChannelId)
                .setSmallIcon(R.drawable.ic_action_replace)
                .setContentTitle("بروزرسانی")
                .setContentText("یک نسخه جدید از برنامه آماده دریافت است")
                .setVibrate(new long[]{100, 500, 300, 500, 300, 500})
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_out_of_time))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        notifManager.notify(1, sNotifBuilder.build());

    }
}
