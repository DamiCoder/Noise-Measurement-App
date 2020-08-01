package dcwiek.noisemeasurmentapp.ui.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.ui.constants.FragmentKeys
import dcwiek.noisemeasurmentapp.ui.fragment.common.fragment.ExtendedFragment
import dcwiek.noisemeasurmentapp.ui.fragment.menu.MainMenuFragment
import dcwiek.noisemeasurmentapp.ui.fragment.register.RegisterFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment: ExtendedFragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        textview_login_registerlink.setOnClickListener {
            replaceFragment(R.id.framelayout_main, RegisterFragment.newInstance(), FragmentKeys.REGISTER_FRAGMENT)
        }
        button_login_login.setOnClickListener {
            //TODO: log in here, create data etc
            createMainMenuFragment()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun createMainMenuFragment() {
        replaceFragment(R.id.framelayout_main, MainMenuFragment.getInstance(), FragmentKeys.MAIN_MENU_FRAGMENT)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

}
