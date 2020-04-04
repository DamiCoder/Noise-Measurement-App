package dcwiek.noisemeasurmentapp.service

import android.content.Context
import android.content.SharedPreferences
import dcwiek.noisemeasurmentapp.R


class SharedPreferencesService(val context: Context) {

//    @Inject
//    @Named("applicationContext")
//    lateinit var context: Context


    private var sharedPref: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)


    fun putSharedPreference(key: String, value: String) {
        with(sharedPref.edit()) {
            putString(key, value)
            commit()
        }
    }

    fun getSharedPreference(key: String): String? {
        return sharedPref.getString(key, null)
    }
}