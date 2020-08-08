package dcwiek.noisemeasurmentapp.application.dto

import dcwiek.noisemeasurmentapp.domain.model.AppUser
import dcwiek.noisemeasurmentapp.domain.model.Place
import dcwiek.noisemeasurmentapp.domain.model.Standard

class ProbeDto(val id: Int,
               val location: String,
               val place: Place,
               val standards: List<Standard>,
               val appUser: AppUser,
               val result: Int,
               val comment: String,
               val probeRating: Int,
               val createdDate: String) {
    companion object {
        const val RECEIVED_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"
    }
}