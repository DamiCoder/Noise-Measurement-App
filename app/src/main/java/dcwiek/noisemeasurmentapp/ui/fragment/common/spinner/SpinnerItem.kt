package dcwiek.noisemeasurmentapp.ui.fragment.common.spinner

class SpinnerItem(val value: String, val isHint: Boolean) {
    companion object {
        fun createValue(value: String): SpinnerItem {
            return SpinnerItem(value, false)
        }
        fun createHint(value: String): SpinnerItem {
            return SpinnerItem(value, true)
        }
    }

    override fun toString(): String {
        return value
    }
}