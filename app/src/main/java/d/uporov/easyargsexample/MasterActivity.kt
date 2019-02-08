package d.uporov.easyargsexample

import android.support.v7.app.AppCompatActivity
import d.uporov.easyargs.asArg
import d.uporov.easyargs.start
import java.io.Serializable

class MasterActivity : AppCompatActivity() {

    fun itemChosen(model: SomeModel) {
        start<DetailsActivity>(model.asArg())
    }
}

class SomeModel: Serializable