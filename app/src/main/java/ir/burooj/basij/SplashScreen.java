package ir.burooj.basij;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import ir.burooj.basij.apiClass.AppDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashScreen extends BAppCompatActivity {
    public static ApiInterface apiInterface;

    TextView textViewError, textViewBurooj;
    String stringError = "";
    boolean flag = true;
    boolean flag0 = true;
    Button button;
    AVLoadingIndicatorView progressBarB;


    SharedPreferences shPref;
    public static final String MyPref = "BuroojPrefers1";
    public static final String tokenName = "tokenNameKey";
    public static final String userIdName = "userIdNameKey";
    public static final String haveAccountName = "haveAccount";
    String token = "", userId = "", haveAccount = "";
    private String modeD = "white";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        textViewError = findViewById(R.id.text_error);
        textViewBurooj = findViewById(R.id.textView);
        button = findViewById(R.id.button3);
        progressBarB = findViewById(R.id.avi);
        apiInterface = Api.getAPI().create(ApiInterface.class);
        textViewBurooj.setTypeface(Typeface.createFromAsset(getAssets(), "lalezar.ttf"));
    }


    public void refresh(View view) {
        button.setVisibility(View.INVISIBLE);
        textViewError.setText("");
        main();
        progressBarB.setVisibility(View.VISIBLE);

    }

    public void main() {
        boolean b = false;
        shPref = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        //آیا اکانت دارد؟؟؟؟؟؟؟؟
        if (shPref.contains(haveAccountName) && shPref.contains(tokenName)) {
            if (shPref.getString(haveAccountName, "").equals("Yes")) {
                b = true;
            } else {
                Intent intent = new Intent(getApplicationContext(), GettingUserNumber.class);
                startActivity(intent);
                finish();
            }
        } else {
            Intent intent = new Intent(getApplicationContext(), GettingUserNumber.class);
            startActivity(intent);
            finish();
        }

        final AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0.1f);
        alphaAnimation.setDuration(800);
        alphaAnimation.setRepeatCount(3);
        alphaAnimation.setFillAfter(false);
        alphaAnimation.setRepeatMode(Animation.REVERSE);


        textViewError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewError.setText(stringError);
            }
        });
        if (b) {
            Call<AppDetails> details = SplashScreen.apiInterface.details();
            details.enqueue(new Callback<AppDetails>() {
                @Override
                public void onResponse(Call<AppDetails> call, Response<AppDetails> response) {
                    try {
                        AppDetails appDetails = response.body();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(SplashScreen.this, textViewBurooj, ViewCompat.getTransitionName(textViewBurooj));

                        if (appDetails != null) {

                            try {
                                PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                                String version = pInfo.versionName;
                                int verCode = pInfo.versionCode;
                                if (Integer.parseInt(appDetails.getMinVersion()) > verCode) {
                                    intent.putExtra("newVersion", appDetails.getDownloadLink());

                                }
                                // intent.putExtra("newVersion", appDetails.getDownloadLink());

                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }

                            String[] strings = new String[9];
                            strings[0] = appDetails.getIsNews();
                            strings[1] = appDetails.getPopupImage();
                            strings[2] = appDetails.getPopupTitle();
                            strings[3] = appDetails.getPopupDesc();
                            strings[4] = appDetails.getMinVersion();
                            strings[5] = appDetails.getDownloadLink();
                            strings[6] = appDetails.getTelegram_link();
                            strings[7] = appDetails.getWeek_state();
                            strings[8] = appDetails.getWeek_string();
                            intent.putExtra("newDetails", strings);
                            startActivity(intent);
                            overridePendingTransition(R.anim.alpha, R.anim.fix_activity);
                            finish();

                        } else {
                            textViewError.setText("مشکلی در ارتباط پیش آمده");
                            stringError = "appDetails = null";

                            button.setVisibility(View.VISIBLE);
                            progressBarB.setVisibility(View.INVISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<AppDetails> call, Throwable t) {
                    textViewError.setText("از اتصال اینترنت خود اطمینان حاصل فرمایید.");

                    textViewError.startAnimation(alphaAnimation);
                    button.setVisibility(View.VISIBLE);
                    progressBarB.setVisibility(View.INVISIBLE);
                    button.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            /**/
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(SplashScreen.this, textViewBurooj, ViewCompat.getTransitionName(textViewBurooj));
                            startActivity(intent);
                            overridePendingTransition(R.anim.alpha, R.anim.fix_activity);
                            finish();

//                            new Handler().postDelayed(new Runnable() {
//                                public void run() {
//
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            finish();
//                                        }
//                                    });
//
//                                }
//                            }, 10000);

                            /**/

                            return true;
                        }
                    });


                    stringError = t.getMessage();

                }
            });
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        main();
    }

}
