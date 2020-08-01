package dcwiek.noisemeasurmentapp.domain.constants

import java.util.*
import java.util.stream.Collectors

enum class Regulation(val label: String) {
    LAW("Prawne"),
    SCIENTIFIC("Naukowe");

    companion object {
        fun getLabels(): List<String> {
            return values().asList().stream().map(Regulation::label).collect(Collectors.toList())
        }

        fun getByLabel(label: String): Optional<Regulation> {
            return values().asList().stream().filter{it.label == label }.findFirst()
        }
    }
}