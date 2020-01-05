package dcwiek.noisemeasurmentapp.view

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.service.NotificationService
import dcwiek.noisemeasurmentapp.service.SharedPreferencesService
import dcwiek.noisemeasurmentapp.view.constants.FragmentKeys
import kotlinx.android.synthetic.main.fragment_customprobesuccess.*


class CustomProbeSuccessFragment : Fragment() {

    companion object {
        fun newInstance() = CustomProbeSuccessFragment()
    }

    private lateinit var viewModel: CustomProbeSuccessViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_customprobesuccess, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CustomProbeSuccessViewModel::class.java)

        val vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        context?.let {
            NotificationService.vibrateAndPlaySound(vibrator, it)
            SharedPreferencesService
                .putSharedPreference(
                    it.getString(R.string.preference_key_choosen_probe),
                    it.getString(R.string.preference_value_use_custom_probe),
                    it)
        }


        button_customprobesuccess_continue.setOnClickListener{
            fragmentManager?.let { fragmentManager ->
                fragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.framelayout_main, MainMenuFragment.newInstance(), FragmentKeys.MAIN_MENU_FRAGMENT)
                    .commit()
            }
        }
    }

}
