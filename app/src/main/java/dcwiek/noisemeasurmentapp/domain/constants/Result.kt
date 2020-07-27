package dcwiek.noisemeasurmentapp.domain.constants

enum class Result(val label:String, val backgroundName: String, val brightColorName: String, val darkColorName: String) {

    BAD("Zły", "archive_item_bg_bad", "failureColorBright", "failureColor"),
    MEDIUM("Średni", "archive_item_bg_medium", "averageColorBright", "averageColor"),
    GOOD("Dobry", "archive_item_bg_good","successColor", "successColorDark");

    companion object {
        fun getByLabel(label: String): Result {
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