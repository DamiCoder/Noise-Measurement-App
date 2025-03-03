package dcwiek.noisemeasurmentapp.service.probe

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dcwiek.noisemeasurmentapp.application.dto.ProbeDto
import dcwiek.noisemeasurmentapp.domain.model.Probe
import dcwiek.noisemeasurmentapp.domain.model.Standard
import dcwiek.noisemeasurmentapp.service.http.NoiseMeasurementServerApi
import java.io.File
import java.util.*
import java.util.stream.Collectors

class ProbeService constructor(private val noiseMeasurementServerApi: NoiseMeasurementServerApi,
                               private val probeMapper: ProbeMapper) {

    companion object {
        private val TAG: String = ProbeService::class.java.name
    }

    fun getAllUserProbesFromRemoteServer(): Optional<LinkedList<Probe>> {
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
            Optional.of(LinkedList(probes))
        } else {
            Optional.empty()
        }
    }

    fun uploadProbeToRemoteServer(probe: Probe): Optional<Probe> {
        var standardIds = probe.standards.stream().map(Standard::id).collect(Collectors.toList())
        if(standardIds.isEmpty()) {
            standardIds = Collections.emptyList()
        }
        val request = noiseMeasurementServerApi.prepareProbeCreationRequest(probe.location, probe.place,
            probe.place.regulation, probe.result, standardIds, probe.comment, probe.userRating)
        val response = noiseMeasurementServerApi.execute(request)
        val body: String = response.body?.string()!!

        Log.v(TAG, Gson().toJson(request))
        Log.v(TAG, body)

        val probeDtoOptional: Optional<ProbeDto>
        probeDtoOptional = if(response.isSuccessful) {
            val probesListType = object : TypeToken<ProbeDto>() {}.type
            Optional.of(Gson().fromJson(body, probesListType))
        } else {
            Optional.empty()
        }

        return if (probeDtoOptional.isPresent) {
            val mappedProbe = probeMapper.mapProbeDtoToProbe(probeDtoOptional.get())
            Optional.of(mappedProbe)
        } else {
            Optional.empty()
        }
    }

    fun processProbeOnRemoteServer(probe: File, amplitudeReferenceValue: Double): Optional<Double> {
        val request = noiseMeasurementServerApi.prepareProbeProcessingRequest(probe, amplitudeReferenceValue)
        val response = noiseMeasurementServerApi.execute(request)
        val body: String = response.body?.string()!!

        Log.v(TAG, Gson().toJson(request))
        Log.v(TAG, body)

        val result: Optional<Double>
        result = if(response.isSuccessful) {
            Optional.of(body.toDouble())
        } else {
            Optional.empty()
        }
        return result
    }
}