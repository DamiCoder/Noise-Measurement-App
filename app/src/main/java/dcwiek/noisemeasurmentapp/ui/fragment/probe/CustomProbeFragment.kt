package dcwiek.noisemeasurmentapp.ui.fragment.probe

import android.animation.LayoutTransition
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProviders
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.application.NoiseMeasurementApplication
import dcwiek.noisemeasurmentapp.service.media.ProbeRecorder
import dcwiek.noisemeasurmentapp.ui.constants.FragmentKeys
import dcwiek.noisemeasurmentapp.ui.fragment.ExtendedFragment
import dcwiek.noisemeasurmentapp.ui.fragment.menu.MainMenuFragment
import kotlinx.android.synthetic.main.fragment_customprobe.*


class CustomProbeFragment : ExtendedFragment() {

    private val TAG = CustomProbeFragment::class.java.name
    private lateinit var probeRecorder: ProbeRecorder
    private lateinit var viewModel: CustomProbeViewModel
    companion object {
        fun newInstance() = CustomProbeFragment()
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
        viewModel = ViewModelProviders.of(this).get(CustomProbeViewModel::class.java)
        customprobe_recordbutton.setOnClickListener {
            recordButtonClickListener()
        }
        customprobe_usedefaultprobetextview.setOnClickListener{
            defaultProbeTextViewClickListener()
        }
    }

    private fun recordButtonClickListener() {
        changeObjectsVisibility()
        var total = 0
        val progressBar = customprobe_progressbar
        progressBar.max = 20000
        progressBar.progress = total
        val timePeriod = 1 * 20 * 1000
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
                    createSuccessFragment()
                } catch (e: Exception) {
                    Log.e(TAG, e.message, e)
                    createFailureFragment()
                }
            }
        }.start()
    }

    private fun defaultProbeTextViewClickListener(){
        context?.let {
            sharedPreferencesService.putSharedPreference(
                it.getString(R.string.preference_key_choosen_probe),
                it.getString(R.string.preference_value_use_custom_probe)
            )
        }

        replaceFragment(R.id.framelayout_main, MainMenuFragment.newInstance(), FragmentKeys.MAIN_MENU_FRAGMENT)
    }

    private fun changeObjectsVisibility() {
        customprobe_usedefaultprobetextview.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
        customprobe_usedefaultprobetextview.visibility = View.GONE

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
        replaceFragment(R.id.framelayout_main, CustomProbeSuccessFragment.newInstance(),
            FragmentKeys.CUSTOM_PROBE_SUCCESS_FRAGMENT)

    }

    private fun createFailureFragment() {
        replaceFragment(R.id.framelayout_main, CustomProbeFailureFragment.newInstance(),
            FragmentKeys.CUSTOM_PROBE_FAILURE_FRAGMENT)
    }

}
