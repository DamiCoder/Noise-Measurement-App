package dcwiek.noisemeasurmentapp.ui.fragment.archive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.domain.model.Probe
import dcwiek.noisemeasurmentapp.ui.fragment.common.ExtendedFragment

class ProbeDetailsFragment(val probe: Probe): ExtendedFragment() {

    companion object {
        fun newInstance() = EmptyArchiveFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_probedetails, container, false)
    }
}