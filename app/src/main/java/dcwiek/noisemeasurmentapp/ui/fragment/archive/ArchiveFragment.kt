package dcwiek.noisemeasurmentapp.ui.fragment.archive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.ui.fragment.ExtendedFragment

class ArchiveFragment : ExtendedFragment() {

    companion object {
        fun newInstance() = ArchiveFragment()
    }

    private lateinit var viewModel: ArchiveViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (dataStorage.archivedProbesData.isEmpty()) {
            return inflater.inflate(R.layout.fragment_archiveempty, container, false)
        } else {
            //TODO: IMPLEMENT FRAGMENT WITH DATA
            return inflater.inflate(R.layout.fragment_archiveempty, container, false)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ArchiveViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
