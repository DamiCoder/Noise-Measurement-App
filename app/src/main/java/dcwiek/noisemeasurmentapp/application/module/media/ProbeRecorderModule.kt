package dcwiek.noisemeasurmentapp.application.module.media

import android.content.Context
import dagger.Module
import dagger.Provides
import dcwiek.noisemeasurmentapp.service.media.ProbeRecorder
import javax.inject.Inject
import javax.inject.Singleton


@Module
class ProbeRecorderModule {

    @Singleton
    @Provides
    @Inject
    fun providesProbeRecorder(context: Context) : ProbeRecorder = ProbeRecorder(context)
}