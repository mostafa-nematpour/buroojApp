package ir.burooj.basij.groups

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.burooj.basij.BAppCompatActivity
import ir.burooj.basij.MainActivity
import ir.burooj.basij.PostFragment
import ir.burooj.basij.R

class ListShowActivity : BAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_show)
        val extras = intent.extras
        var i = "0"
        var name = ""
        if (null != extras) {
            i = extras.getString("id", "-2")
            name = extras.getString("name", getString(R.string.app_name))
        } else {
            finish()
        }
        val postFragment = PostFragment(i, name)
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.frg_holder, postFragment)
        ft.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, 0)
    }
}