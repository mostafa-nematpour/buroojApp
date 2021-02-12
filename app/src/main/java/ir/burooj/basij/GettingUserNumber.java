package ir.burooj.basij;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


//import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import br.com.simplepass.loadingbutton.customViews.OnAnimationEndListener;
import ir.burooj.basij.apiClass.generatePhoneToken;
import ir.burooj.basij.apiClass.verifyPhone;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Thread.sleep;


public class GettingUserNumber extends BAppCompatActivity {

    public static ApiInterface apiInterface;
    EditText editText;
    TextInputLayout textInputLayout;
    CircularProgressButton button;
    TextView textView, textViewN;
    AlertDialog.Builder dl;
    String flag0 = "timeOut";
    String token = "";

    Button btnSheet;
    LinearLayout sheetLayout;
    RelativeLayout relativeLayout0;
    BottomSheetBehavior bottomSheet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_user_number);
        apiInterface = Api.getAPI().create(ApiInterface.class);

        textView = findViewById(R.id.error_text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
//گرفتن و چک شماره
        editText = findViewById(R.id.user_number_edittext);
        button = findViewById(R.id.next);
        editText.requestFocus();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b();
            }
        });
//
        textInputLayout = findViewById(R.id.user_number_layout);
        btnSheet = findViewById(R.id.button);
        sheetLayout = findViewById(R.id.bottom_sheet);
        bottomSheet = BottomSheetBehavior.from(sheetLayout);
        textViewN = findViewById(R.id.text_n);
        relativeLayout0 = findViewById(R.id.relative_layout00);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputLayout.setError("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void b() {
        textInputLayout.setError("");
        if (!editText.getText().toString().trim().equals("")) {
            if (Tools.ifNumberTrue(editText.getText().toString())) {
                button.startAnimation();
                numberProcess(editText.getText().toString());
            } else {
                textInputLayout.setError("شماره واردشده نامعتبر است.");
            }
        } else {
            textInputLayout.setError("فیلد خالی هست.");
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void numberProcess(String number) {

        flag0 = "timeOut";
        Call<generatePhoneToken> posts = apiInterface.generatePhone(number, Build.MODEL);
        posts.enqueue(new Callback<generatePhoneToken>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<generatePhoneToken> call, Response<generatePhoneToken> response) {

                generatePhoneToken phoneToken = response.body();

                if (phoneToken != null && phoneToken.getResponse() != null) {
                    switch (phoneToken.getResponse()) {
                        case 1:
                            if (phoneToken.getToken() != null) {
                                flag0 = "yes";
                                token = phoneToken.getToken();
                            } else {
                                notDo();
                            }
                            break;
                        case -1:
                            flag0 = "AnotherTime";
                            break;
                        case 305:
                            flag0 = "smsError";
                            break;
                        case 304:
                            flag0 = "noDatabase";
                            break;
                        case 303:
                            flag0 = "wrongField";
                            break;
                        default:
                            notDo();
                            break;
                    }
                } else {
                    notDo();
                }

                switch (flag0) {
                    case "yes":
                        // درست / ادامه پروسه
                        LottieAnimationView animationView=findViewById(R.id.sms_animation);
                        animationView.playAnimation();
                        animationView.setRepeatCount(1);
                        textView.setText("");
                        button.doneLoadingAnimation(Color.parseColor("#1A237E"), bitmapp(R.drawable.ic_action_name));
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                relativeLayout0.setVisibility(View.VISIBLE);
                                if (bottomSheet.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                                    bottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
                                }
                            }
                        });
                        showAlertDialog();
                        break;

                    case "timeOut":
                        // timeOut
                        //اشتباه / پیام پاسخی از سمت سرور دریافت نشد
                        button.doneLoadingAnimation(Color.parseColor("#FFE53935"), bitmapp(R.drawable.ic_action_replace));
                        textView.setText("پاسخی از سمت سرور دریافت نشد(timeOut)");
                        again();
                        break;
                    case "smsError":
                        //smsError
                        //اشتباه / پیام ارور پیامک
                        button.doneLoadingAnimation(Color.parseColor("#FFE53935"), bitmapp(R.drawable.ic_action_replace));
                        textView.setText("لطفا در زمانی دیگر تلاش کنید" + "\n" + "در حال حاضر توانایی پاسخ به شما را نداریم(305)");
                        again();
                        break;
                    case "AnotherTime":
                        // -1
                        // اشتباه / پیام بعدا تلاش کنید
                        button.doneLoadingAnimation(Color.parseColor("#FFE53935"), bitmapp(R.drawable.ic_action_replace));
                        textView.setText("بعداً تلاش کنید(حداکثر 45 ثانیه)");
                        again();
                        break;

                    case "noDatabase":
                        // 304
                        // اشتباه / پیام بعدا تلاش کنید304
                        button.doneLoadingAnimation(Color.parseColor("#FFE53935"), bitmapp(R.drawable.ic_action_replace));
                        textView.setText("بعداً تلاش کنید(304)");
                        again();
                        break;

                    case "wrongField":
                        // 303
                        // اشتباه / اطلاعات به سرور به درستی ارسال نشده
                        button.doneLoadingAnimation(Color.parseColor("#FFE53935"), bitmapp(R.drawable.ic_action_replace));
                        textView.setText("اطلاعات به سرور به درستی ارسال نشده\n" +
                                "دوباره امتحان کنید ولی احتمالا درست نمی\u200Cشود\nنسخه برنامه خود\u200Cرا بروزرسانی کنید");
                        again();
                        break;
                    default:
                        // نمی دونم شاید یه روزی پیش اومد
                        button.doneLoadingAnimation(Color.parseColor("#FFE53935"), bitmapp(R.drawable.ic_action_replace));
                        textView.setText("یه مشکلی پیش\u200Cآمده دیگه.");
                        again();
                        break;
                }

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(Call<generatePhoneToken> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

                // 2
                // اشتباه / از اتصال اینترنت خود اطمینان حاصل فرمایید
                button.doneLoadingAnimation(Color.parseColor("#FFE53935"), bitmapp(R.drawable.ic_action_replace));
                textView.setText("از اتصال اینترنت خود اطمینان حاصل فرمایید." + "\n" + "مشکلی در ارتباط با سرور پیش\u200Cآمده.");
                again();


            }
        });
    }

    private void showAlertDialog() {

        final Button b = findViewById(R.id.dbutton);
        final EditText editText1 = findViewById(R.id.user_sms_edittext);
        final TextInputLayout textInputLayout = findViewById(R.id.user_sms_TextInputLayout);

        relativeLayout0.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBST));
        editText1.requestFocus();
        editText1.setText("");
        relativeLayout0.setVisibility(View.VISIBLE);
        bottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);

        bottomSheet.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    relativeLayout0.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBST));
                    relativeLayout0.setVisibility(View.VISIBLE);
                    relativeLayout0.setOnClickListener(null);
                    editText1.requestFocus();
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    relativeLayout0.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBSD));
                    relativeLayout0.setVisibility(View.GONE);
                    hideKeyboardFrom(getApplicationContext(), editText1);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


        textViewN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheet.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheet.setPeekHeight(0);
                    bottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    editText.requestFocus();
                    again2();

                }
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToServer(editText1.getText().toString(), editText1, token);

            }
        });


        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputLayout.setError("");
                String s1;
                s1 = editText1.getText().toString();
                if (s1.length() == 5) {
                    sendToServer(s1, editText1, token);
                } else if (s1.length() > 5) {
                    editText1.setError("کد اشتباه!");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //  dl.show();
    }

    private void sendToServer(String code, final EditText editText1, final String token) {

        final TextInputLayout textInputLayoutc = findViewById(R.id.user_sms_TextInputLayout);
        if (code.length() == 5) {
            Call<verifyPhone> verify = apiInterface.verifyPhoneSms(token, code, Build.VERSION.SDK_INT, Build.MODEL);
            verify.enqueue(new Callback<verifyPhone>() {
                @Override
                public void onResponse(@NotNull Call<verifyPhone> call, @NotNull Response<verifyPhone> response) {
                    final verifyPhone verifyPhone0 = response.body();
                    if (verifyPhone0 != null && verifyPhone0.getResponse() != null) {
                        switch (verifyPhone0.getResponse()) {
                            case 1:
                                if (bottomSheet.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                                    bottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                    bottomSheet.setPeekHeight(0);
                                }
                                // درست / ادامه کار
                                if (verifyPhone0.getHaveAccount() != null &&
                                        verifyPhone0.getHaveAccount().equals("No") &&
                                        verifyPhone0.getToken() != null &&
                                        verifyPhone0.getId() != null) {
                                    // اکانت نداره // رفتن به پرم اولیه
                                    Tools.sharedPreference(getApplicationContext(), verifyPhone0.getHaveAccount(), verifyPhone0.getToken(), verifyPhone0.getId());
                                    Intent intent = new Intent(getApplicationContext(), RegistrationFormActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else if (verifyPhone0.getHaveAccount() != null &&
                                        verifyPhone0.getHaveAccount().equals("Yes") &&
                                        verifyPhone0.getToken() != null &&
                                        verifyPhone0.getId() != null) {

                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            // do your stuff
                                            Tools.sharedPreference(
                                                    getApplicationContext(),
                                                    verifyPhone0.getHaveAccount(),
                                                    verifyPhone0.getToken(),
                                                    verifyPhone0.getId()
                                            );
                                            MainActivity.menuItem1 = null;
                                            try {
                                                sleep(600);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            runOnUiThread(new Runnable() {
                                                public void run() {
                                                    Intent intent = new Intent(
                                                            getApplicationContext(),
                                                            MainActivity.class
                                                    );
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            });
                                        }
                                    }).start();




                                } else {
                                    notDo();
                                }
                                break;
                            case 0:
                                // زمان گذشته
                                textInputLayoutc.setError("کد منقضی شد.");
                                break;
                            case -4:
                                textInputLayoutc.setError("مشکلی پیش آمده.");
                                break;
                            case -1:
                                // کد اشتباه
                                textInputLayoutc.setError("کد اشتباه است.");

                                break;
                            case 304:
                                notDo();
                                break;

                            default:
                                textInputLayoutc.setError("مشکلی نا معلوم پیش آمده.");
                                notDo();
                                break;
                        }
                    } else {
                        notDo();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<verifyPhone> call, @NotNull Throwable t) {
                    textInputLayoutc.setError("اتصال اینترنت خود را برسی فرمایید");
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else if (editText1.getText().toString().equals("")) {
            textInputLayoutc.setError("فیلد خالی هست");
        } else {
            textInputLayoutc.setError("در وارد کردن کد دقت کنید");
        }
    }


    private void again() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                again2();
            }
        });
    }

    private void again2() {
        textView.setText("");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b();
            }
        });
        button.revertAnimation();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        button.dispose();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private Bitmap bitmapp(int i) {
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(i);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private void notDo() {
        Toast.makeText(getApplicationContext(), "از سمت سرور اطلاعات ناقصی ارسال شده", Toast.LENGTH_LONG).show();
        finish();
    }
}
