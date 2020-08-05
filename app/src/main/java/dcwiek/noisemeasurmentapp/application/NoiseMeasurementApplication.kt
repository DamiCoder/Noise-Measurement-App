package dcwiek.noisemeasurmentapp.application

import android.app.Application
import dcwiek.noisemeasurmentapp.application.component.*
import dcwiek.noisemeasurmentapp.application.module.ContextModule
import dcwiek.noisemeasurmentapp.application.module.NoiseMeasurementServerApiModule

class NoiseMeasurementApplication : Application() {
    private lateinit var probeRecorderComponent: ProbeRecorderComponent
//    private lateinit var storageComponent: StorageComponent
    private lateinit var notificationComponent: NotificationComponent
    private lateinit var appServiceComponent: AppServiceComponent

    override fun onCreate() {

        super.onCreate()
        val contextModule = ContextModule(this)
        probeRecorderComponent = DaggerProbeRecorderComponent
            .builder()
            .contextModule(contextModule)
            .build()
//
//        storageComponent = DaggerStorageComponent.builder()
//            .contextModule(contextModule)
//            .build()

        appServiceComponent = DaggerAppServiceComponent.builder()
            .contextModule(contextModule)
            .noiseMeasurementServerApiModule(NoiseMeasurementServerApiModule())
            .build()

        notificationComponent = DaggerNotificationComponent.builder()
            .contextModule(contextModule)
            .build()
    }

    fun getProbeRecorderComponent(): ProbeRecorderComponent = probeRecorderComponent

//    fun getStorageComponent(): StorageComponent = storageComponent

    fun getNotificationComponent(): NotificationComponent = notificationComponent

    fun getAppServiceComponent(): AppServiceComponent = appServiceComponent

}