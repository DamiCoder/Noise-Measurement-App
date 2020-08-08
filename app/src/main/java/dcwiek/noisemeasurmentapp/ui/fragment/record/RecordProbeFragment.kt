package dcwiek.noisemeasurmentapp.ui.fragment.record

import android.animation.LayoutTransition
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.application.NoiseMeasurementApplication
import dcwiek.noisemeasurmentapp.service.media.ProbeRecorder
import dcwiek.noisemeasurmentapp.ui.constants.FragmentKeys
import dcwiek.noisemeasurmentapp.ui.fragment.common.fragment.ExtendedFragment
import kotlinx.android.synthetic.main.fragment_customprobe.*

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

                    //TODO: pass here value and make here necesarry calculations
                    createSuccessFragment()
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

    private fun createSuccessFragment() {
        replaceFragment(
            R.id.framelayout_main, RecordProbeSuccessFragment.newInstance(),
            FragmentKeys.CUSTOM_PROBE_SUCCESS_FRAGMENT)

    }

    private fun createFailureFragment() {
        replaceFragment(
            R.id.framelayout_main, RecordProbeFailureFragment.newInstance(),
            FragmentKeys.CUSTOM_PROBE_FAILURE_FRAGMENT)
    }

}