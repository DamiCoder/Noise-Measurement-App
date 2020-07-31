package dcwiek.noisemeasurmentapp.ui.fragment.probe

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.application.NoiseMeasurementApplication
import dcwiek.noisemeasurmentapp.service.NotificationService
import dcwiek.noisemeasurmentapp.ui.constants.FragmentKeys
import dcwiek.noisemeasurmentapp.ui.fragment.common.fragment.ExtendedFragment
import kotlinx.android.synthetic.main.fragment_customprobefailure.*


class CustomProbeFailureFragment : ExtendedFragment() {

    companion object {
        fun newInstance() = CustomProbeFailureFragment()
    }

    private lateinit var viewModel: CustomProbeFailureViewModel
    private lateinit var notificationService: NotificationService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val notificationComponent = (requireContext().applicationContext as NoiseMeasurementApplication)
            .getNotificationComponent()
        notificationService = notificationComponent.getNotificationService()
        return inflater.inflate(R.layout.fragment_customprobefailure, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CustomProbeFailureViewModel::class.java)

        val vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        notificationService.vibrateAndPlaySound(vibrator)

        button_customprobefailure_continue.setOnClickListener{
            replaceFragment(R.id.framelayout_main, ChooseProbeFragment.newInstance(), FragmentKeys.CHOOSE_PROBE_FRAGMENT)
        }
    }

}
