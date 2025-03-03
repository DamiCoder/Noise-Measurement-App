package dcwiek.noisemeasurmentapp.ui.fragment.common.fragment

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.application.NoiseMeasurementApplication
import dcwiek.noisemeasurmentapp.domain.DataStorage
import dcwiek.noisemeasurmentapp.service.place.PlaceService
import dcwiek.noisemeasurmentapp.service.probe.ProbeService
import dcwiek.noisemeasurmentapp.service.regulation.RegulationService
import dcwiek.noisemeasurmentapp.service.standard.StandardService
import dcwiek.noisemeasurmentapp.service.user.UserService
import dcwiek.noisemeasurmentapp.ui.constants.FragmentKeys
import dcwiek.noisemeasurmentapp.ui.fragment.menu.MainMenuFragment


open class ExtendedFragment: Fragment() {

    lateinit var dataStorage: DataStorage
    lateinit var userService: UserService
    lateinit var placeService: PlaceService
    lateinit var regulationService: RegulationService
    lateinit var standardService: StandardService
    lateinit var probeService: ProbeService
    lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        val application = requireContext().applicationContext as NoiseMeasurementApplication
        val appServiceComponent = application.getAppServiceComponent()

        dataStorage = appServiceComponent.getDataStorage()
        userService = appServiceComponent.getUserService()
        placeService = appServiceComponent.getPlaceService()
        regulationService = appServiceComponent.getRegulationService()
        standardService = appServiceComponent.getStandardService()
        probeService = appServiceComponent.getProbeService()
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        }
    }

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

    fun loadMainMenuFragment() {
        val fragment = MainMenuFragment.getInstance()
        replaceFragment(R.id.framelayout_main, fragment, FragmentKeys.MAIN_MENU_FRAGMENT)
        when(fragment.currentMenuItem?.itemId) {
            R.id.item_bottomnav_record -> fragment.initializeRecordView()
            R.id.item_bottomnav_history -> fragment.initializeArchiveView()
            R.id.item_bottomnav_standards -> fragment.initializeStandardsView()
            R.id.item_bottomnav_help -> fragment.initializeHelpView()
        }
    }

    open fun hideSoftKeyboard(rootView: View) {
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            rootView.getWindowVisibleDisplayFrame(r)

            val heightDiff = rootView.rootView.height - r.height()
            if (heightDiff > 0.25 * rootView.rootView.height) {
                val inputMethodManager = this@ExtendedFragment.activity!!
                    .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

                inputMethodManager.hideSoftInputFromWindow(activity!!.currentFocus!!.windowToken, 0)
            }
        }
    }
}