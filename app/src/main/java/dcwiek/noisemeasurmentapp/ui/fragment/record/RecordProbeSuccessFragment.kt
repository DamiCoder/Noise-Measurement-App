package dcwiek.noisemeasurmentapp.ui.fragment.record

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.application.NoiseMeasurementApplication
import dcwiek.noisemeasurmentapp.domain.model.Probe
import dcwiek.noisemeasurmentapp.service.NotificationService
import dcwiek.noisemeasurmentapp.ui.fragment.common.fragment.ExtendedFragment
import kotlinx.android.synthetic.main.fragment_customprobesuccess.*
import java.net.URL
import java.time.LocalDateTime


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

//        context?.let { context ->  sharedPreferencesService.putSharedPreference(
//            context.getString(R.string.preference_key_choosen_probe),
//            context.getString(R.string.preference_value_use_custom_probe))
//        }

        button_customprobesuccess_continue.setOnClickListener{

            val result = (20..110).shuffled().last()
            val place = dataStorage.currentlySelectedPlace
            val standards = standardService.getHazardousStandards(result, place)
            val hazard = standardService.determineHealthHazard(result, place)
            val probe = Probe(0, "", place, standards, hazard, result, "", 3, LocalDateTime.now())
            //TODO: extract probe + send to server + add it to list
            UploadProbeAsyncTask(this, probe).execute()

        }
    }

    private class UploadProbeAsyncTask(val recordProbeSuccessFragment: RecordProbeSuccessFragment, val probe: Probe) : AsyncTask<URL?, Int?, Unit>() {

        override fun doInBackground(vararg urls: URL?){
            val probes = recordProbeSuccessFragment.probeService.uploadProbeToRemoteServer(probe)
            probes.ifPresent { recordProbeSuccessFragment.dataStorage.archivedProbes.value?.add(probe) }
        }

        override fun onProgressUpdate(vararg progress: Int?) {
        }

        override fun onPostExecute(result: Unit) {
            recordProbeSuccessFragment.loadMainMenuFragment()
        }
    }

}
