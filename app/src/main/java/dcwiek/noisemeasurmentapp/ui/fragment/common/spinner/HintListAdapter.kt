package dcwiek.noisemeasurmentapp.ui.fragment.common.spinner

import android.content.Context
import android.widget.ArrayAdapter


class HintListAdapter(context: Context, resource: Int, objects: List<SpinnerItem>): ArrayAdapter<SpinnerItem>(context, resource, objects) {

    override fun getCount(): Int {
        return super.getCount() - 1
    }
}