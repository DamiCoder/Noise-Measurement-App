package dcwiek.noisemeasurmentapp.ui.fragment.record

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.RadioButton
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.forEach
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.domain.constants.Regulation
import dcwiek.noisemeasurmentapp.domain.model.Probe
import dcwiek.noisemeasurmentapp.ui.fragment.common.fragment.ExtendedFragment
import kotlinx.android.synthetic.main.fragment_result.view.*
import kotlinx.android.synthetic.main.popup_comment.view.*
import kotlinx.android.synthetic.main.popup_rating.view.*
import java.net.URL

class ResultFragment(private val probe: Probe) : ExtendedFragment() {

    companion object {
        fun newInstance(probe: Probe) = ResultFragment(probe)
        const val EMPTY_RESULT: String = "-"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val regulationConst = Regulation.getByName(probe.place.regulation.name)
        view.result_resultdescview.text = probe.healthHazard.hazardDescription
        view.result_locationview.text = if(!probe.location.isBlank()) probe.location else EMPTY_RESULT
        view.result_commentview.text = if(!probe.comment.isBlank()) probe.comment else EMPTY_RESULT
        view.result_placeview.text = probe.place.getNameFormatted()
        view.result_ratingview.text = if (probe.userRating != null) { probe.userRating.toString() } else { EMPTY_RESULT }
        if (regulationConst.isPresent) {
            view.result_regulationview.text = regulationConst.get().label
        }
        view.result_resultview.text = probe.getResultFormatted()
        val brightColorId = resources.getIdentifier(probe.healthHazard.brightColorName, "color", context?.packageName)
        val darkColorId = resources.getIdentifier(probe.healthHazard.darkColorName, "color", context?.packageName)
        val brightColor = resources.getColor(brightColorId, context?.theme)
        val darkColor = resources.getColor(darkColorId, context?.theme)
        view.result_resultview.setTextColor(darkColor)
        if(probe.standards.isNotEmpty()) {
            view.result_resultlabelview.text = probe.standards[0].getTitleFormatted()
        }
        view.result_resultlabelview.setTextColor(brightColor)

        view.result_addcommentbutton.setOnClickListener {
            createCommentPopup(requireContext(), this@ResultFragment.requireView())
        }

        view.result_addratingbutton.setOnClickListener {
            createRatingPopup(requireContext(), this@ResultFragment.requireView())
        }

        view.result_continuebutton.setOnClickListener {
            UploadProbeAsyncTask(this, probe).execute()
        }

    }

    private class UploadProbeAsyncTask(val resultFragment: ResultFragment, val probe: Probe) :
        AsyncTask<URL?, Int?, Unit>() {

        override fun doInBackground(vararg urls: URL?) {
            val probes = resultFragment.probeService.uploadProbeToRemoteServer(probe)
            probes.ifPresent { resultFragment.dataStorage.archivedProbes.value?.add(probe) }
        }

        override fun onProgressUpdate(vararg progress: Int?) {
        }

        override fun onPostExecute(result: Unit) {
            resultFragment.loadMainMenuFragment()
        }
    }

    private fun createCommentPopup(context: Context, parentView: View) {
        val popupView: View = LayoutInflater.from(context).inflate(R.layout.popup_comment, null)

        val focusable = true
        val popupWindow = PopupWindow(popupView, 350.toPx(), 250.toPx(), focusable)
        popupWindow.animationStyle = R.style.Animation
        popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0)
        popupView.popup_comment_okbutton.setOnClickListener {
            probe.comment = popupView.popup_comment_comment.text.toString()
            parentView.result_commentview.text = probe.comment
            popupWindow.dismiss()
        }
    }


    private fun createRatingPopup(context: Context, parentView: View) {
        val popupView: View = LayoutInflater.from(context).inflate(R.layout.popup_rating, null)

        val focusable = true
        val popupWindow = PopupWindow(popupView, 350.toPx(), 250.toPx(), focusable)
        popupWindow.animationStyle = R.style.Animation
        popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0)
        popupView.popup_rating_okbutton.setOnClickListener {
            val checkedRadioButton = popupView.findViewById<RadioButton>(popupView.popup_rating_radiogroup.checkedRadioButtonId)
            probe.userRating = Integer.parseInt(checkedRadioButton.text.toString())
            parentView.result_ratingview.text = probe.userRating.toString()
            popupWindow.dismiss()
        }

        popupView.popup_rating_radiogroup.setOnCheckedChangeListener{ _, _ ->
            changeRadioButtonsAppearance(popupView)
        }
    }

    private fun changeRadioButtonsAppearance(radioButtonsParent: View) {
        radioButtonsParent.popup_rating_radiogroup.forEach {
            val radioButton = it as RadioButton
            if (!radioButton.isChecked) {
                radioButton.background = ResourcesCompat.getDrawable(resources, R.drawable.radio_button_unchecked, null)
                val typeface = resources.getFont(R.font.open_sans)
                radioButton.typeface = typeface
                radioButton.setTextColor(ResourcesCompat.getColor(resources, R.color.contraryBlue, null))
            } else {
                radioButton.background = ResourcesCompat.getDrawable(resources, R.drawable.radio_button_checked, null)
                val typeface = resources.getFont(R.font.open_sans)
                radioButton.typeface = typeface
                radioButton.setTextColor(Color.WHITE)
            }
        }
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}