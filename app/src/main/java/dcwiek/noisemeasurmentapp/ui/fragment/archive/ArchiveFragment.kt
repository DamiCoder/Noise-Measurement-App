package dcwiek.noisemeasurmentapp.ui.fragment.archive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.ui.fragment.archive.adapter.ProbesAdapter
import dcwiek.noisemeasurmentapp.ui.fragment.common.ExtendedFragment
import kotlinx.android.synthetic.main.fragment_archive.*

class ArchiveFragment : ExtendedFragment() {

    private lateinit var archiveRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_archive, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        archiveRecyclerView = recyclerview_archive as RecyclerView
        archiveRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ProbesAdapter(context, dataStorage.archivedProbesData.value!!)
        }
    }

    companion object {
        fun newInstance() : ArchiveFragment = ArchiveFragment()
    }

}
