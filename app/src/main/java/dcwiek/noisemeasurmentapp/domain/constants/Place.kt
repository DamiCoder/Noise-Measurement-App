package dcwiek.noisemeasurmentapp.domain.constants

import java.util.*
import java.util.stream.Collectors

enum class Place(val label: String, val regulation: Regulation) {
    HEALTH_RESORT("Uzdrowisko / Szpital poza miastem", Regulation.LAW),
    DETACHED_HOUSE("Dom jednorodzinny / Szpital miejski", Regulation.LAW),
    MULTI_OCCUPIED_HOUSE("Dom wielorodzinny / Blok / Teren mieszkaniowo-usługowy", Regulation.LAW),
    DOWNTOWN("Strefa śródmiejska", Regulation.LAW),
    PLACE_OF_RESIDENCE("Środowisko zamieszkania", Regulation.SCIENTIFIC),
    WORKSITE("Środowisko zawodowe", Regulation.SCIENTIFIC),
    INDUSTRIAL_WORKSITE("Środowisko zawodowe (Przemysłowe)", Regulation.SCIENTIFIC),
    OFFICE_WORKSITE("Środowisko zawodowe (Biurowe)", Regulation.SCIENTIFIC);

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