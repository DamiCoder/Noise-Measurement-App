package dcwiek.noisemeasurmentapp.application.module.service

import dagger.Module
import dagger.Provides
import dcwiek.noisemeasurmentapp.domain.DataStorage
import dcwiek.noisemeasurmentapp.service.http.NoiseMeasurementServerApi
import dcwiek.noisemeasurmentapp.service.probe.ProbeMapper
import dcwiek.noisemeasurmentapp.service.standard.StandardService
import javax.inject.Inject
import javax.inject.Singleton

@Module
class ProbeMapperModule {

    @Singleton
    @Provides
    @Inject
    fun providesProbeMapper(standardService: StandardService, noiseMeasurementServerApi: NoiseMeasurementServerApi,
    dataStorage: DataStorage): ProbeMapper = ProbeMapper(StandardService(noiseMeasurementServerApi, dataStorage))
}