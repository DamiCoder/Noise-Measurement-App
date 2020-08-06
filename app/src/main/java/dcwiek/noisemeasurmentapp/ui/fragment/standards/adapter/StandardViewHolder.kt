package dcwiek.noisemeasurmentapp.ui.fragment.standards.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.domain.model.Standard

class StandardViewHolder(val context: Context, inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_standardlist, parent, false)) {

    private val title: TextView = itemView.findViewById(R.id.standarditem_title)
    private val description: TextView = itemView.findViewById(R.id.standarditem_description)
    private val acceptedValues: TextView = itemView.findViewById(R.id.standarditem_acceptedvaluestext)
    private val riskValues: TextView = itemView.findViewById(R.id.standarditem_riskvaluestext)
    private val threatValues: TextView = itemView.findViewById(R.id.standarditem_threatvalues)
    private val place: TextView = itemView.findViewById(R.id.standarditem_placetext)
    val view: View = this.itemView

    fun bind(standard: Standard) {
        title.text = standard.getTitleFormatted()
        description.text = standard.description
        acceptedValues.text = "< ${standard.minValue} dB"
        riskValues.text = ">= ${standard.minValue} dB"
        threatValues.text = ">= ${standard.maxValue} dB"
        place.text = standard.place.getNameFormatted()
    }
}