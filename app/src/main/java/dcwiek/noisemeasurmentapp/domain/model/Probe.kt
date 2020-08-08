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
    val comment: String,
    val userRating: Int,
    val createdDate: LocalDateTime) {

    companion object {
//        fun mockGoodProbe(): Probe {
//            val regulation = Regulation(0, "LAW")
//            val place = Place(0, "Place name", "Quite short place description", "", regulation)
//            val standard = Standard(0, "Dobry", "Quite short standard description", 10, 35, regulation, place)
//            return Probe(0, "180,90,180,90", place, listOf(standard), HealthHazard.NONE, , "Empty comment", 3, LocalDateTime.now().minusDays(2))
//        }
//        fun mockBadProbe(): Probe {
//            val regulation = Regulation(0, "LAW")
//            val place = Place(0, "Place name", "Quite short place description", "", regulation)
//            val standard = Standard(0, "Zły", "Quite short standard description", 10, 35, regulation, place)
//            return Probe(0, "180,90,180,90", place, listOf(standard), HealthHazard.HIGH,50, "Empty comment", LocalDateTime.now().minusDays(2))
//        }
//        fun mockAverageProbe(): Probe {
//            val regulation = Regulation(0, "LAW")
//            val place = Place(0, "Place name", "Quite short place description", "", regulation)
//            val standard = Standard(0, "Średni", "Quite short standard description", 10, 35, regulation, place)
//            return Probe(0, "180,90,180,90", place, listOf(standard),  HealthHazard.MEDIUM,50, "Empty comment", LocalDateTime.now().minusDays(2))
//        }

        const val DISPLAY_DATE_PATTERN = "dd-MM-yyyy HH:mm"
    }

    fun getCreateDateFormatted(): String {
        val dtf = DateTimeFormatter.ofPattern(DISPLAY_DATE_PATTERN)
        return createdDate.format(dtf).toString()
    }

    fun getResultFormatted(): String {
        return result.toString() + "db"
    }
}