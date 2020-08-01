package dcwiek.noisemeasurmentapp.ui.fragment.record

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.application.NoiseMeasurementApplication
import dcwiek.noisemeasurmentapp.service.NotificationService
import dcwiek.noisemeasurmentapp.ui.fragment.common.fragment.ExtendedFragment
import kotlinx.android.synthetic.main.fragment_customprobesuccess.*


class RecordProbeSuccessFragment : ExtendedFragment() {

    companion object {
        fun newInstance() = RecordProbeSuccessFragment()
    }

    private lateinit var notificationService: NotificationService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val notificationComponent = (requireContext().applicationContext as NoiseMeasurementApplication)
            .getNotificationComponent()
        notificationService = notificationComponent.getNotificationService()
        return inflater.inflate(R.layout.fragment_customprobesuccess, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        notificationService.vibrateAndPlaySound(vibrator)

        context?.let { context ->  sharedPreferencesService.putSharedPreference(
            context.getString(R.string.preference_key_choosen_probe),
            context.getString(R.string.preference_value_use_custom_probe))
        }

        button_customprobesuccess_continue.setOnClickListener{
            //TODO: extract probe + send to server + add it to list
            loadMainMenuFragment()

        }
    }

}
