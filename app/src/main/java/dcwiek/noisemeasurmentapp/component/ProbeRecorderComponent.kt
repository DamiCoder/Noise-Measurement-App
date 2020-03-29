package dcwiek.noisemeasurmentapp.component

import dagger.Component
import dcwiek.noisemeasurmentapp.module.ContextModule
import dcwiek.noisemeasurmentapp.service.ProbeRecorder
import javax.inject.Singleton

@Singleton
@Component(modules = [ProbeRecorder::class, ContextModule::class])
interface ProbeRecorderComponent {

    fun getProbeRecorder(): ProbeRecorder
}