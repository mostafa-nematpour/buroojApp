package ir.burooj.basij;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BAppCompatActivity extends AppCompatActivity {

    SharedPreferences shPref;
    private static final String counter = "counter";
    private static final String MyPref = "BuroojPrefers1";
    private static final String tokenName = "tokenNameKey";
    private static final String userIdName = "userIdNameKey";
    private static final String haveAccountName = "haveAccount";
    private static final String InvitationCodeName = "InvitationCode";
    public String token = "", userId = "", haveAccount = "";

    public ApiInterface apiInterface;
    public String modeD = "white";
    private static final String DarkMode = "DarkMode";
    final String MyPref1 = "BuroojSettingPrefersDarkMode";
    int def = R.drawable.defaultpic;

    int placeholder = R.drawable.defaultpic;

    public BAppCompatActivity() {
        super();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme0();
        loadShared();
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        apiInterface = Api.getAPI().create(ApiInterface.class);
//         overridePendingTransition(0, R.anim.slide_up);
        overridePendingTransition(0, 0);

        if (modeD.equals("black")) {
            placeholder = R.drawable.custom_back_8;
        }

        try {
            if (MainActivity.modeD.equals("black")) {
                def = R.drawable.custom_back_8;
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    private void loadShared() {
        shPref = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        if (shPref.contains(haveAccountName)) {
            haveAccount = shPref.getString(haveAccountName, "");
        } else {
            logOut(getApplicationContext());
        }
        if (shPref.contains(tokenName)) {
            token = shPref.getString(tokenName, "");
        } else {
            logOut(getApplicationContext());
            finish();
        }
        if (shPref.contains(userIdName)) {
            userId = shPref.getString(userIdName, "");
        } else {
            logOut(getApplicationContext());
            finish();
        }
    }

    void logOut(Context context) {
        Log.d("tag1", "logOut: ---------------logOut----------------");
        shPref = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = shPref.edit();
        sEdit.putString(haveAccountName, "");
        sEdit.putString(tokenName, "");
        sEdit.putString(userIdName, "");
        sEdit.apply();
        Intent intent = new Intent(context, SplashScreen.class);
        startActivity(intent);
    }

    public void counterPlus() {
        SharedPreferences shPref = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = shPref.edit();
        if (shPref.contains(counter)) {
            int counterN = shPref.getInt(counter, 0) + 1;
            sEdit.putInt(counter, counterN).apply();
            sEdit.commit();
        }else {
            sEdit.putInt(counter, 1).apply();
            sEdit.commit();
        }
    }

    public int getAppCounter(){
        try {
            SharedPreferences shPref = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
            return shPref.getInt(counter, 0);
        }catch (Exception e){
            return 0;
        }
    }

    public void setAllDefaultTheme() {
        try {
            int currentNightMode = getResources().getConfiguration().uiMode
                    & Configuration.UI_MODE_NIGHT_MASK;
            if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
                Log.d("tag1", "setAllDefaultTheme: dark");
                SharedPreferences shPref = getSharedPreferences(MyPref1, Context.MODE_PRIVATE);
                SharedPreferences.Editor sEdit = shPref.edit();
                sEdit.putString(DarkMode, "black").apply();
                sEdit.commit();
            }
        } catch (Exception e) {
            Log.d("tag1", e.getMessage());
        }
    }

    public void setTheme0() {
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
        return R.style.AppTheme2;
    }
}
