package dcwiek.noisemeasurmentapp.service.regulation

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dcwiek.noisemeasurmentapp.domain.model.Regulation
import dcwiek.noisemeasurmentapp.service.http.NoiseMeasurementServerApi
import java.util.*
import javax.inject.Inject

class RegulationService @Inject constructor(private val noiseMeasurementServerApi: NoiseMeasurementServerApi) {

    companion object {
        private val TAG: String = RegulationService::class.java.name
    }

    fun getRegulationsFromRemoteServer(): Optional<List<Regulation>> {
        val request = noiseMeasurementServerApi.prepareRegulationsRetrievalRequest()
        val response = noiseMeasurementServerApi.execute(request)
        val body: String = response.body?.string()!!

        Log.v(TAG, Gson().toJson(request))
        Log.v(TAG, body)
        return if(response.isSuccessful) {
            val regulationListType = object : TypeToken<List<Regulation>>() {}.type
            Optional.of(Gson().fromJson(body, regulationListType))
        } else {
            Optional.empty()
        }
    }
}