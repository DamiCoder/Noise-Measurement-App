package dcwiek.noisemeasurmentapp.ui.fragment.record

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.application.NoiseMeasurementApplication
import dcwiek.noisemeasurmentapp.domain.model.Probe
import dcwiek.noisemeasurmentapp.service.NotificationService
import dcwiek.noisemeasurmentapp.ui.fragment.common.fragment.ExtendedFragment
import kotlinx.android.synthetic.main.fragment_customprobesuccess.*
import java.time.LocalDateTime


class RecordProbeSuccessFragment(private val result: Int) : ExtendedFragment() {

    companion object {
        fun newInstance(result: Int) = RecordProbeSuccessFragment(result)
        private val TAG = RecordProbeSuccessFragment::class.java.simpleName
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

        button_customprobesuccess_continue.setOnClickListener{

            if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fusedLocationClient.lastLocation.addOnCompleteListener{
                    val lastLocation: Location? = it.result
                    Log.i(TAG, "lastLocation.task.isSuccessful: ${it.isSuccessful}")
                    val location =
                        if (it.isSuccessful) {
                            if (lastLocation != null) {
                                "${lastLocation.longitude};${lastLocation.latitude}"
                            } else {
                                ""
                            }
                        } else {
                            ""
                        }
                    val probe = createProbe(location)
                    loadResultFragment(probe)
                }
            } else {
                val probe = createProbe("")
                loadResultFragment(probe)
            }
        }
    }

    private fun createProbe(location: String): Probe {
        val place = dataStorage.currentlySelectedPlace
        val standards = standardService.getHazardousStandards(result, place)
        val hazard = standardService.determineHealthHazard(result, place)
        val probe = Probe(0, location, place, standards, hazard, result, "", null, LocalDateTime.now())
        return probe
    }

    private fun loadResultFragment(probe: Probe) {
        replaceFragment(R.id.framelayout_main, ResultFragment.newInstance(probe))
    }
}
