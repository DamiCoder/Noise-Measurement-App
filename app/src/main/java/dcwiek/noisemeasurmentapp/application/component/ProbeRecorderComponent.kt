package dcwiek.noisemeasurmentapp.application.component

import android.content.Context
import dagger.Component
import dcwiek.noisemeasurmentapp.application.module.ContextModule
import dcwiek.noisemeasurmentapp.application.module.ProbeRecorderModule
import dcwiek.noisemeasurmentapp.service.media.ProbeRecorder
import javax.inject.Singleton

@Singleton
@Component(modules = [ProbeRecorderModule::class, ContextModule::class])
interface ProbeRecorderComponent {

    fun getContext(): Context
    fun getProbeRecorder(): ProbeRecorder

}