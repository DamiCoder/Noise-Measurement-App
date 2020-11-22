package dcwiek.noisemeasurmentapp.application.module.service

import dagger.Module
import dagger.Provides
import dcwiek.noisemeasurmentapp.domain.DataStorage
import dcwiek.noisemeasurmentapp.service.http.NoiseMeasurementServerApi
import dcwiek.noisemeasurmentapp.service.standard.StandardService
import javax.inject.Inject
import javax.inject.Singleton

@Module
class StandardServiceModule {

    @Singleton
    @Provides
    @Inject
    fun providesStandardService(noiseMeasurementServerApi: NoiseMeasurementServerApi, dataStorage: DataStorage)
            : StandardService = StandardService(noiseMeasurementServerApi, dataStorage)
}