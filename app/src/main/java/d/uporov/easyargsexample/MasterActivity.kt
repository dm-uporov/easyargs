package d.uporov.easyargsexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import d.uporov.easyargs.asArg
import d.uporov.easyargs.newInstance
import d.uporov.easyargs.start
import kotlinx.android.synthetic.main.activity_master.*

class MasterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master)
        startActivityButton.setOnClickListener(::onStartActivityClicked)
        startFragmentButton.setOnClickListener(::onStartFragmentClicked)
    }

    private fun onStartActivityClicked(v: View) {
        if (checkBoxWithArgument.isChecked) {
            start<DetailsActivity>(SomeModel().asArg())
        } else {
            start<DetailsActivity>()
        }
    }

    private fun onStartFragmentClicked(v: View) {
        val fragment: DetailsFragment = if (checkBoxWithArgument.isChecked) {
            newInstance(SomeModel().asArg())
        } else {
            newInstance()
        }

        supportFragmentManager.beginTransaction()
            .add(android.R.id.content, fragment)
            .addToBackStack(null)
            .commit()
    }
}