package dcwiek.noisemeasurmentapp.domain.constants

import java.util.*
import java.util.stream.Collectors

enum class Place(val label: String, val regulation: Regulation) {
    HEALTH_RESORT("Uzdrowisko lub Szpital poza miastem", Regulation.LAW),
    DETACHED_HOUSE("Dom jednorodzinny lub Szpital miejski", Regulation.LAW),
    MULTI_OCCUPIED_HOUSE("Dom wielorodzinny lub Blok lub Teren rekreacyjny lub Teren mieszkaniowo-usługowy", Regulation.LAW),
    DOWNTOWN("Strefa śródmiejska", Regulation.LAW),
    PLACE_OF_RESIDENCE("Środowisko zamieszkania", Regulation.SCIENTIFIC),
    WORKSITE("Środowisko zawodowe", Regulation.SCIENTIFIC),
    INDUSTRIAL_WORKSITE("Środowisko zawodowe przemysłowe", Regulation.SCIENTIFIC),
    OFFICE_WORKSITE("Środowisko zawodowe biurowe", Regulation.SCIENTIFIC);

    companion object {
        fun getAllByRegulation(regulation: Regulation): List<Place> {
            return values().asList()
                .stream()
                .filter{place -> place.regulation == regulation}
                .collect(Collectors.toList())
        }

        fun getByLabel(label: String): Optional<Place> {
            return values().asList()
                .stream()
                .filter{place -> place.label == label}
                .findFirst()
        }
    }

}