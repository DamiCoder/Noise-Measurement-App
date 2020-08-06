package dcwiek.noisemeasurmentapp.service.standard

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dcwiek.noisemeasurmentapp.domain.DataStorage
import dcwiek.noisemeasurmentapp.domain.constants.HealthHazard
import dcwiek.noisemeasurmentapp.domain.model.Place
import dcwiek.noisemeasurmentapp.domain.model.Standard
import dcwiek.noisemeasurmentapp.service.http.NoiseMeasurementServerApi
import java.util.*
import java.util.stream.Collectors
import javax.inject.Inject

class StandardService @Inject constructor(private val noiseMeasurementServerApi: NoiseMeasurementServerApi,
                                          private val dataStorage: DataStorage) {

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

    fun determineHealthHazard(result: Int, place: Place): HealthHazard {
        val standards = dataStorage.standards.value!!
        val filteredStandards = standards.stream()
            .filter {
                if (place.type != null) {
                    place.name == it.place.name && place.type == it.place.type
                } else {
                    place.name == it.place.name
                }
            }
            .collect(Collectors.toList())

        val healthHazardDeterminator = HealthHazardDeterminator()
        filteredStandards.forEach {
            if(result >= it.maxValue) {
                healthHazardDeterminator.logHighHazard()
            } else if(result >= it.minValue) {
                healthHazardDeterminator.logMediumHazard()
            } else {
                healthHazardDeterminator.logNoneHazard()
            }
        }

        return healthHazardDeterminator.determineHealthHazard()
    }

    private class HealthHazardDeterminator {
        var noneHazard = 0
        var mediumHazard = 0
        var highHazard = 0

        fun logNoneHazard() {
            noneHazard++
        }

        fun logMediumHazard() {
            mediumHazard++
        }

        fun logHighHazard() {
            highHazard++
        }

        fun determineHealthHazard(): HealthHazard {
            return if(highHazard != 0) {
                HealthHazard.HIGH
            } else if (mediumHazard != 0) {
                HealthHazard.MEDIUM
            } else {
                HealthHazard.NONE
            }
        }
    }
}