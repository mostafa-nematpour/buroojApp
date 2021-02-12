package ir.burooj.basij;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import ir.burooj.basij.apiClass.Register;
import ir.burooj.basij.apiClass.generatePhoneToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationFormActivity extends BAppCompatActivity {

    EditText editTextName, editTextFamily,editTextCode;
    Button button;
    String invitationCode="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);
        // API
        apiInterface = Api.getAPI().create(ApiInterface.class);

        //راست چین 
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        init();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextName.getText().toString().equals("")) {
                    editTextName.setError("نام نمی\u200Cتواند خالی باشد!");
                } else if (editTextName.getText().toString().length() > 31) {
                    editTextName.setError("نام نمی\u200Cتواند بیشتر از 30 کاراکتر باشد!");

                } else
                    register(editTextName.getText().toString(), editTextFamily.getText().toString(),editTextCode.getText().toString());
            }
        });

    }

    private void init() {
        editTextName = findViewById(R.id.name_editText);
        editTextFamily = findViewById(R.id.family_editText);
        editTextCode = findViewById(R.id.code_editText);
        button = findViewById(R.id.ok_button);
    }

    private void register(String name, String family,String invitationCode) {
        Call<Register> posts = apiInterface.register(token, Integer.parseInt(userId),
                Build.VERSION.SDK_INT,Build.MODEL, name, family, Build.BRAND,invitationCode);
        posts.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                Register register = response.body();
                if (register != null) {
                    switch (register.getResponse()) {
                        case 1:
                            Tools.sharedPreference(getApplicationContext(), "Yes",
                                    register.getToken(), register.getId());
                            break;
                        case 0:
                            break;
                        case 304:
                            notDo("اشکال دیتابیس");
                            break;
                        case -3:
                            notDo("کاربر وجود دارد");
                            break;
                        case -1:
                            notDo("توکن اشتباه");
                            break;
                        case 303:
                            notDo("فیلداشتباه");
                            break;
                    }

                }
                if (register != null & register.getToken() != null & register.getId() != null) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"مشکلی در ارتباط پیش آمده", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void notDo(String s) {
        Toast.makeText(getApplicationContext(), "از سمت سرور اطلاعات ناقصی ارسال شده" + s, Toast.LENGTH_LONG).show();
        finish();
    }


}
