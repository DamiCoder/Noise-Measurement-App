package dcwiek.noisemeasurmentapp.service.probe

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dcwiek.noisemeasurmentapp.application.dto.ProbeDto
import dcwiek.noisemeasurmentapp.domain.model.Probe
import dcwiek.noisemeasurmentapp.service.http.NoiseMeasurementServerApi
import java.util.*
import java.util.stream.Collectors

class ProbeService constructor(private val noiseMeasurementServerApi: NoiseMeasurementServerApi,
                               private val probeMapper: ProbeMapper) {

    companion object {
        private val TAG: String = ProbeService::class.java.name
    }

    fun getAllUserProbesFromRemoteServer(): Optional<List<Probe>> {
        val request = noiseMeasurementServerApi.prepareProbesRetrievalRequest()
        val response = noiseMeasurementServerApi.execute(request)
        val body: String = response.body?.string()!!

        Log.v(TAG, Gson().toJson(request))
        Log.v(TAG, body)

        val probeDtosOptional: Optional<List<ProbeDto>>
        probeDtosOptional = if(response.isSuccessful) {
            val probesListType = object : TypeToken<List<ProbeDto>>() {}.type
            Optional.of(Gson().fromJson(body, probesListType))
        } else {
            Optional.empty()
        }

        return if (probeDtosOptional.isPresent) {
            val probes = probeDtosOptional.get().stream().map { probeMapper.mapProbeDtoToProbe(it) }.collect(Collectors.toList())
            Optional.of(probes)
        } else {
            Optional.empty()
        }
    }
}