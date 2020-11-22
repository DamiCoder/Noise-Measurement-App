package dcwiek.noisemeasurmentapp.ui.fragment.standards

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.forEach
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.domain.constants.Regulation
import dcwiek.noisemeasurmentapp.ui.constants.FragmentKeys
import dcwiek.noisemeasurmentapp.ui.fragment.common.fragment.ExtendedFragment
import kotlinx.android.synthetic.main.fragment_initstandardsdiscovery.*

class InitStandardsDiscoveryFragment: ExtendedFragment() {

    companion object {
        fun newInstance() = InitStandardsDiscoveryFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_initstandardsdiscovery, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initstandardsdiscovery_radiogroup.setOnCheckedChangeListener { _, _ ->
            changeRadioButtonsAppearance()
        }
        button_initstandardsview_continue.setOnClickListener {
            if (initstandardsdiscovery_radiogroup.checkedRadioButtonId == R.id.initstandardsdiscovery_law_radiobutton) {
                replaceFragment(
                    R.id.framelayout_main,
                    StandardsDiscoveryFragment.newInstance(Regulation.LAW),
                    FragmentKeys.STANDARDS_DISCOVERY_FRAGMENT
                )
            } else {
                replaceFragment(
                    R.id.framelayout_main,
                    StandardsDiscoveryFragment.newInstance(Regulation.SCIENTIFIC),
                    FragmentKeys.STANDARDS_DISCOVERY_FRAGMENT
                )
            }
        }
    }

   private fun changeRadioButtonsAppearance() {
       initstandardsdiscovery_radiogroup.forEach {
            val radioButton = it as RadioButton
            if (!radioButton.isChecked) {
                radioButton.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.radio_button_unchecked, null)
                val typeface = resources.getFont(R.font.open_sans)
                radioButton.typeface = typeface
                radioButton.setTextColor(ResourcesCompat.getColor(resources, R.color.contraryBlue, null))
            } else {
                radioButton.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.radio_button_checked, null)
                val typeface = resources.getFont(R.font.open_sans)
                radioButton.typeface = typeface
                radioButton.setTextColor(Color.WHITE)
            }
        }
    }
}