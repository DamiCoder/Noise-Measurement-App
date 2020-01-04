package dcwiek.noisemeasurmentapp.view

import android.animation.LayoutTransition
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import dcwiek.noisemeasurmentapp.R
import kotlinx.android.synthetic.main.fragment_customprobe.*


class CustomProbeFragment : Fragment() {


    companion object {
        fun newInstance() = CustomProbeFragment()
        private const val ALPHA_FADE_DURATION : Long = 1000L
        private const val ALPHA_START_VALUE : Float = 1f
        private const val ALPHA_END_VALUE : Float = 0.5f

    }

    private lateinit var viewModel: CustomProbeViewModel

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
        // TODO: Use the ViewModel
    }

    private fun recordButtonClickListener() {
        changeObjectsVisibility()
        var total = 0
        val progressBar = customprobe_progressbar
        progressBar.max = 20000
        progressBar.progress = total
        val timePeriod = 1 * 20 * 1000 // 20 seconds in milli seconds
        object : CountDownTimer(timePeriod.toLong(), 25) {
            override fun onTick(millisUntilFinished: Long) {
                total = (progressBar.max - millisUntilFinished).toInt()
                progressBar.progress = total
            }

            override fun onFinish() { // DO something when 1 minute is up
            }
        }.start()
    }

    private fun changeObjectsVisibility(){
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

}
