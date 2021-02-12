package ir.burooj.basij;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Objects;


public class SettingActivity extends BAppCompatActivity {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch aSwitch;

    boolean flag = false;
    private SharedPreferences shPref;
    private static final String MyPref1 = "BuroojSettingPrefersDarkMode";
    private static final String DarkMode = "DarkMode";
    String modeD = "";
    Button buttonExit, buttonChange1, buttonUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shPref = getSharedPreferences(MyPref1, Context.MODE_PRIVATE);


        setContentView(R.layout.activity_setting);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        aSwitch = findViewById(R.id.switch1);
        if (shPref.contains(DarkMode)) {
            modeD = shPref.getString(DarkMode, "white");
            if (modeD.equals("black")) {
                aSwitch.setChecked(true);
            } else if (modeD.equals("white")) {
                aSwitch.setChecked(false);
            }
        } else {
            aSwitch.setChecked(false);
            modeD = "white";
        }
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor sEdit = shPref.edit();
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                if (isChecked) {
                    sEdit.putString(DarkMode, "black");
                } else {
                    sEdit.putString(DarkMode, "white");
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                sEdit.apply();
                overridePendingTransition(R.anim.alpha, R.anim.fix_activity);
                finish();
            }
        });

        buttonExit = findViewById(R.id.exit);
        buttonChange1 = findViewById(R.id.button_change1);
        buttonUp = findViewById(R.id.button_update);
        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DownloadNewVersion.class);
                startActivity(intent);
            }
        });
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                khoroj();
            }
        });
        buttonChange1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chang("تغییر\u200cمشخصات");
            }
        });
        try {
            Toolbar toolbar = findViewById(R.id.toolbar_setting);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void khoroj() {
        int theme = R.style.DialogTheme;
        if (modeD.equals("black")) {
            theme = R.style.DialogThemeDark;
        }

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, theme);
        alertBuilder.setMessage("می\u200Cخواهید از حساب کاربریتان خارج شوید؟");
        alertBuilder.setPositiveButton("بله", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logOut(getApplicationContext());
                finish();

            }
        });
        alertBuilder.setNegativeButton("بستن", null);
        AlertDialog alert = alertBuilder.create();
        alertBuilder.show();
    }


    private void chang(String s) {
        Intent intent = new Intent(getApplicationContext(), CompleteProfileActivity.class);
        intent.putExtra("name12", s);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
//        if (flag) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            this.finish();
//        } else {
//            super.onBackPressed();
//        }
    }

}
