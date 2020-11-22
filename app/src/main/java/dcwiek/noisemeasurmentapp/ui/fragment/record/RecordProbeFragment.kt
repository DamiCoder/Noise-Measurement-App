package dcwiek.noisemeasurmentapp.ui.fragment.record

import android.animation.LayoutTransition
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.PopupWindow
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.application.NoiseMeasurementApplication
import dcwiek.noisemeasurmentapp.service.media.ProbeRecorder
import dcwiek.noisemeasurmentapp.ui.constants.FragmentKeys
import dcwiek.noisemeasurmentapp.ui.fragment.common.fragment.ExtendedFragment
import kotlinx.android.synthetic.main.fragment_customprobe.*
import kotlinx.android.synthetic.main.popup_probeprocessing.view.*
import java.io.File
import java.net.URL
import java.util.*


class RecordProbeFragment: ExtendedFragment() {

    private val TAG = RecordProbeFragment::class.java.name
    private lateinit var probeRecorder: ProbeRecorder

    companion object {
        fun newInstance() = RecordProbeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val noiseMeasurementApplication = (requireContext().applicationContext as NoiseMeasurementApplication)
        probeRecorder = noiseMeasurementApplication
            .getProbeRecorderComponent()
            .getProbeRecorder()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_customprobe, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        customprobe_constraintlayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        customprobe_recordbutton.setOnClickListener {
            recordButtonClickListener()
        }
    }

    private fun recordButtonClickListener() {
        changeObjectsVisibility()
        var total = 0
        val progressBar = customprobe_progressbar
        progressBar.max = ProbeRecorder.RECORD_DURATION
        progressBar.progress = total
        val timePeriod = ProbeRecorder.RECORD_DURATION
        try {
            probeRecorder.startRecording()
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            createFailureFragment()
        }
        object : CountDownTimer(timePeriod.toLong(), 25) {
            override fun onTick(millisUntilFinished: Long) {
                total = (progressBar.max - millisUntilFinished).toInt()
                progressBar.progress = total
            }
            override fun onFinish() {
                try {
                    probeRecorder.stopRecording()
                    val popupWindow = createProcessingPopup(requireView())
                    ProcessProbeAsyncTask(this@RecordProbeFragment, probeRecorder.recordedProbe!!, ProbeRecorder.AMPLITUDE_REFERENCE_VALUE, popupWindow).execute()
                } catch (e: Exception) {
                    Log.e(TAG, e.message, e)
                    createFailureFragment()
                }
            }
        }.start()
    }

    private fun changeObjectsVisibility() {
        customprobe_recordbutton.isClickable = false
        customprobe_recordbutton.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
        customprobe_recordbutton.visibility = View.GONE

        customprobe_progressbar.visibility = View.VISIBLE
        customprobe_progressbar.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))

        customprobe_recordingtextview.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
        customprobe_recordingtextview.visibility = View.VISIBLE
        customprobe_recordingtextview.startAnimation(AnimationUtils.loadAnimation(context, R.anim.blinking))
    }

    private fun createSuccessFragment(result: Int) {
        replaceFragment(
            R.id.framelayout_main, RecordProbeSuccessFragment.newInstance(result),
            FragmentKeys.CUSTOM_PROBE_SUCCESS_FRAGMENT)

    }

    private fun createFailureFragment() {
        replaceFragment(
            R.id.framelayout_main, RecordProbeFailureFragment.newInstance(),
            FragmentKeys.CUSTOM_PROBE_FAILURE_FRAGMENT)
    }

    private class ProcessProbeAsyncTask(
        val recordProbeFragment: RecordProbeFragment,
        val probe: File,
        val amplitudeReferenceValue: Double,
        val popupWindow: PopupWindow) : AsyncTask<URL?, Int?, Optional<Double>>() {


        override fun doInBackground(vararg urls: URL?): Optional<Double> {
            return recordProbeFragment.probeService.processProbeOnRemoteServer(probe, amplitudeReferenceValue)
        }

        override fun onProgressUpdate(vararg progress: Int?) {
        }

        override fun onPostExecute(result: Optional<Double>) {
            popupWindow.dismiss()
            if (result.isPresent) {
                recordProbeFragment.createSuccessFragment(result.get().toInt())
            } else {
                recordProbeFragment.createFailureFragment()
            }
        }
    }

    fun createProcessingPopup(parentView: View): PopupWindow {
        val popupView: View = LayoutInflater.from(context).inflate(R.layout.popup_probeprocessing, null)
        val focusable = true
        val popupWindow = PopupWindow(popupView, 300.toPx(), 150.toPx(), focusable)
        val progressDrawable: Drawable = popupView.waiting_progressbar.progressDrawable.mutate()
        progressDrawable.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
        popupView.waiting_progressbar.progressDrawable = progressDrawable
        popupView.waiting_progressbar.progressTintList = ColorStateList.valueOf(Color.RED)
        popupWindow.animationStyle = R.style.Animation
        popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0)
        return popupWindow
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}