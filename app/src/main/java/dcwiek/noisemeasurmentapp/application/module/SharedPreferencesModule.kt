package dcwiek.noisemeasurmentapp.application.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dcwiek.noisemeasurmentapp.service.SharedPreferencesService
import javax.inject.Inject
import javax.inject.Singleton

@Module
class SharedPreferencesModule {

    @Singleton
    @Provides
    @Inject
    fun providesSharedPreferencesService(context: Context): SharedPreferencesService = SharedPreferencesService(context)

}