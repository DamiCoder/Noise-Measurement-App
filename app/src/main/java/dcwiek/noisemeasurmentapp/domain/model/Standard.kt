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
}