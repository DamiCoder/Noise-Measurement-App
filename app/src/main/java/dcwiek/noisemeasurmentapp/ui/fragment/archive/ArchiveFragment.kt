package dcwiek.noisemeasurmentapp.ui.fragment.archive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.ui.fragment.ExtendedFragment

class ArchiveFragment : ExtendedFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_archive, container, false)
    }

    companion object {
        fun newInstance() : ArchiveFragment = ArchiveFragment()
    }

}
