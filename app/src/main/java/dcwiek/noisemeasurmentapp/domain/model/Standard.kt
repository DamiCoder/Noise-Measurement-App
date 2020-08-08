package dcwiek.noisemeasurmentapp.domain.model


class Standard(
    val id: Int,
    private val title: String,
    val description: String,
    val minValue: Int,
    val maxValue: Int,
    val regulation: Regulation,
    val place: Place) {

    fun getTitleFormatted(): String {
        return if (regulation.name == dcwiek.noisemeasurmentapp.domain.constants.Regulation.LAW.name) {
            "Norma prawna"
        } else {
            "Zagrożenie: $title"
        }
    }

    companion object {
//        fun mockStandard(regulation: Regulation): Standard {
//            return if(regulation.name == dcwiek.noisemeasurmentapp.domain.constants.Regulation.SCIENTIFIC.name) {
//                val place = Place(0, "Place name", "Quite short place description", "ABC", regulation)
//                Standard(0, "Dobry", "Quite short standard description", 10, 35, regulation, place)
//            } else {
//                val place = Place(0, "Place name", "Quite short place description", "", regulation)
//                Standard(0, "Dobry", "Quite short standard description", 10, 35, regulation, place)
//            }
//        }
    }

}