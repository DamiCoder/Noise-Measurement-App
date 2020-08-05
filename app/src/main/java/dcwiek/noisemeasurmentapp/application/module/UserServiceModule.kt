package dcwiek.noisemeasurmentapp.application.module

import dagger.Module
import dagger.Provides
import dcwiek.noisemeasurmentapp.domain.DataStorage
import dcwiek.noisemeasurmentapp.service.http.NoiseMeasurementServerApi
import dcwiek.noisemeasurmentapp.service.user.UserService
import javax.inject.Inject
import javax.inject.Singleton

@Module
class UserServiceModule {

    @Singleton
    @Provides
    @Inject
    fun providesUserService(noiseMeasurementServerApi: NoiseMeasurementServerApi, dataStorage: DataStorage): UserService
            = UserService(noiseMeasurementServerApi, dataStorage)
}