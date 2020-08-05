package dcwiek.noisemeasurmentapp.ui.fragment.login

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.domain.model.AppUser
import dcwiek.noisemeasurmentapp.ui.constants.FragmentKeys
import dcwiek.noisemeasurmentapp.ui.fragment.common.fragment.ExtendedFragment
import dcwiek.noisemeasurmentapp.ui.fragment.common.popup.PopupUtil
import dcwiek.noisemeasurmentapp.ui.fragment.menu.MainMenuFragment
import dcwiek.noisemeasurmentapp.ui.fragment.register.RegisterFragment
import kotlinx.android.synthetic.main.fragment_login.*
import java.net.URL
import java.util.*


class LoginFragment: ExtendedFragment() {

//    private lateinit var userService: UserService

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        textview_login_registerlink.setOnClickListener {
            replaceFragment(R.id.framelayout_main, RegisterFragment.newInstance(), FragmentKeys.REGISTER_FRAGMENT)
        }
        button_login_login.setOnClickListener {
            val username = login_usernameedittext.text.toString()
            val password = login_passwordedittext.text.toString()
            LoginAsyncTask(this, username, password).execute()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    fun createMainMenuFragment() {
        replaceFragment(R.id.framelayout_main, MainMenuFragment.getInstance(), FragmentKeys.MAIN_MENU_FRAGMENT)
    }

    private class LoginAsyncTask(val loginFragment: LoginFragment, val username: String, val password: String) : AsyncTask<URL?, Int?, Optional<AppUser>>() {
        override fun doInBackground(vararg urls: URL?): Optional<AppUser> {
            return loginFragment.userService.login(username, password)
            //TODO: download here other necessary
        }

        override fun onProgressUpdate(vararg progress: Int?) {
        }

        override fun onPostExecute(result: Optional<AppUser>) {
            if (result.isPresent) {
                loginFragment.dataStorage.currentUser.postValue(result.get())
                loginFragment.createMainMenuFragment()
            } else {
                loginFragment.view?.let {
                    PopupUtil.createInfoPopup(loginFragment.requireContext(), it, "Nie udało się zalogować",
                    "Zweryfikuj nazwę użytkownika oraz hasło i spróbuj ponownie")
                }
            }
        }
    }
}
