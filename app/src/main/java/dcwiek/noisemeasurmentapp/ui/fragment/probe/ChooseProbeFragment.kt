package dcwiek.noisemeasurmentapp.ui.fragment.probe

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.RadioButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.forEach
import androidx.lifecycle.ViewModelProviders
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.ui.constants.FragmentKeys
import dcwiek.noisemeasurmentapp.ui.fragment.common.fragment.ExtendedFragment
import dcwiek.noisemeasurmentapp.ui.fragment.menu.MainMenuFragment
import kotlinx.android.synthetic.main.fragment_chooseprobe.*


class ChooseProbeFragment : ExtendedFragment() {

    private lateinit var popupWindow : PopupWindow

    companion object {
        fun newInstance() = ChooseProbeFragment()
    }

    private lateinit var viewModel: ChooseProbeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chooseprobe, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ChooseProbeViewModel::class.java)

        chooseprobe_radiogroup.setOnCheckedChangeListener { group, checkedId ->
            changeRadioButtonsAppearance()
        }
        button_chooseprobe_continue.setOnClickListener {
            if (chooseprobe_defaultproberadio.isChecked) {
                context?.let {
                    sharedPreferencesService.putSharedPreference(
                        it.getString(R.string.preference_key_choosen_probe),
                        it.getString(R.string.preference_value_use_custom_probe)
                    )
                }
                createMainMenuFragment()
            } else {
                createCustomProbeFragment()
            }
        }
        chooseprobe_button_info.setOnClickListener {
            createInfoPopup()
        }
    }

    private fun changeRadioButtonsAppearance() {
        chooseprobe_radiogroup.forEach {
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

    private fun createInfoPopup() {
        val popupView: View = LayoutInflater.from(context).inflate(R.layout.popup_chooseprobeinfo, null)
        val width = ConstraintLayout.LayoutParams.WRAP_CONTENT
        val height = ConstraintLayout.LayoutParams.WRAP_CONTENT
        val focusable = true
        popupWindow = PopupWindow(popupView, width, height, focusable)
        popupWindow.animationStyle = R.style.Animation
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        (popupView.findViewById(R.id.popup_chooseprobeinfo_closebutton) as Button).setOnClickListener {
            popupWindow.dismiss()
        }
    }

    private fun createCustomProbeFragment() {
        replaceFragment(R.id.framelayout_main, CustomProbeFragment.newInstance(), FragmentKeys.CUSTOM_PROBE_FRAGMENT)
    }

    private fun createMainMenuFragment() {
        replaceFragment(R.id.framelayout_main, MainMenuFragment.getInstance(), FragmentKeys.MAIN_MENU_FRAGMENT)
    }

}
