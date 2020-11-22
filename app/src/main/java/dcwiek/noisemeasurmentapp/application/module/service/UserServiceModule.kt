package dcwiek.noisemeasurmentapp.application.module.service

import dagger.Module
import dagger.Provides
import dcwiek.noisemeasurmentapp.service.http.NoiseMeasurementServerApi
import dcwiek.noisemeasurmentapp.service.user.UserService
import javax.inject.Inject
import javax.inject.Singleton

@Module
class UserServiceModule {

    @Singleton
    @Provides
    @Inject
    fun providesUserService(noiseMeasurementServerApi: NoiseMeasurementServerApi): UserService
            = UserService(noiseMeasurementServerApi)
}