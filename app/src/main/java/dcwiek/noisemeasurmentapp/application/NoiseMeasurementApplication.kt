package dcwiek.noisemeasurmentapp.application

import android.app.Application
import dcwiek.noisemeasurmentapp.application.component.DaggerProbeRecorderComponent
import dcwiek.noisemeasurmentapp.application.component.DaggerStorageComponent
import dcwiek.noisemeasurmentapp.application.component.ProbeRecorderComponent
import dcwiek.noisemeasurmentapp.application.component.StorageComponent
import dcwiek.noisemeasurmentapp.application.module.ContextModule

class NoiseMeasurementApplication : Application() {
    private lateinit var probeRecorderComponent: ProbeRecorderComponent
    private lateinit var storageComponent: StorageComponent
    override fun onCreate() {
        super.onCreate()
        val contextModule = ContextModule(this)
        probeRecorderComponent = DaggerProbeRecorderComponent
            .builder()
            .contextModule(contextModule)
            .build()

        storageComponent = DaggerStorageComponent.builder()
            .contextModule(contextModule)
            .build()
    }

    fun getProbeRecorderComponent(): ProbeRecorderComponent {
        return probeRecorderComponent
    }

    fun getStorageComponent(): StorageComponent {
        return storageComponent;
    }
}