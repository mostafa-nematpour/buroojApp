package ir.burooj.basij;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import ir.burooj.basij.apiClass.CompleteProfile;
import ir.burooj.basij.apiClass.GetUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AccountFragment extends Fragment {


    View view;
    private TextView textViewName, textViewFamily, textViewNumber, textViewTarikh,
            textViewKSetting, textViewId, textTotalInvitation, textViewInvite;
    private CardView buttonTakmil;

    private ImageView imageViewBackground, imageViewLogo;
    SharedPreferences shPref;
    private static final String MyPref = "BuroojPrefers1";
    private static final String tokenName = "tokenNameKey";
    private static final String userIdName = "userIdNameKey";
    private static final String haveAccountName = "haveAccount";
    private String token = "", userId = "", haveAccount = "";


    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);

        textViewName = view.findViewById(R.id.textView5);
        textViewFamily = view.findViewById(R.id.textView6);
        textViewNumber = view.findViewById(R.id.textView7);
        textViewTarikh = view.findViewById(R.id.textView8);
        imageViewBackground = view.findViewById(R.id.imageView4);
        imageViewLogo = view.findViewById(R.id.imageView2);
        textViewKSetting = view.findViewById(R.id.khoroj);
        textViewId = view.findViewById(R.id.textView12);
        buttonTakmil = view.findViewById(R.id.button4);
        textTotalInvitation = view.findViewById(R.id.textTotalInvitation);
        textViewInvite = view.findViewById(R.id.invite);


        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onStart() {
        super.onStart();
        laodshar();


          /*
                    اندازه گیری صفحه
                  */
        try {
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;

            ViewGroup.LayoutParams params = imageViewBackground.getLayoutParams();
            params.height = (3 * height) / 10;
            imageViewBackground.setLayoutParams(params);

            ViewGroup.LayoutParams params1 = imageViewLogo.getLayoutParams();
            params1.height = height / 8;

            imageViewLogo.setLayoutParams(params1);


        } catch (Exception e) {
            e.printStackTrace();
        }
                   /*
                     اندازه گیری صفحه
        */

        textViewId.setText("شناسه\u200cکاربری: " + userId);
        textViewKSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), SettingActivity.class);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(), textViewKSetting,
                        ViewCompat.getTransitionName(textViewKSetting)
                );
                startActivity(intent);
                getActivity().finish();

            }
        });


        Call<GetUser> getUserCall = MainActivity.apiInterface.getUser(userId, token);
        getUserCall.enqueue(new Callback<GetUser>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<GetUser> call, Response<GetUser> response) {


                final GetUser user = response.body();
                if (user != null && user.getResponse() == 1) {
                    textViewName.setText("نام: " + user.getName());
                    if (user.getTotalInvitation() != null && !user.getTotalInvitation().equals("0")) {
                        textTotalInvitation.setText("تعداد دعوت\u200Cها: " + user.getTotalInvitation());
                    } else {
                        textTotalInvitation.setVisibility(View.GONE);
                    }
                    if (user.getLastname() == null || user.getLastname().equals("")) {
                        textViewFamily.setVisibility(View.GONE);
                    }
                    textViewFamily.setText("نام\u200Cخانوادگی: " + user.getLastname());
                    textViewNumber.setText("شماره تلفن: " + user.getPhone_number());
                    textViewTarikh.setText("تاریخ عضویت: " + user.getRegister_date());
                    if (!user.isComplete()) {
                        buttonTakmil.setVisibility(View.VISIBLE);
                        buttonTakmil.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                chang("تکمیل\u200Cحساب");
                            }
                        });
                    } else {
                        textViewInvite.setVisibility(View.VISIBLE);
                        textViewInvite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                shareIntent.setType("text/plain");
                                shareIntent.putExtra(Intent.EXTRA_TEXT,
                                        "برای نصب بروج از لینک: \n " +
                                                "https://burooj.ir/api/file/burooj.apk \n اقدام کنید. \n\n" +
                                                "کدمعرف:\n" + user.getInvitationCode());
                                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "The title");
                                startActivity(Intent.createChooser(shareIntent, "دعوت با:"));
                            }
                        });
                    }

                }
            }

            @Override
            public void onFailure(Call<GetUser> call, Throwable t) {
                textViewName.setText("مشکلی در اتصال پیش آمده");
            }
        });
    }

    private void chang(String s) {
        Intent intent = new Intent(getActivity(), CompleteProfileActivity.class);
        intent.putExtra("name12", s);
        startActivity(intent);
    }


    private void laodshar() {
        shPref = getActivity().getSharedPreferences(MyPref, Context.MODE_PRIVATE);

        if (shPref.contains(tokenName)) {
            token = shPref.getString(tokenName, null);
        } else {
            logOut(view.getContext());
            getActivity().finish();

        }
        if (shPref.contains(userIdName)) {
            userId = shPref.getString(userIdName, null);
        } else {
            logOut(view.getContext());
            getActivity().finish();
        }
        if (shPref.contains(haveAccountName)) {
            haveAccount = shPref.getString(haveAccountName, null);
        } else {
            logOut(view.getContext());
            getActivity().finish();
        }

    }

    void logOut(Context context) {
        Tools.sharedPreference(context, "", "", "");
        Intent intent = new Intent(context, SplashScreen.class);
        startActivity(intent);
    }


}
