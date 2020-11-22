package dcwiek.noisemeasurmentapp.application.module.service

import android.content.Context
import dagger.Module
import dagger.Provides
import dcwiek.noisemeasurmentapp.service.http.NoiseMeasurementServerApi
import javax.inject.Inject
import javax.inject.Singleton

@Module
class NoiseMeasurementServerApiModule {

    @Singleton
    @Provides
    @Inject
    fun providesNoiseMeasurementApi(context: Context): NoiseMeasurementServerApi
            = NoiseMeasurementServerApi(context)
}