package dcwiek.noisemeasurmentapp.service.standard

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dcwiek.noisemeasurmentapp.domain.model.Standard
import dcwiek.noisemeasurmentapp.service.http.NoiseMeasurementServerApi
import java.util.*
import javax.inject.Inject

class StandardService @Inject constructor(private val noiseMeasurementServerApi: NoiseMeasurementServerApi) {

    companion object {
        private val TAG: String = StandardService::class.java.name
    }

    fun getStandardsFromRemoteServer(): Optional<List<Standard>> {
        val request = noiseMeasurementServerApi.prepareStandardsRetrievalRequest()
        val response = noiseMeasurementServerApi.execute(request)
        val body: String = response.body?.string()!!

        Log.v(TAG, Gson().toJson(request))
        Log.v(TAG, body)

        return if(response.isSuccessful) {
            val regulationListType = object : TypeToken<List<Standard>>() {}.type
            Optional.of(Gson().fromJson(body, regulationListType))
        } else {
            Optional.empty()
        }
    }
}