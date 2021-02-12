package ir.burooj.basij;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Time;
import android.transition.Fade;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.WriterException;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import uk.co.senab.photoview.PhotoView;

import static ir.burooj.basij.MainActivity.pica;

public class ImageZoom extends BAppCompatActivity {

    PhotoView imageView;
    FloatingActionButton floatingActionButton;
    private final int WRITE_EXTERNAL_STORAGE = 100;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        setContentView(R.layout.activity_image_zoom);
        imageView = findViewById(R.id.image_zoom);
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSave();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getString("qrToken") == null) {
                String url = extras.getString("ImageForZoom");

                pica.load(url)
                        .placeholder(R.drawable.defaultpic)
                        .error(R.drawable.errorpic)
                        .into(imageView);
            } else {

                String token = extras.getString("qrToken");
                qr(imageView, token);

            }

        } else {
            Toast.makeText(getApplicationContext(), "مشکلی در بار گزاری پیش آمده", Toast.LENGTH_LONG).show();
            finish();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Fade fade = new Fade();
            View decor = getWindow().getDecorView();
            fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);
            getWindow().setExitTransition(fade);

            getWindow().setEnterTransition(fade);
        }
    }


    int x0,y0;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart() {
        super.onStart();
        final RelativeLayout linearLayout = findViewById(R.id.line13);

//
//        imageView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                int x = (int) event.getX();
//                int y = (int) event.getY();
//
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        Log.i("TAG", "touched down");
//                        x0=x;
//                        y0=y;
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        Log.i("TAG", "moving: (" + x + ", " + y + ")");
//                        if(y-y0>500){
//                            Toast.makeText(getApplicationContext(),"okk",Toast.LENGTH_SHORT).show();
//
//
//                            linearLayout.setBackgroundColor(Color.parseColor("#4B000000"));
//
//                        }
//
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        Log.i("TAG", "touched up");
//                        break;
//                }
//                return true;
//            }
//        });

    }

    private void qr(ImageView imageView, String token) {
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;
        QRGEncoder qrgEncoder = new QRGEncoder(
                token, null,
                QRGContents.Type.TEXT,
                smallerDimension);
        try {
            Bitmap bitmap = qrgEncoder.encodeAsBitmap();
            imageView.setImageBitmap(bitmap);
            // Toast.makeText(getApplicationContext(),token,Toast.LENGTH_LONG).show();

        } catch (WriterException e) {
            Log.v("qr", e.toString());
        }
    }

    private void onSave() {
        if (ContextCompat.checkSelfPermission(ImageZoom.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestSTORAGEPermission();
        } else {
            save();
        }
    }


    private void save() {
        FileOutputStream out = null;
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        try {
            out = new FileOutputStream(getFilename());
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            Snackbar.make(imageView, "تصویر ذخیره شد.", Snackbar.LENGTH_SHORT)
                    .show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Snackbar.make(imageView, "خطایی در ذخیره سازی رخ داد", Snackbar.LENGTH_SHORT).show();
        }

    }

    private String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory()
                .getPath(), "Pictures" + "/" + "بروج");
        if (!file.exists()) {
            file.mkdirs();
        }
        String currentDateandTime = "IMG_";
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        String day = (today.monthDay + "");             // Day of the month (1-31)
        String month = (today.month + "");              // Month (0-11)
        String year = (today.year + "");                // Year
        String time = (today.format("%k%M%S"));  // Current time
        currentDateandTime += year + month + day + "_" + time;
        String uriSting = (file.getAbsolutePath() + "/"
                + currentDateandTime + ".jpg");
        return uriSting;
    }


    ///پرمیشن

    private void requestSTORAGEPermission() {


        if (ActivityCompat.shouldShowRequestPermissionRationale(ImageZoom.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this, R.style.DialogTheme)
                    .setTitle("درخواست مجوز")
                    .setMessage("برای ذخیره عکس باید مجوز را تایید کنید.")
                    .setPositiveButton("موافقم", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            reqPermission();
                        }
                    })
                    .setNegativeButton("لغو", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create()
                    .show();

        } else {
            reqPermission();
        }

    }

    private void reqPermission() {
        ActivityCompat.requestPermissions(ImageZoom.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "مجوز تایید شد", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "مجوز رد شد", Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
