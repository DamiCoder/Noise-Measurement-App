package dcwiek.noisemeasurmentapp.ui.fragment

import androidx.fragment.app.Fragment
import dcwiek.noisemeasurmentapp.R

open class ExtendedFragment: Fragment() {

    fun replaceFragment(containerId: Int, replacement: Fragment){
        fragmentManager
            ?.beginTransaction()
            ?.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            ?.replace(containerId, replacement)
            ?.commit()
    }

    fun replaceFragment(containerId: Int, replacement: Fragment, tag: String){
        fragmentManager
            ?.beginTransaction()
            ?.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            ?.replace(containerId, replacement, tag)
            ?.commit()
    }
}