package dcwiek.noisemeasurmentapp.domain.model

import dcwiek.noisemeasurmentapp.domain.constants.HealthHazard
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Probe(
    val id: Int,
    val location: String,
    val place: Place,
    val standards: List<Standard>,
    val healthHazard: HealthHazard,
    val result: Int,
    var comment: String,
    var userRating: Int?,
    val createdDate: LocalDateTime) {

    companion object {
        const val DISPLAY_DATE_PATTERN = "dd-MM-yyyy HH:mm"
    }

    fun getCreateDateFormatted(): String {
        val dtf = DateTimeFormatter.ofPattern(DISPLAY_DATE_PATTERN)
        return createdDate.format(dtf).toString()
    }

    fun getResultFormatted(): String {
        return result.toString() + "dB"
    }
}