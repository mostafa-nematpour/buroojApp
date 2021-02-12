package ir.burooj.basij;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import ir.burooj.basij.more.LoanModel;
import ir.burooj.basij.more.ZoomOutPageTransformer;

public class VameActivity extends BAppCompatActivity {
    List<LoanModel> loanModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vame);
        ViewPager2 viewPager = findViewById(R.id.viewPager2);
        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager.setPageTransformer(new ZoomOutPageTransformer());
        loanModels.add(new LoanModel("نحوه گرفتن وام دانشجویی",
                getString(R.string.v0)
                , "برای ادامه صفحه را به بالا بکشید", "#FF1744"));

        loanModels.add(new LoanModel("مرحله اول",
                getString(R.string.v1)
                , "صفحه (1 / 5)", "#D500F9"));
        loanModels.add(new LoanModel("مرحله دوم",
                getString(R.string.v2)
                , "صفحه (2 / 5)", "#F50057"));

        loanModels.add(new LoanModel("مرحله سوم",
                getString(R.string.v3)
                , "صفحه (3 / 5)", "#651FFF"));

        loanModels.add(new LoanModel("مرحله چهارم",
                getString(R.string.v4)
                , "صفحه (4 / 5)", "#3D5AFE"));
        loanModels.add(new LoanModel("بهتره بدونید",
                getString(R.string.v5)
                , "صفحه (5 / 5)", "#2979FF"));


        viewPager.setAdapter(new ViewPagerAdapter(this, loanModels));
    }

}
