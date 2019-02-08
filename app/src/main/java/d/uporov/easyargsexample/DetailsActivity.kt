package d.uporov.easyargsexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import d.uporov.easyargs.getArg

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val model: SomeModel = intent.getArg()
    }
}