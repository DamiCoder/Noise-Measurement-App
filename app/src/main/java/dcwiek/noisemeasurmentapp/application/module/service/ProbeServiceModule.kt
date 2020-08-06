package dcwiek.noisemeasurmentapp.application.module.service

import dagger.Module
import dagger.Provides
import dcwiek.noisemeasurmentapp.service.http.NoiseMeasurementServerApi
import dcwiek.noisemeasurmentapp.service.probe.ProbeMapper
import dcwiek.noisemeasurmentapp.service.probe.ProbeService
import javax.inject.Inject
import javax.inject.Singleton

@Module
class ProbeServiceModule {

    @Singleton
    @Provides
    @Inject
    fun providesProbeService(noiseMeasurementServerApi: NoiseMeasurementServerApi, probeMapper: ProbeMapper): ProbeService
            = ProbeService(noiseMeasurementServerApi, probeMapper)

}