package dcwiek.noisemeasurmentapp.domain.constants

enum class HealthHazard(val label:String, val backgroundName: String, val brightColorName: String, val darkColorName: String, val hazardDescription: String) {

    HIGH("Zły", "archive_item_bg_bad", "failureColorBright", "failureColor",
    "Poziom hałasu zarejestrowany przez telefon zagraża Twojemu zdrowiu/przekracza dopuszczalne normy. Udaj się w cichsze miejsce, bądź zminimalizuj czas ekspozycji."),
    MEDIUM("Średni", "archive_item_bg_medium", "averageColorBright", "averageColor",
        "Poziom hałasu zarejestrowany przez telefon może zagrażać Twojemu zdrowiu/przekraczać dopuszczalne normy. Udaj się w cichsze miejsce, bądź zminimalizuj czas ekspozycji."),
    NONE("Dobry", "archive_item_bg_good","successColor", "successColorDark",
    "Poziom hałasu zarejestrowany przez telefon nie zagraża Twojemu zdrowiu/nie przekracza dopuszczalnych norm");

    companion object {
        fun getByLabel(label: String): HealthHazard {
            val result = values().toList().stream()
                .filter{result -> result.label == label }
                .findFirst()
                .orElse(null)
            if(result == null) {
                throw Exception()
            } else {
                return result
            }
        }
    }
}