package dcwiek.noisemeasurmentapp.ui.fragment.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.ui.fragment.common.fragment.ExtendedFragment
import dcwiek.noisemeasurmentapp.ui.fragment.login.LoginFragment
import kotlinx.android.synthetic.main.fragment_register.*

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
            replaceFragment(R.id.framelayout_main, LoginFragment.newInstance())
        }
        super.onViewCreated(view, savedInstanceState)
    }
}
