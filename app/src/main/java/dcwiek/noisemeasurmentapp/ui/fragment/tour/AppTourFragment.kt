package dcwiek.noisemeasurmentapp.ui.fragment.tour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.ui.constants.FragmentKeys
import dcwiek.noisemeasurmentapp.ui.fragment.common.fragment.ExtendedFragment
import dcwiek.noisemeasurmentapp.ui.fragment.menu.MainMenuFragment
import kotlinx.android.synthetic.main.fragment_apptour.view.*

class AppTourFragment: ExtendedFragment() {

    companion object {
        fun newInstance() = AppTourFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_apptour, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apptour_continuebutton.setOnClickListener {
            createMainMenuFragment()
        }
    }

    fun createMainMenuFragment() {
        replaceFragment(R.id.framelayout_main, MainMenuFragment.getInstance(), FragmentKeys.MAIN_MENU_FRAGMENT)
    }
}