package dcwiek.noisemeasurmentapp.ui.fragment.archive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.domain.constants.Result
import dcwiek.noisemeasurmentapp.domain.model.Probe
import dcwiek.noisemeasurmentapp.ui.constants.FragmentKeys
import dcwiek.noisemeasurmentapp.ui.fragment.common.ExtendedFragment
import dcwiek.noisemeasurmentapp.ui.fragment.menu.MainMenuFragment
import kotlinx.android.synthetic.main.fragment_probedetails.view.*

class ProbeDetailsFragment(val probe: Probe): ExtendedFragment() {

    private val EMPTY_RESULT = "-"

    companion object {
        fun newInstance(probe: Probe) = ProbeDetailsFragment(probe)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_probedetails, container, false)
        val result = Result.getByLabel(probe.standard.title)
        view.probedetails_locationview.text = if(!probe.comment.isBlank()) probe.comment else EMPTY_RESULT
        view.probedetails_datecreatedview.text = probe.getCreateDateFormatted()
        view.probedetails_locationview.text = if(!probe.location.isBlank()) probe.location else EMPTY_RESULT
        view.probedetails_placeview.text = probe.place.name
        view.probedetails_regulationview.text = probe.standard.regulation
        view.probedetails_resultview.text = probe.getResultFormatted()
        val brightColorId = resources.getIdentifier(result.brightColorName, "color", context?.packageName)
        val darkColorId = resources.getIdentifier(result.darkColorName, "color", context?.packageName)
        val brightColor = resources.getColor(brightColorId, context?.theme)
        val darkColor = resources.getColor(darkColorId, context?.theme)
        view.probedetails_resultview.setTextColor(darkColor)
        view.probedetails_resultlabelview.text = probe.standard.title
        view.probedetails_resultlabelview.setTextColor(brightColor)
        view.probedetails_resultdescview.text = probe.standard.description
        view.probedetails_backbutton.setOnClickListener {
            replaceFragment(R.id.framelayout_main, MainMenuFragment.getInstance(), FragmentKeys.MAIN_MENU_FRAGMENT)
            MainMenuFragment.getInstance().initializeArchiveView()
        }
        return view
    }
}