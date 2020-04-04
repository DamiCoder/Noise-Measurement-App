package dcwiek.noisemeasurmentapp.application.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dcwiek.noisemeasurmentapp.data.DataStorage
import javax.inject.Inject
import javax.inject.Singleton

@Module
class DataStorageModule {

    @Singleton
    @Provides
    @Inject
    fun provideDataStorage(context: Context) : DataStorage {
        //TODO: add context
        return DataStorage()
    }
}