package ir.burooj.basij;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.wang.avi.AVLoadingIndicatorView;

import ir.burooj.basij.apiClass.CompleteProfile;
import ir.burooj.basij.apiClass.GetUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ir.burooj.basij.MainActivity.pica;

public class CompleteProfileActivity extends BAppCompatActivity implements
        AdapterView.OnItemSelectedListener {




    /*-*/
    /*
     *
     *
     *
     *
     * تغیر تو این ایجاد نکن
     *
     *
     *
     *
     * */



    int j = 0;
    RelativeLayout relativeLayoutLoading, relativeLayoutalal;
    EditText editTextName, editTextLastName, editTextShomare;
    TextView textView;
    AVLoadingIndicatorView avLoadingIndicatorView;
    Button button;
    TextInputLayout textInputLayoutName, textInputLayoutLastName, textInputLayoutShomare;
    Spinner spin;
    String[] country = {"رشته خود را انتخاب کنید.", "1- آمار", "2- علوم کامپیوتر", "3- زیست", "4- زمین", "5- شیمی", "6- فیزیک", "7- مهندسی برق",
            "8- مهندسی پلیمر", "9- مهندسی عمران", "10- مهندسی صنایع", "11- مهندسی مکانیک", "12- مهندسی کامپیوتر", "13- معماری", "14- ادبیات", "15- جغرافیا", "16- ادبیات انگلیسی",
            "17- زبان انگلیسی", "18- علوم اجتماعی", "19- معارف", "20- تربیت", "21- اقتصاد", "22- مدیرت",
            "23- حسابداری", "25- شیمی", "26- عمران", "27- نقشه برداری", "در بین گزینه\u200cها نیست."};




    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);
        relativeLayoutLoading = findViewById(R.id.relative2);
        editTextName = findViewById(R.id.name_edit_text);
        editTextLastName = findViewById(R.id.last_name_edit_text);
        editTextShomare = findViewById(R.id.shomare_edit_text);
        textView = findViewById(R.id.txt_load);
        avLoadingIndicatorView = findViewById(R.id.avi11);
        relativeLayoutalal = findViewById(R.id.alal);
        button = findViewById(R.id.btn_okk);
        textInputLayoutName = findViewById(R.id.name_layout);
        textInputLayoutLastName = findViewById(R.id.last_name_layout);
        textInputLayoutShomare = findViewById(R.id.shomare_layout);
        spin = (Spinner) findViewById(R.id.spinner);
        if(modeD.equals("black")){
            ImageView imageView=findViewById(R.id.image_complete);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_complet_account_dark));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                String s7 = extras.getString("name12");
                getSupportActionBar().setTitle(s7);
            } else {
                getSupportActionBar().setTitle("بروج");
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        relativeLayoutLoading.setVisibility(View.VISIBLE);
        relativeLayoutLoading.setOnClickListener(null);
        relativeLayoutalal.setOnClickListener(null);
        hideKeyboardFrom(getApplicationContext(), editTextShomare);
        j = 0;
        getUser();


        ///
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter<String> aa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);


        ///
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textInputLayoutName.setError("");
                textInputLayoutLastName.setError("");
                textInputLayoutShomare.setError("");

                int i = 0;
                if (Tools.check(editTextShomare, textInputLayoutShomare, 10, 10,
                        "شماره\u200Cدانشجویی نمی\u200Cتواند خالی باشد."
                        , "شماره\u200Cدانشجویی نمی\u200Cتواند کمتر از 10 کاراکتر باشد!"
                        , "شماره\u200Cدانشجویی نمی\u200Cتواند بیشتر از 10 کاراکتر باشد!")) {
                    i++;
                }

                if (Tools.check(editTextLastName, textInputLayoutLastName, 3, 30,
                        "نام\u200Cخانوادگی نمی\u200Cتواند خالی باشد."
                        , "نام\u200Cخانوادگی نمی\u200Cتواند کمتر از 3 کاراکتر باشد!"
                        , "نام\u200Cخانوادگی نمی\u200Cتواند بیشتر از 30 کاراکتر باشد!")) {
                    i++;
                }

                if (Tools.check(editTextName, textInputLayoutName, 3, 20,
                        "نام نمی\u200Cتواند خالی باشد."
                        , "نام نمی\u200Cتواند کمتر از 3 کاراکتر باشد!"
                        , "نام نمی\u200Cتواند بیشتر از 20 کاراکتر باشد!")) {
                    i++;
                }

                if (j == 0) {
                    Snackbar.make(button, "رشته خود را انتخاب کنید.", Snackbar.LENGTH_LONG).show();
                } else {
                    i++;
                }


                if (i == 4) {
                    if (!(editTextShomare.getText().toString().equals(user.getShomare_daneshjouyi()) &&
                            editTextLastName.getText().toString().equals(user.getLastname()) &&
                            editTextName.getText().toString().equals(user.getName()) && j == Integer.parseInt(user.getReshte_edu()))) {
                        alert();
                    } else {
                        Snackbar.make(button, "ابتدا تغییری ایجاد کنید.", Snackbar.LENGTH_LONG).show();
                    }

                }
            }
        });
    }


    private void alert() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.DialogTheme);
        alertBuilder.setMessage("صحت اطلاعات خود را تایید می\u200cکنید؟");
        alertBuilder.setPositiveButton("بله", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                complete(userId, token, editTextName.getText().toString(),
                        editTextLastName.getText().toString(), j,
                        editTextShomare.getText().toString());

            }
        });
        alertBuilder.setNegativeButton("بستن", null);
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    GetUser user;

    private void getUser() {
        Call<GetUser> getUserCall = MainActivity.apiInterface.getUser(userId, token);
        getUserCall.enqueue(new Callback<GetUser>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<GetUser> call, Response<GetUser> response) {
                user = response.body();
                if (user != null && user.getResponse() == 1) {
                    relativeLayoutLoading.setVisibility(View.GONE);
                    if (user.getName() != null) {
                        editTextName.setText(user.getName());
                    } else {
                        editTextName.requestFocus();
                    }
                    if (user.getLastname() != null) {
                        editTextLastName.setText(user.getLastname());
                    } else {
                        editTextLastName.requestFocus();
                    }
                    if (user.getShomare_daneshjouyi() != null) {
                        editTextShomare.setText(user.getShomare_daneshjouyi());
                    } else {
                        editTextShomare.requestFocus();
                    }
                    if (user.getReshte_edu() != null) {
                        try {
                            j = Integer.parseInt(user.getReshte_edu());
                            spin.setSelection(j);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetUser> call, Throwable t) {
                textView.setText("مشکلی در ارتباط پیش\u200Cآمده.");
                avLoadingIndicatorView.setVisibility(View.INVISIBLE);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textView.setText("منتظر بمانید.");
                        avLoadingIndicatorView.setVisibility(View.VISIBLE);
                        getUser();
                    }
                });
            }
        });
    }


    private void complete(String userId, String token, String name, String lastname, int reshte,
                          String shomareDaneshjouyi) {

        Call<CompleteProfile> completeProfileCall = MainActivity.apiInterface.completeProfile
                (userId, token, name, lastname, reshte, shomareDaneshjouyi);

        completeProfileCall.enqueue(new Callback<CompleteProfile>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<CompleteProfile> call, Response<CompleteProfile> response) {
                CompleteProfile completeProfile = response.body();
                if (completeProfile != null) {

                    switch (completeProfile.getResponse()) {
                        case "1":
                            buttonBack(1, "اطلاعات با موفقیت ثبت شد.");
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            });
                            break;
                        case "-2":
                            buttonBack(2, "ورودی نامعتبر!");
                            break;
                        case "-1":
                            finish();
                            break;
                        case "303":
                            buttonBack(2, "ورودی نامعتبر است.");
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            });
                            break;
                        default:
                            buttonBack(2, completeProfile.getResponse() + "  مشکلی پیش آمده  ");
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            });
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<CompleteProfile> call, Throwable t) {
                button.setText("مشکلی در ارتباط پیش\u200Cآمده.");
            }
        });

    }

    private void buttonBack(int i, String s) {

        if (i == 1) {
            button.setText(s);
            button.setTextColor(Color.parseColor("#FFFFFF"));
            button.setBackground(getResources().getDrawable(R.drawable.custom_button_5));
        } else if (i == 2) {
            button.setText(s);
            button.setTextColor(Color.parseColor("#FFFFFF"));
            button.setBackground(getResources().getDrawable(R.drawable.custom_button_6));
        }
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        //  Toast.makeText(getApplicationContext(), ""+position, Toast.LENGTH_LONG).show();
        j = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }



}
