package dcwiek.noisemeasurmentapp.application.module.notification

import android.content.Context
import dagger.Module
import dagger.Provides
import dcwiek.noisemeasurmentapp.service.NotificationService
import javax.inject.Inject
import javax.inject.Singleton

@Module
class NotificationModule {

    @Singleton
    @Provides
    @Inject
    fun provideNotificationService(context: Context) : NotificationService = NotificationService(context)
}