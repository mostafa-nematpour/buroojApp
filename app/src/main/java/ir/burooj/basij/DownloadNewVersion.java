package ir.burooj.basij;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ir.burooj.basij.apiClass.AppDetails;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ir.burooj.basij.MainActivity.modeD;
import static java.lang.Thread.sleep;

public class DownloadNewVersion extends BAppCompatActivity {
    ProgressBar androidProgressBar;
    int progressStatusCounter = 0;
    Button button;
    TextView textView, textViewError, textViewD;
    int progress = 0;
    RelativeLayout relativeLayout;
    AVLoadingIndicatorView progressBarB;
    LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_new_version);
        button = findViewById(R.id.button_download);
        textView = findViewById(R.id.text_download);
        textViewD = findViewById(R.id.textView21);
        relativeLayout = findViewById(R.id.relative_layout_dnv);
        textViewError = findViewById(R.id.text_dnv);
        progressBarB = findViewById(R.id.avl);
        animationView = findViewById(R.id.download_animation);

        androidProgressBar = (ProgressBar) findViewById(R.id.horizontal_progress_bar);
        runMan();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                install();

                return true;
            }
        });
    }

    private void install() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (getPackageManager().canRequestPackageInstalls()) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                @SuppressLint("SdCardPath") Uri uri = FileProvider.getUriForFile(
                        getApplicationContext(),
                        BuildConfig.APPLICATION_ID,
                        new File("/data/data/" + getPackageName() + "/apk/burooj_app.apk")
                );
                intent.setDataAndType(
                        uri,
                        MimeTypeMap.getSingleton().getMimeTypeFromExtension("apk")
                );
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent);
            } else {
                startActivity(
                        new Intent(
                                android.provider.Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                                Uri.parse("package:ir.burooj.basij")
                        )
                );
            }
        } else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            @SuppressLint("SdCardPath") Uri uri = FileProvider.getUriForFile(
                    getApplicationContext(),
                    BuildConfig.APPLICATION_ID,
                    new File("/data/data/" + getPackageName() + "/apk/burooj_app.apk")
            );
            intent.setDataAndType(uri, MimeTypeMap.getSingleton().getMimeTypeFromExtension("apk"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        }
    }

    private void runMan() {
        relativeLayout.setVisibility(View.VISIBLE);
        textViewError.setOnClickListener(null);
        textViewError.setText(R.string.waiting);
        progressBarB.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            /* class ir.tapgroup.tapservice.SplashActivity.AnonymousClass1 */

            public void run() {
                getDi();
            }
        }, 1000);
    }

    private void getDi() {
        textViewError.setOnClickListener(null);
        textViewError.setText(R.string.waiting);
        progressBarB.setVisibility(View.VISIBLE);
        Call<AppDetails> details = SplashScreen.apiInterface.details();
        details.enqueue(new Callback<AppDetails>() {
            @Override
            public void onResponse(Call<AppDetails> call, Response<AppDetails> response) {
                try {
                    AppDetails appDetails = response.body();

                    if (appDetails != null) {
                        animationView.playAnimation();
                        try {
                            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                            String version = pInfo.versionName;
                            int verCode = pInfo.versionCode;
                            if (!(Integer.parseInt(appDetails.getMinVersion()) >= verCode)) {
                                textView.setText("نسخه شما به\u200cروز هست.");
                                button.setOnClickListener(null);
                            }
                            relativeLayout.setVisibility(View.GONE);
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }

                    } else {
                        textViewError.setText("مشکلی در ارتباط پیش آمده");
                        progressBarB.setVisibility(View.INVISIBLE);
                        textViewError.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                runMan();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    textViewError.setText("مشکلی در ارتباط پیش آمده");
                }
            }

            @Override
            public void onFailure(Call<AppDetails> call, Throwable t) {
                textViewError.setText("از اتصال اینترنت خود اطمینان حاصل فرمایید.");
                progressBarB.setVisibility(View.GONE);
                textViewError.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        runMan();
                    }
                });
            }
        });
    }

    final String TAG = "downloadNew";

    private void download() {
        button.setOnClickListener(null);
        Call<ResponseBody> call = MainActivity.apiInterface.downloadBurooj("file/burooj.apk");
        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    animationView.setAnimation(R.raw.download1);
                    animationView.setFrame(0);
                    Log.d(TAG, "server contacted and has file");

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // do your stuff
                            if (saveToDisk(response.body())) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //install();
                                        animationView.setAnimation(R.raw.download);
                                        ///animationView.setMinFrame(13);
                                        animationView.playAnimation();
                                        animationView.addAnimatorListener(new Animator.AnimatorListener() {
                                            @Override
                                            public void onAnimationStart(Animator animation) {
                                            }

                                            @Override
                                            public void onAnimationEnd(Animator animation) {

                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        // do your stuff
                                                        try {
                                                            sleep(500);
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        }
                                                        runOnUiThread(new Runnable() {
                                                            public void run() {
                                                                install();
                                                            }
                                                        });
                                                    }
                                                }).start();

                                            }

                                            @Override
                                            public void onAnimationCancel(Animator animation) {
                                            }

                                            @Override
                                            public void onAnimationRepeat(Animator animation) {
                                            }
                                        });
                                        button.setText("نصب");
                                        button.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                install();
                                            }
                                        });

                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        textView.setText("مشکلی در حین دانلود پیش آمده.");

                                        button.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                download();
                                            }
                                        });
                                    }
                                });
                            }
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    // do onPostExecute stuff
                                }
                            });
                        }
                    }).start();

                } else {
                    Log.d(TAG, "server contact failed");
                    textView.setText("server contact failed");
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            download();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "error");
                textView.setText("مشکلی در اتصال پیش آمده.");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        download();
                    }
                });
            }
        });
    }


    public boolean saveToDisk(final ResponseBody body) {

        try {

            new File("/data/data/" + getPackageName() + "/apk").mkdir();
            File destinationFile = new File("/data/data/" + getPackageName() + "/apk/burooj_app.apk");

            InputStream is = null;
            OutputStream os = null;

            try {
                Log.d(TAG, "File Size=" + body.contentLength());

                is = body.byteStream();
                os = new FileOutputStream(destinationFile);

                byte[] data = new byte[4096];
                int count;
                progress = 0;
                while ((count = is.read(data)) != -1) {
                    os.write(data, 0, count);
                    progress += count;

                    Log.d(TAG, "Progress: " + progress + "/" + body.contentLength() + " >>>> " + (float) progress / body.contentLength());

                    runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            progressStatusCounter = (int) ((progress * 100) / body.contentLength());
                            androidProgressBar.setProgress(progressStatusCounter);
                            try {
                                animationView.setFrame(progressStatusCounter * 3);
                            } catch (Exception e) {
                            }
                        }
                    });
                }


                os.flush();

                Log.d(TAG, "File saved successfully!");

                return true;
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Failed to save the file" + e.getMessage());
                return false;
            } finally {
                if (is != null) is.close();
                if (os != null) os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Failed to save the file!");
            return false;
        }
    }

}
