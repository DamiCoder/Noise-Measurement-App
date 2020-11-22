package dcwiek.noisemeasurmentapp.ui.fragment.archive.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.domain.model.Probe

class ProbeViewHolder(val context: Context, inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_archivelist, parent, false)) {

    private val date: TextView = itemView.findViewById(R.id.textview_archive_date)
    private val result: TextView = itemView.findViewById(R.id.textview_archive_result)
    private val button: Button = itemView.findViewById(R.id.button_archive_description)
    private val backgroundLayout: ConstraintLayout = itemView.findViewById(R.id.constraintlayout_archive)
    val view: View = this.itemView

    fun bind(probe: Probe) {
        date.text  = probe.getCreateDateFormatted()
        result.text = probe.healthHazard.label
        val drawableResourceId: Int = view.resources.getIdentifier(probe.healthHazard.backgroundName, "drawable", view.context.packageName)
        backgroundLayout.background = ResourcesCompat.getDrawable(view.resources, drawableResourceId, null)
    }

    fun setButtonClickAction(buttonClickListener: View.OnClickListener) {
        button.setOnClickListener(buttonClickListener)
    }
}