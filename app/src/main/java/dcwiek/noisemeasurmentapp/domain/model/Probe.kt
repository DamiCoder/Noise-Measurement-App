package dcwiek.noisemeasurmentapp.domain.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Probe(
    val id: Int,
    val location: String,
    val place: Place,
    val standard: Standard,
    val appUser: AppUser,
    val result: Int,
    val comment: String,
    val createdDate: LocalDateTime) {

    companion object {
        fun mockGoodProbe(): Probe {
            val place = Place(0, "Place name", "Quite short place description")
            val standard = Standard(0, "Dobry", "Quite short standard description", 10, 35, "Law")
            return Probe(0, "180,90,180,90", place, standard, AppUser(), 50, "Empty comment", LocalDateTime.now().minusDays(2))
        }
        fun mockBadProbe(): Probe {
            val place = Place(0, "Place name", "Quite short place description")
            val standard = Standard(0, "Zły", "Quite short standard description", 10, 35, "Law")
            return Probe(0, "180,90,180,90", place, standard, AppUser(), 50, "Empty comment", LocalDateTime.now().minusDays(2))
        }
        fun mockAverageProbe(): Probe {
            val place = Place(0, "Średni", "Quite short place description")
            val standard = Standard(0, "Średni", "Quite short standard description", 10, 35, "Law")
            return Probe(0, "180,90,180,90", place, standard, AppUser(), 50, "Empty comment", LocalDateTime.now().minusDays(2))
        }
    }

    fun getCreateDateFormatted(): String {
        val dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm")
        return createdDate.format(dtf).toString()
    }

    fun getResultFormatted(): String {
        return result.toString() + "db"
    }
}