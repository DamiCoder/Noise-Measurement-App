package dcwiek.noisemeasurmentapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.service.SharedPreferencesService
import dcwiek.noisemeasurmentapp.view.constants.FragmentKeys
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        textview_login_registerlink.setOnClickListener {
            fragmentManager?.let { fragmentManager ->
                fragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.framelayout_main, RegisterFragment.newInstance(), FragmentKeys.REGISTER_FRAGMENT)
                    .commit()
            }
        }
        button_login_login.setOnClickListener {
            chooseNextFragment()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun chooseNextFragment() {
        val chosenProbe =
            SharedPreferencesService.getSharedPreference(getString(R.string.preference_key_choosen_probe), context)
        if (chosenProbe != null) {
            createMainMenuFragment()
        } else {
            createChooseProbeFragment()
        }
    }

    private fun createMainMenuFragment() {
        fragmentManager?.let { fragmentManager ->
            fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.framelayout_main, MainMenuFragment.newInstance(), FragmentKeys.MAIN_MENU_FRAGMENT)
                .commit()
        }
    }

    private fun createChooseProbeFragment() {
        fragmentManager?.let { fragmentManager ->
            fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.framelayout_main, ChooseProbeFragment.newInstance(), FragmentKeys.CHOOSE_PROBE_FRAGMENT)
                .commit()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
