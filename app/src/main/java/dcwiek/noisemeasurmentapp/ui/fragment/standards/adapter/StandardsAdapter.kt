package dcwiek.noisemeasurmentapp.ui.fragment.standards.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dcwiek.noisemeasurmentapp.domain.model.Standard

class StandardsAdapter(private val list: List<Standard>): RecyclerView.Adapter<StandardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return StandardViewHolder(parent.context, inflater, parent)
    }

    override fun onBindViewHolder(holder: StandardViewHolder, position: Int) {
        val standard: Standard = list[position]
        holder.bind(standard)
    }

    override fun getItemCount(): Int = list.size
}