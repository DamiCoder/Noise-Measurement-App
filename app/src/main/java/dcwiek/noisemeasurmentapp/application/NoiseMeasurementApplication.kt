package dcwiek.noisemeasurmentapp.application

import android.app.Application
import dcwiek.noisemeasurmentapp.component.DaggerProbeRecorderComponent
import dcwiek.noisemeasurmentapp.component.ProbeRecorderComponent
import dcwiek.noisemeasurmentapp.service.ProbeRecorder

class NoiseMeasurementApplication : Application() {
    private lateinit var probeRecorderComponent: ProbeRecorderComponent
    override fun onCreate() {
        super.onCreate()
        probeRecorderComponent = DaggerProbeRecorderComponent
            .builder()
            .probeRecorder(ProbeRecorder(applicationContext))
            .build()
    }

    fun getMyComponent(): ProbeRecorderComponent {
        return probeRecorderComponent
    }
}