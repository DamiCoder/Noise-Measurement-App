package dcwiek.noisemeasurmentapp.application.component

import android.content.Context
import dagger.Component
import dcwiek.noisemeasurmentapp.application.module.ContextModule
import dcwiek.noisemeasurmentapp.application.module.DataStorageModule
import dcwiek.noisemeasurmentapp.application.module.NoiseMeasurementServerApiModule
import dcwiek.noisemeasurmentapp.application.module.UserServiceModule
import dcwiek.noisemeasurmentapp.service.user.UserService
import javax.inject.Singleton

@Singleton
@Component(modules = [DataStorageModule::class, ContextModule::class, UserServiceModule::class, NoiseMeasurementServerApiModule::class])
interface AppServiceComponent {

    fun getContext(): Context
    fun getUserService(): UserService
}