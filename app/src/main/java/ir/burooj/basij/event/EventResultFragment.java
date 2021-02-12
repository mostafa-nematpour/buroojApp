package ir.burooj.basij.event;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import ir.burooj.basij.MainActivity;
import ir.burooj.basij.R;
import ir.burooj.basij.apiClass.Event;
import ir.burooj.basij.apiClass.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventResultFragment extends BottomSheetDialogFragment {
    private Event event;
    private Response response;
    private TextView textView;
    View view;
    LottieAnimationView imageView;
    private Button button;

    public EventResultFragment() {
    }

    public EventResultFragment(Event event, Response response) {
        this.event = event;
        this.response = response;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_event_result, container, false);
        textView = view.findViewById(R.id.text_event_result);
        imageView = view.findViewById(R.id.ic_check);
        button = view.findViewById(R.id.b423);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        String s = "خطا!";
        switch (response.getResponse()) {
            case "1":
                if (!response.isFree() && response.getPayment_link() != null && !response.getPayment_link().isEmpty()) {


                    try {
                        imageView.setAnimation(R.raw.link);
                    } catch (Exception ignored) {
                    }
                    s=event.getPrice();
                    button.setText("ارجاع به درگاه پرداخت");
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(response.getPayment_link()));
                            startActivity(intent);
                        }
                    });

                } else {
                    imageView.setAnimation(R.raw.check);
                    s = "ثبت نام شما با موفقیت انجام شد.";
                }

                break;
            case "-1":
                //توکن و یوز اشتباه
                Toast.makeText(getContext(), "خطا", Toast.LENGTH_LONG).show();
            case "303":
                // ورودی اشتباه
            case "-5":
                // ثبت نام نکرده
                // dismiss();
                break;
            case "-4":
                //ثبت نام تکمیل نشده
                s = "ثبت نام تکمیل نشده است.";
                imageView.setAnimation(R.raw.account);
                break;

            case "-3":
                // بلیط تموم شده
                s = "بیلت تمام شده";
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_message_error));
                break;
            case "-2":
                // رویداد وجود نداره
                Toast.makeText(view.getContext(), "رویداد وجود ندارد.", Toast.LENGTH_LONG).show();
                break;
            case "-6":
                //
                imageView.setAnimation(R.raw.timeout);
                s = "مدت زمان ثبت\u200cنام تمام شده.";
                break;
        }
        textView.setText(s);

        return view;
    }
}
