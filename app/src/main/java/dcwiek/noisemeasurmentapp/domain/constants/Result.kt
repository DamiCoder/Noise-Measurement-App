package dcwiek.noisemeasurmentapp.domain.constants

enum class Result(val label:String, val backgroundName: String) {

    BAD("Zły", "archive_item_bg_bad"),
    MEDIUM("Średni", "archive_item_bg_medium"),
    GOOD("Dobry", "archive_item_bg_good");

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