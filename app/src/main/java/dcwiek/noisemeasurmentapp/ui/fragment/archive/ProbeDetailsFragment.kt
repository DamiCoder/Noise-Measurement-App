package dcwiek.noisemeasurmentapp.ui.fragment.archive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.domain.constants.Regulation
import dcwiek.noisemeasurmentapp.domain.model.Probe
import dcwiek.noisemeasurmentapp.ui.fragment.common.fragment.ExtendedFragment
import kotlinx.android.synthetic.main.fragment_probedetails.view.*

class ProbeDetailsFragment(val probe: Probe): ExtendedFragment() {


    companion object {
        private const val EMPTY_RESULT = "-"
        fun newInstance(probe: Probe) = ProbeDetailsFragment(probe)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val regulationConst = Regulation.getByName(probe.place.regulation.name)
        val view = inflater.inflate(R.layout.fragment_probedetails, container, false)
        view.probedetails_resultdescview.text = probe.healthHazard.hazardDescription
        view.probedetails_locationview.text = if(!probe.location.isBlank()) probe.location else EMPTY_RESULT
        view.probedetails_datecreatedview.text = probe.getCreateDateFormatted()
        view.probedetails_commentview.text = if(!probe.comment.isBlank()) probe.comment else EMPTY_RESULT
        view.probedetails_placeview.text = probe.place.getNameFormatted()
        view.probedetails_ratingview.text = probe.userRating.toString()
        if(regulationConst.isPresent) {
            view.probedetails_regulationview.text = regulationConst.get().label
        }
        view.probedetails_resultview.text = probe.getResultFormatted()
        val brightColorId = resources.getIdentifier(probe.healthHazard.brightColorName, "color", context?.packageName)
        val darkColorId = resources.getIdentifier(probe.healthHazard.darkColorName, "color", context?.packageName)
        val brightColor = resources.getColor(brightColorId, context?.theme)
        val darkColor = resources.getColor(darkColorId, context?.theme)
        view.probedetails_resultview.setTextColor(darkColor)
        if(probe.standards.isNotEmpty()) {
            view.probedetails_resultlabelview.text = probe.standards[0].getTitleFormatted()
        }
        view.probedetails_resultlabelview.setTextColor(brightColor)
        view.probedetails_backbutton.setOnClickListener {
            loadMainMenuFragment()
        }
        return view
    }
}