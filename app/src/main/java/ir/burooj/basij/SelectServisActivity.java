package ir.burooj.basij;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Objects;


public class SelectServisActivity extends BAppCompatActivity {


    Button button1, button2;
    ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_servis);
        try {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("انتخاب خط سرویس");
        } catch (Exception e) {
            e.printStackTrace();
        }
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        button1 = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        imageView = findViewById(R.id.imageView3);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.3f, 1, 1.3f, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(500);
        imageView.startAnimation(scaleAnimation);
    }


    @Override
    protected void onStart() {
        super.onStart();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myIntent(1,button1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myIntent(2,button2);
            }
        });

        /*
                    اندازه گیری صفحه
        */
        try {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;

            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            params.height = 4 * height / 10;
            imageView.setLayoutParams(params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
                     اندازه گیری صفحه
        */


    }

    private void myIntent(int i,Button button) {

        Intent intent = new Intent(getApplicationContext(), ServisActivity.class);
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, button, ViewCompat.getTransitionName(button));

        intent.putExtra("i", i);
        startActivity(intent, compat.toBundle());

    }

    @Override
    protected void onStop() {
        super.onStop();
        button1.setOnClickListener(null);
        button2.setOnClickListener(null);
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
