package dcwiek.noisemeasurmentapp.application.module.service

import dagger.Module
import dagger.Provides
import dcwiek.noisemeasurmentapp.service.http.NoiseMeasurementServerApi
import dcwiek.noisemeasurmentapp.service.place.PlaceService
import javax.inject.Inject
import javax.inject.Singleton

@Module
class PlaceServiceModule {

    @Singleton
    @Provides
    @Inject
    fun providesPlaceService(noiseMeasurementServerApi: NoiseMeasurementServerApi): PlaceService
            = PlaceService(noiseMeasurementServerApi)
}