package dcwiek.noisemeasurmentapp.application.component

import android.content.Context
import dagger.Component
import dcwiek.noisemeasurmentapp.application.module.ContextModule
import dcwiek.noisemeasurmentapp.application.module.NotificationModule
import dcwiek.noisemeasurmentapp.service.NotificationService
import javax.inject.Singleton

@Singleton
@Component(modules = [NotificationModule::class, ContextModule::class])
interface NotificationComponent {

    fun getNotificationService() : NotificationService
    fun getContext() : Context
}