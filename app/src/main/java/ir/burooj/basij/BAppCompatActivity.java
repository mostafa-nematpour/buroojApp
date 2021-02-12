package ir.burooj.basij;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BAppCompatActivity extends AppCompatActivity {

    SharedPreferences shPref;
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
        // Toast.makeText(getApplicationContext(),modeD,Toast.LENGTH_LONG).show();
        //  overridePendingTransition(R.anim.alpha, R.anim.fix_activity);

        if(modeD.equals("black")){
            placeholder=R.drawable.custom_back_8;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // overridePendingTransition(R.anim.alpha,R.anim.alpha);
    }

    private void loadShared() {
        shPref = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
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
        if (shPref.contains(haveAccountName)) {
            haveAccount = shPref.getString(haveAccountName, "");
        } else {
            logOut(getApplicationContext());
            finish();
        }
    }

    void logOut(Context context) {
        shPref = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = shPref.edit();
        sEdit.putString(haveAccountName, "");
        sEdit.putString(tokenName, "");
        sEdit.putString(userIdName, "");
        sEdit.apply();
        Intent intent = new Intent(context, SplashScreen.class);
        startActivity(intent);
    }


    private void setTheme0() {
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
