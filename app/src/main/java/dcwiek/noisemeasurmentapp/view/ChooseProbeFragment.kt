package dcwiek.noisemeasurmentapp.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.view.const.FragmentKeys
import kotlinx.android.synthetic.main.fragment_chooseprobe.*


class ChooseProbeFragment : Fragment() {

    companion object {
        fun newInstance() = ChooseProbeFragment()
    }

    private lateinit var viewModel: ChooseProbeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chooseprobe, container, false)
    }

    @SuppressLint("ResourceType")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ChooseProbeViewModel::class.java)

        chooseprobe_radiogroup.setOnCheckedChangeListener { group, checkedId ->
            chooseprobe_radiogroup.forEach {
                val radioButton = it as RadioButton
                if(!radioButton.isChecked) {
                    radioButton.background = ResourcesCompat.getDrawable(resources, R.drawable.radio_button_unchecked, null)
                    val typeface = resources.getFont(R.font.open_sans)
                    radioButton.typeface = typeface
                    radioButton.setTextColor(ResourcesCompat.getColor(resources, R.color.contraryBlue, null))
                } else {
                    radioButton.background = ResourcesCompat.getDrawable(resources, R.drawable.radio_button_checked, null);
                    val typeface = resources.getFont(R.font.open_sans)
                    radioButton.typeface = typeface
                    radioButton.setTextColor(Color.WHITE)
                }
            }
        }
        button_chooseprobe_continue.setOnClickListener{
            if(chooseprobe_defaultproberadio.isChecked) {
                fragmentManager?.let { fragmentManager ->
                    fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.framelayout_main, MainMenuFragment.newInstance(), FragmentKeys.MAIN_MENU_FRAGMENT)
                        .commit()
                }
            }else {
                //TODO: przenieś do fragmentu nagrywania próbki
            }
        }
    }

}
