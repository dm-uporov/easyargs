package d.uporov.easyargsexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.udy18rus.easyargs.getArgOrNull
import kotlinx.android.synthetic.main.layout_details.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_details)
        getArgOrNull<SomeModel>()?.run { message.text = "Has argument" }
    }
}