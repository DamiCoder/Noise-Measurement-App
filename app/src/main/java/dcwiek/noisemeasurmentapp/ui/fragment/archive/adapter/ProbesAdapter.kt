package dcwiek.noisemeasurmentapp.ui.fragment.archive.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dcwiek.noisemeasurmentapp.domain.model.Probe

class ProbesAdapter(private val list: List<Probe>, private val clickListener: ArchiveItemClickListener):
    RecyclerView.Adapter<ProbeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProbeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ProbeViewHolder(parent.context, inflater, parent)
    }

    override fun onBindViewHolder(holder: ProbeViewHolder, position: Int) {
        val probe: Probe = list[position]
        holder.bind(probe)
        holder.setButtonClickAction(View.OnClickListener { clickListener.switchToProbeDetailsFragment(probe)})
    }

    override fun getItemCount(): Int = list.size
}