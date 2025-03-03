package dcwiek.noisemeasurmentapp.ui.fragment.archive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.domain.model.Probe
import dcwiek.noisemeasurmentapp.ui.constants.FragmentKeys
import dcwiek.noisemeasurmentapp.ui.fragment.archive.adapter.ArchiveItemClickListener
import dcwiek.noisemeasurmentapp.ui.fragment.archive.adapter.ProbesAdapter
import dcwiek.noisemeasurmentapp.ui.fragment.common.fragment.ExtendedFragment
import kotlinx.android.synthetic.main.fragment_archive.*

class ArchiveFragment: ExtendedFragment(), ArchiveItemClickListener {

    companion object {
        fun newInstance() : ArchiveFragment = ArchiveFragment()
    }

    private lateinit var archiveRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_archive, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        archiveRecyclerView = recyclerview_archive as RecyclerView
        archiveRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ProbesAdapter(dataStorage.archivedProbes.value!!, this@ArchiveFragment)
        }
    }

    override fun switchToProbeDetailsFragment(probe: Probe) {
        replaceFragment(R.id.framelayout_main, ProbeDetailsFragment.newInstance(probe), FragmentKeys.PROBE_DETAILS_FRAGMENT)
    }

}
