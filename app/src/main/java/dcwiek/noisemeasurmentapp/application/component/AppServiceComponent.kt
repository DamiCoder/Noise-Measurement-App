package dcwiek.noisemeasurmentapp.application.component

import android.content.Context
import dagger.Component
import dcwiek.noisemeasurmentapp.application.module.*
import dcwiek.noisemeasurmentapp.domain.DataStorage
import dcwiek.noisemeasurmentapp.service.SharedPreferencesService
import dcwiek.noisemeasurmentapp.service.place.PlaceService
import dcwiek.noisemeasurmentapp.service.regulation.RegulationService
import dcwiek.noisemeasurmentapp.service.user.UserService
import javax.inject.Singleton

@Singleton
@Component(modules = [DataStorageModule::class, ContextModule::class, UserServiceModule::class,
    NoiseMeasurementServerApiModule::class, PlaceServiceModule::class, SharedPreferencesModule::class])
interface AppServiceComponent {

    fun getContext(): Context
    fun getUserService(): UserService
    fun getPlaceService(): PlaceService
    fun getRegulationService(): RegulationService
    fun getSharedPreferencesService() : SharedPreferencesService
    fun getDataStorage() : DataStorage
}