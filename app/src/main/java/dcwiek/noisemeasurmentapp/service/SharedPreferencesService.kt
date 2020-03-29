package dcwiek.noisemeasurmentapp.service

import android.content.Context
import android.content.SharedPreferences
import dcwiek.noisemeasurmentapp.R

class SharedPreferencesService {

    //TODO move to @component
    companion object {
        private lateinit var sharedPref: SharedPreferences
        private fun initializeSharedPreferences(context: Context) {
            if(!Companion::sharedPref.isInitialized) {
                sharedPref =
                    context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
            }
        }

        fun putSharedPreference(key: String, value: String, context: Context?) {
            context?.let { it ->
                initializeSharedPreferences(
                    it
                )
            }
            with (sharedPref.edit()) {
                putString(key, value)
                commit()
            }
        }

        fun getSharedPreference(key : String, context: Context?) : String? {
            context?.let { it ->
                initializeSharedPreferences(
                    it
                )
            }
            return sharedPref.getString(key,null)
        }
    }
}