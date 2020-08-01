package dcwiek.noisemeasurmentapp.domain.model

class Standard(
    val id: Int,
    val title: String,
    val description: String,
    val minValue: Int,
    val maxValue: Int,
    val regulation: String,
    val place: Place)