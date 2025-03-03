package dcwiek.noisemeasurmentapp.domain.model

class Place(val id: Int, val name: String, val description: String, val type: String?, val regulation: Regulation) {

    fun getNameFormatted(): String {
        return if(type != null) {
            "$name (${type})"
        } else {
            name
        }
    }
}