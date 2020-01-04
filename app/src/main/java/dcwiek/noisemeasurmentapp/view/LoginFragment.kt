package dcwiek.noisemeasurmentapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import dcwiek.noisemeasurmentapp.R
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
        textview_login_registerlink.setOnClickListener{
            fragmentManager?.let { fragmentManager ->
                fragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.framelayout_main, RegisterFragment.newInstance(), FragmentKeys.REGISTER_FRAGMENT)
                    .commit()
            }
        }

        //TODO: add MainMenuFragment to ChooseProbeFragment!
        button_login_login.setOnClickListener{
            fragmentManager?.let { fragmentManager ->
                fragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.framelayout_main, ChooseProbeFragment.newInstance(), FragmentKeys.CHOOSE_PROBE_FRAGMENT)
                    .commit()
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
