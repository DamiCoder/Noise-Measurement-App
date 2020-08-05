package dcwiek.noisemeasurmentapp.service.user

import android.util.Log
import com.google.gson.Gson
import dcwiek.noisemeasurmentapp.domain.DataStorage
import dcwiek.noisemeasurmentapp.domain.model.AppUser
import dcwiek.noisemeasurmentapp.service.http.NoiseMeasurementServerApi
import java.util.*

class UserService constructor(
    private val noiseMeasurementServerApi: NoiseMeasurementServerApi,
    private val dataStorage: DataStorage) {

    companion object {
        private val TAG: String = UserService::class.java.name
    }

    fun login(username: String, password: String): Optional<AppUser> {
        val request = noiseMeasurementServerApi.prepareLoginRequest(username, password)
        val response = noiseMeasurementServerApi.execute(request)
        val body: String = response.body?.string()!!

        Log.v(TAG, Gson().toJson(request))
        Log.v(TAG, body)
        return if(response.isSuccessful) {
            Optional.of(Gson().fromJson(body, AppUser::class.javaObjectType))
        } else {
            Optional.empty()
        }
    }

    fun register(username: String, password: String): Optional<AppUser> {
        val request = noiseMeasurementServerApi.prepareRegisterRequest(username, password)
        val response = noiseMeasurementServerApi.execute(request)
        val body: String = response.body?.string()!!

        Log.v(TAG, Gson().toJson(request))
        Log.v(TAG, body)
        return if(response.isSuccessful) {
            Optional.of(Gson().fromJson(body, AppUser::class.javaObjectType))
        } else {
            Optional.empty()
        }
    }


}