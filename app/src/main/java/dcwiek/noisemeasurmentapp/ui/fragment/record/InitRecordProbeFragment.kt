package dcwiek.noisemeasurmentapp.ui.fragment.record

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.domain.constants.Place
import dcwiek.noisemeasurmentapp.domain.constants.Regulation
import dcwiek.noisemeasurmentapp.ui.fragment.common.fragment.ExtendedFragment
import dcwiek.noisemeasurmentapp.ui.fragment.common.spinner.HintListAdapter
import dcwiek.noisemeasurmentapp.ui.fragment.common.spinner.SpinnerItem
import kotlinx.android.synthetic.main.fragment_initrecordprobe.*
import kotlinx.android.synthetic.main.fragment_initrecordprobe.view.*
import java.util.*
import java.util.stream.Collectors


class InitRecordProbeFragment: ExtendedFragment() {

    companion object {
        fun newInstance() : InitRecordProbeFragment = InitRecordProbeFragment()
        const val TAG = "InitRecordProbeFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_initrecordprobe, container, false)
        val spinnerValues = LinkedList<SpinnerItem>()

        spinnerValues.addAll(Regulation.getLabels().stream().map{regulation -> SpinnerItem.createValue(regulation)}.collect(Collectors.toList()))
        spinnerValues.add(SpinnerItem.createHint("Wybierz regulację"))

        view.initrecordprobe_standardtypespinner.adapter = HintListAdapter(requireContext(), R.layout.spinner_item,
            spinnerValues)
        view.initrecordprobe_standardtypespinner.setSelection(spinnerValues.size - 1)

        view.initrecordprobe_standardtypespinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position) as SpinnerItem
                Log.d(TAG,  selectedItem.toString())
                if (!selectedItem.isHint) {
                    val selectedRegulation = Regulation.getByLabel(selectedItem.value)
                    if(selectedRegulation.isPresent) {
                        render2ndStep(selectedRegulation.get())
                        initrecordprobe_standardtypespinner.isEnabled = false
                        initrecordprobe_standardtypespinner.isClickable = false
                    } else {
                        //TODO; handle exception with popup
                    }
                }
            }
        }
        return view
    }

    private fun render2ndStep(regulation: Regulation) {
        val placeSpinnerLabel = layoutInflater.inflate(R.layout.spinner_labeltextview, null) as TextView

        placeSpinnerLabel.text = "2. Wybierz aktualne miejsce przebywania"
        placeSpinnerLabel.textSize = 18F

        val spinnerValues = LinkedList<SpinnerItem>()

        if(regulation == Regulation.LAW) {
            spinnerValues.addAll(Place.getAllByRegulation(Regulation.LAW)
                .stream()
                .map { SpinnerItem.createValue(it.label) }
                .collect(Collectors.toList()))
        } else {
            spinnerValues.addAll(Place.getAllByRegulation(Regulation.SCIENTIFIC)
                .stream()
                .map { SpinnerItem.createValue(it.label) }
                .collect(Collectors.toList()))
        }

        spinnerValues.add(SpinnerItem.createHint("Wybierz miejsce"))
        val hintListAdapter = HintListAdapter(requireContext(), R.layout.spinner_item, spinnerValues)

        val placeSpinner = layoutInflater.inflate(R.layout.spinner, null) as Spinner
        placeSpinner.adapter = hintListAdapter
        placeSpinner.setSelection(spinnerValues.size - 1)

        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        fragment_initrecordprobe_layout.addView(placeSpinnerLabel)
        fragment_initrecordprobe_layout.addView(placeSpinner)
        fragment_initrecordprobe_layout.startAnimation(animation)

        placeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position) as SpinnerItem
                Log.d(TAG, selectedItem.toString())
                if (!selectedItem.isHint) {
                    placeSpinner.isEnabled = false
                    placeSpinner.isClickable = false
                    val selectedPlaceOptional = Place.getByLabel(selectedItem.value)
                    if(selectedPlaceOptional.isPresent) {
                        dataStorage.currentlySelectedPlace = selectedPlaceOptional.get()
                        renderRecordingView()
                    } else {
                        //TODO exception popup appears. It takes as constructor parameter some ExceptionHandler interface and does action given in interface on close/OK button pressed
                        //TODO it should also contain error message
                    }
                }
            }
        }
    }

    private fun renderRecordingView() {
        Log.d(TAG, "Rendering recording view for place ${dataStorage.currentlySelectedPlace}")
        val nextViewButton = layoutInflater.inflate(R.layout.button, null) as Button
        nextViewButton.text = "Dalej"

        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(0, 30.toPx(), 0, 0)
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL
        nextViewButton.layoutParams = layoutParams

        fragment_initrecordprobe_layout.addView(nextViewButton)
        nextViewButton.setOnClickListener { view ->  }
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}