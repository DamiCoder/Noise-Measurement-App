package dcwiek.noisemeasurmentapp.application.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dcwiek.noisemeasurmentapp.media.ProbeRecorder
import javax.inject.Inject
import javax.inject.Singleton


@Module
class ProbeRecorderModule {

    @Singleton
    @Provides
    @Inject
    fun providesProbeRecorder(context: Context) : ProbeRecorder =
        ProbeRecorder(context)
}