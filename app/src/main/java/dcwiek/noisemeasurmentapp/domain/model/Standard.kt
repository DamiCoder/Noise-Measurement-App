package dcwiek.noisemeasurmentapp.domain.model

import dcwiek.noisemeasurmentapp.domain.constants.Regulation

class Standard(
    val id: Int,
    val title: String,
    val description: String,
    val minValue: Int,
    val maxValue: Int,
    val regulation: String,
    val place: Place) {

    fun getTitleFormatted(): String {
        return if(regulation == Regulation.LAW.name) { "Norma prawna" } else { title }
    }

    fun getPlaceFormatted(): String {
        return if(regulation == Regulation.LAW.name) { place.name } else { "${place.name} (${place.type})" }
    }

    companion object {
        fun mockStandard(regulation: Regulation): Standard {
            return if(regulation == Regulation.SCIENTIFIC) {
                val place = Place(0, "Place name", "Quite short place description", "ABC")
                Standard(0, "Dobry", "Quite short standard description", 10, 35, regulation.name, place)
            } else {
                val place = Place(0, "Place name", "Quite short place description", "")
                Standard(0, "Dobry", "Quite short standard description", 10, 35, regulation.name, place)
            }
        }
    }

}