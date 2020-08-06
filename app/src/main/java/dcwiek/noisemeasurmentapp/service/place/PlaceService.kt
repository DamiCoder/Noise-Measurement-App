package dcwiek.noisemeasurmentapp.service.place

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dcwiek.noisemeasurmentapp.domain.model.Place
import dcwiek.noisemeasurmentapp.service.http.NoiseMeasurementServerApi
import java.util.*

class PlaceService constructor(private val noiseMeasurementServerApi: NoiseMeasurementServerApi) {

    companion object {
        private val TAG: String = PlaceService::class.java.name
    }

    fun getPlacesFromRemoteServer(): Optional<List<Place>> {
        val request = noiseMeasurementServerApi.preparePlacesRetrievalRequest()
        val response = noiseMeasurementServerApi.execute(request)
        val body: String = response.body?.string()!!

        Log.v(TAG, Gson().toJson(request))
        Log.v(TAG, body)
        return if(response.isSuccessful) {
            val placeListType = object : TypeToken<List<Place>>() {}.type
            Optional.of(Gson().fromJson(body, placeListType))
        } else {
            Optional.empty()
        }
    }
}