package dcwiek.noisemeasurmentapp.ui.fragment.register

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.domain.model.AppUser
import dcwiek.noisemeasurmentapp.ui.fragment.common.fragment.ExtendedFragment
import dcwiek.noisemeasurmentapp.ui.fragment.common.popup.PopupUtil
import dcwiek.noisemeasurmentapp.ui.fragment.login.LoginFragment
import kotlinx.android.synthetic.main.fragment_register.*
import java.net.URL
import java.util.*

class RegisterFragment : ExtendedFragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    override fun onCreateView(        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button_register_back.setOnClickListener{
            switchToLoginFragment()
        }


        button_register_register.setOnClickListener {
            val username = register_usernameedittext.text.toString()
            val password = register_passwordedittext.text.toString()

            hideSoftKeyboard()
            RegisterAsyncTask(this, username, password).execute()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    fun switchToLoginFragment() {
        replaceFragment(R.id.framelayout_main, LoginFragment.newInstance())
    }

    private class RegisterAsyncTask(val registerFragment: RegisterFragment, val username: String, val password: String) : AsyncTask<URL?, Int?, Optional<AppUser>>() {
        override fun doInBackground(vararg urls: URL?): Optional<AppUser> {
            return registerFragment.userService.register(username, password)
        }

        override fun onProgressUpdate(vararg progress: Int?) {
        }

        override fun onPostExecute(result: Optional<AppUser>) {
            if (result.isPresent) {
                PopupUtil.createInfoPopup(registerFragment.requireContext(), registerFragment.requireView(), "Udało się założyć konto!",
                    "Teraz możesz się zalogować")
                registerFragment.switchToLoginFragment()
            } else {
                PopupUtil.createInfoPopup(registerFragment.requireContext(), registerFragment.requireView(), "Nie udało się zarejestrować",
                        "Spróbuj ponownie za jakiś czas")
            }
        }
    }
}
