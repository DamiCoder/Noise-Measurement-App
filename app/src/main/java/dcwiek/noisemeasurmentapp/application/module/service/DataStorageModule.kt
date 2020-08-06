package dcwiek.noisemeasurmentapp.application.module.service

import android.content.Context
import dagger.Module
import dagger.Provides
import dcwiek.noisemeasurmentapp.domain.DataStorage
import javax.inject.Inject
import javax.inject.Singleton

@Module
class DataStorageModule {

    @Singleton
    @Provides
    @Inject
    fun provideDataStorage(context: Context) : DataStorage = DataStorage()
}