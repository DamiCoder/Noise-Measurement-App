package dcwiek.noisemeasurmentapp.application.component

import android.content.Context
import dagger.Component
import dcwiek.noisemeasurmentapp.application.module.ContextModule
import dcwiek.noisemeasurmentapp.application.module.service.*
import dcwiek.noisemeasurmentapp.domain.DataStorage
import dcwiek.noisemeasurmentapp.service.SharedPreferencesService
import dcwiek.noisemeasurmentapp.service.place.PlaceService
import dcwiek.noisemeasurmentapp.service.probe.ProbeMapper
import dcwiek.noisemeasurmentapp.service.probe.ProbeService
import dcwiek.noisemeasurmentapp.service.regulation.RegulationService
import dcwiek.noisemeasurmentapp.service.standard.StandardService
import dcwiek.noisemeasurmentapp.service.user.UserService
import javax.inject.Singleton

@Singleton
@Component(modules = [DataStorageModule::class, ContextModule::class, UserServiceModule::class,
        NoiseMeasurementServerApiModule::class, PlaceServiceModule::class, SharedPreferencesModule::class,
        StandardServiceModule::class, ProbeServiceModule::class, ProbeMapperModule::class])
interface AppServiceComponent {

    fun getContext(): Context
    fun getUserService(): UserService
    fun getPlaceService(): PlaceService
    fun getRegulationService(): RegulationService
    fun getStandardService(): StandardService
    fun getSharedPreferencesService(): SharedPreferencesService
    fun getDataStorage(): DataStorage
    fun getProbeService(): ProbeService
    fun getProbeMapper(): ProbeMapper
}