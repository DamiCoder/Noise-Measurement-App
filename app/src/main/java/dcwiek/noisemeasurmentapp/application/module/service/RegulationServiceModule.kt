package dcwiek.noisemeasurmentapp.application.module.service

import dagger.Module
import dagger.Provides
import dcwiek.noisemeasurmentapp.service.http.NoiseMeasurementServerApi
import dcwiek.noisemeasurmentapp.service.regulation.RegulationService
import javax.inject.Inject
import javax.inject.Singleton

@Module
class RegulationServiceModule {

    @Singleton
    @Provides
    @Inject
    fun providesRegulationService(noiseMeasurementServerApi: NoiseMeasurementServerApi): RegulationService
            = RegulationService(noiseMeasurementServerApi)
}